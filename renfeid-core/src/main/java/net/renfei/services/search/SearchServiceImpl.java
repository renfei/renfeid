package net.renfei.services.search;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.model.ListData;
import net.renfei.model.kitbox.IkAnalyzeVO;
import net.renfei.model.search.SearchItem;
import net.renfei.model.search.TypeEnum;
import net.renfei.repositories.SearchRepository;
import net.renfei.repositories.SysLogsMapper;
import net.renfei.repositories.model.HotSearch;
import net.renfei.services.BaseService;
import net.renfei.services.SearchService;
import net.renfei.services.system.AggregateService;
import net.renfei.utils.NumberUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.renfei.model.search.SearchItem.INDEX_COORDINATES;

/**
 * 搜索服务
 *
 * @author renfei
 */
@Service
public class SearchServiceImpl extends BaseService implements SearchService {
    private final SearchRepository searchRepository;
    private final RestHighLevelClient restHighLevelClient;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final SysLogsMapper sysLogsMapper;
    private final AggregateService aggregateService;

    public SearchServiceImpl(SearchRepository searchRepository,
                             RestHighLevelClient restHighLevelClient,
                             ElasticsearchRestTemplate elasticsearchRestTemplate,
                             SysLogsMapper sysLogsMapper, AggregateService aggregateService) {
        this.searchRepository = searchRepository;
        this.restHighLevelClient = restHighLevelClient;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
        this.sysLogsMapper = sysLogsMapper;
        this.aggregateService = aggregateService;
    }

    /**
     * 搜索
     *
     * @param word  搜索词
     * @param pages 页码
     * @param rows  每页容量
     * @return
     */
    @Override
    public ListData<SearchItem> search(String word, String pages, String rows) {
        return search(null, null, null, word, pages, rows);
    }

    /**
     * 搜索
     *
     * @param type  类型
     * @param word  搜索词
     * @param pages 页码
     * @param rows  每页容量
     * @return
     */
    @Override
    public ListData<SearchItem> search(TypeEnum type, String word, String pages, String rows) {
        return search(type, null, null, word, pages, rows);
    }

    /**
     * 搜索
     *
     * @param type      类型
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param word      搜索词
     * @param pages     页码
     * @param rows      每页容量
     * @return
     */
    @Override
    public ListData<SearchItem> search(TypeEnum type, Date startDate, Date endDate, String word, String pages, String rows) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (!ObjectUtils.isEmpty(word)) {
            QueryBuilder wordBuilder = QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchQuery("title", word))
                    .should(QueryBuilders.matchQuery("content", word));
            queryBuilder.must(wordBuilder);
        }
        if (type != null) {
            queryBuilder.must(QueryBuilders.termQuery("type", type.getName()));
        }
        if (startDate != null) {
            queryBuilder.must(QueryBuilders.rangeQuery("date").gt(startDate));
        }
        if (endDate != null) {
            queryBuilder.must(QueryBuilders.rangeQuery("date").lt(startDate));
        }
        return search(queryBuilder, pages, rows);
    }

    @Override
    public ListData<SearchItem> search(TypeEnum type, Long originalId, String pages, String rows) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("type", type.getName()))
                .must(QueryBuilders.termQuery("originalId", originalId));
        return search(queryBuilder, pages, rows);
    }

    @Override
    public ListData<SearchItem> search(QueryBuilder queryBuilder, String pages, String rows) {
        createIndex();
        int page = NumberUtils.parseInt(pages, 1) - 1,
                size = NumberUtils.parseInt(rows, 10);
        if (page < 0) {
            page = 0;
        }
        if (size < 0) {
            size = 10;
        }
        // 关键词高亮的色值
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                //添加分页  注意页码是从0开始的
                //pageable的实现类PageRequest的静态方法of
                //要排序就增加参数3 Sort.Direction.ASC升  Sort.Direction.DESC降
                .withPageable(PageRequest.of(page, size))
                //排序整体
                //根据字段排序fieldSort("字段名")   .order(SortOrder.ASC/DESC)
//                .withSort(SortBuilders.fieldSort("date").order(SortOrder.DESC))
                .build();
        //elasticsearchRestTemplate.search方法参数一,本机查询的构造,参数二index的类,可选参数三再次声明库名(可以多个)
        SearchHits<SearchItem> search = elasticsearchRestTemplate.search(query, SearchItem.class, IndexCoordinates.of(INDEX_COORDINATES));
        ListData<SearchItem> searchItemListData = new ListData<>();
        searchItemListData.setTotal(search.getTotalHits());
        List<SearchItem> searchItems = new ArrayList<>();
        search.forEach(searchItemSearchHit -> searchItems.add(searchItemSearchHit.getContent()));
        searchItemListData.setData(searchItems);
        return searchItemListData;
    }

    /**
     * IK分词
     *
     * @param word
     * @return
     * @throws IOException
     */
    @Override
    public List<IkAnalyzeVO> getIkAnalyzeTerms(String word) throws IOException {
        AnalyzeRequest request = AnalyzeRequest.withIndexAnalyzer(INDEX_COORDINATES, "ik_max_word", word);
        AnalyzeResponse analyze = restHighLevelClient.indices().analyze(request, RequestOptions.DEFAULT);
        List<AnalyzeResponse.AnalyzeToken> tokenList = analyze.getTokens();
        // 循环赋值
        List<IkAnalyzeVO> searchTermList = new ArrayList<>();
        tokenList.forEach(ikToken -> {
            IkAnalyzeVO ikAnalyzeVO = new IkAnalyzeVO();
            ikAnalyzeVO.setWord(ikToken.getTerm());
            ikAnalyzeVO.setType(ikToken.getType());
            searchTermList.add(ikAnalyzeVO);
        });
        return searchTermList;
    }

    /**
     * 创建索引库，如果不存在触发全量同步
     */
    @Override
    public void createIndex() {
        /*
        由于我的博客数据量小，更新频率低，所以采取每天删掉索引重建的方式来同步数据
        省去了丢失的全量同步，新增的增量同步，直接每天凌晨全量导入
         */
        //设置索引信息(绑定实体类)  返回IndexOperations
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(SearchItem.class);
        if (!indexOperations.exists()) {
            // 不存在索引库，创建索引库
            indexOperations.create();
            //Creates the index mapping for the entity this IndexOperations is bound to.
            //为该IndexOperations绑定到的实体创建索引映射。  有一个为给定类创建索引的重载,需要类的字节码文件
            Document mapping = indexOperations.createMapping();
            //writes a mapping to the index  将刚刚通过类创建的映射写入索引
            indexOperations.putMapping(mapping);
            List<SearchItem> searchItemAll = aggregateService.getAllDataBySearchItem();
            this.searchRepository.saveAll(searchItemAll);
        }
    }

    @Override
    public void deleteIndex() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(SearchItem.class);
        if (indexOperations.exists()) {
            // 已经存在，删除
            indexOperations.delete();
        }
    }

    @Override
    public List<HotSearch> getHotSearchList() {
        Page<HotSearch> page = PageHelper.startPage(1, 15);
        sysLogsMapper.selectHotSearchList();
        return page.getResult();
    }
}