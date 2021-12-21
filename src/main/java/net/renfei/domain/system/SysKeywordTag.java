package net.renfei.domain.system;

import lombok.Data;
import net.renfei.exception.NotExistException;
import net.renfei.repositories.SysKeywordObjectMapper;
import net.renfei.repositories.SysKeywordTagMapper;
import net.renfei.repositories.model.SysKeywordObject;
import net.renfei.repositories.model.SysKeywordObjectExample;
import net.renfei.repositories.model.SysKeywordTagExample;
import net.renfei.utils.ApplicationContextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 全局关键字标签
 *
 * @author renfei
 */
@Data
public final class SysKeywordTag {
    private Long id;
    private String enName;
    private String zhName;
    private Long count;
    private final SysKeywordTagMapper keywordTagMapper;
    private final SysKeywordObjectMapper keywordObjectMapper;

    {
        keywordTagMapper = (SysKeywordTagMapper) ApplicationContextUtil.getBean("sysKeywordTagMapper");
        keywordObjectMapper = (SysKeywordObjectMapper) ApplicationContextUtil.getBean("sysKeywordObjectMapper");
    }

    /**
     * 私有构造函数，禁止直接实例化
     */
    private SysKeywordTag() {
    }

    /**
     * 根据标签ID实例化
     *
     * @param id 标签ID
     * @throws NotExistException
     */
    public SysKeywordTag(Long id) throws NotExistException {
        assert keywordTagMapper != null;
        net.renfei.repositories.model.SysKeywordTag sysKeywordTag = keywordTagMapper.selectByPrimaryKey(id);
        if (sysKeywordTag == null) {
            throw new NotExistException("标签不存在");
        }
        this.id = id;
        this.enName = sysKeywordTag.getEnName();
        this.zhName = sysKeywordTag.getZhName();
    }

    /**
     * 根据 EnName 实例化
     *
     * @param enName 英文别名
     * @throws NotExistException
     */
    public SysKeywordTag(String enName) throws NotExistException {
        SysKeywordTagExample example = new SysKeywordTagExample();
        example.createCriteria().andEnNameEqualTo(enName);
        assert keywordTagMapper != null;
        List<net.renfei.repositories.model.SysKeywordTag> sysKeywordTagList =
                keywordTagMapper.selectByExample(example);
        if (sysKeywordTagList.isEmpty()) {
            throw new NotExistException("标签不存在");
        }
        this.id = sysKeywordTagList.get(0).getId();
        this.enName = sysKeywordTagList.get(0).getEnName();
        this.zhName = sysKeywordTagList.get(0).getZhName();
    }

    /**
     * 获取所有标签
     *
     * @param systemTypeEnum 子系统类型
     * @return
     */
    public static List<SysKeywordTag> keywordTagList(SystemTypeEnum systemTypeEnum) {
        SysKeywordTag sysKeywordTag = new SysKeywordTag();
        return sysKeywordTag.getKeywordTagListBySysType(systemTypeEnum);
    }

    /**
     * 获取同类型其他拥有相同标签的对象ID
     *
     * @param systemTypeEnum 子系统类型
     * @param objectId       查询当前的对象ID
     * @return
     */
    public static List<Long> cognateIdList(SystemTypeEnum systemTypeEnum, Long objectId) {
        SysKeywordTag sysKeywordTag = new SysKeywordTag();
        return sysKeywordTag.getCognateIdList(systemTypeEnum, objectId);
    }

    private List<SysKeywordTag> getKeywordTagListBySysType(SystemTypeEnum systemTypeEnum) {
        List<SysKeywordTag> sysKeywordTagList = new CopyOnWriteArrayList<>();
        List<Map<String, Object>> dataList = keywordTagMapper.getTagAndCount(systemTypeEnum.toString());
        if (dataList.isEmpty()) {
            return sysKeywordTagList;
        }
        for (Map<String, Object> data : dataList
        ) {
            SysKeywordTag sysKeywordTag = new SysKeywordTag();
            sysKeywordTag.setId(Long.parseLong(data.get("id").toString()));
            sysKeywordTag.setEnName(data.get("en_name").toString());
            sysKeywordTag.setZhName(data.get("zh_name").toString());
            sysKeywordTag.setCount(Long.parseLong(data.get("CNT").toString()));
            sysKeywordTagList.add(sysKeywordTag);
        }
        return sysKeywordTagList;
    }

    /**
     * 获取同类型其他拥有相同标签的对象ID
     *
     * @param systemTypeEnum 子系统类型
     * @param objectId       查询当前的对象ID
     * @return
     */
    private List<Long> getCognateIdList(SystemTypeEnum systemTypeEnum, Long objectId) {
        SysKeywordObjectExample example = new SysKeywordObjectExample();
        example.createCriteria()
                .andObjectIdEqualTo(objectId)
                .andObjectTypeEqualTo(systemTypeEnum.toString());
        List<SysKeywordObject> sysKeywordObjects = keywordObjectMapper.selectByExample(example);
        List<Long> ids = new ArrayList<>();
        sysKeywordObjects.forEach(sysKeywordObject -> ids.add(sysKeywordObject.getId()));
        example = new SysKeywordObjectExample();
        example.createCriteria()
                .andObjectIdNotEqualTo(objectId)
                .andObjectTypeEqualTo(systemTypeEnum.toString())
                .andIdIn(ids);
        sysKeywordObjects = keywordObjectMapper.selectByExample(example);
        List<Long> objIds = new ArrayList<>();
        sysKeywordObjects.forEach(sysKeywordObject -> objIds.add(sysKeywordObject.getObjectId()));
        return objIds;
    }
}