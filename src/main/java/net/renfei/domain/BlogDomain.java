package net.renfei.domain;

import com.github.pagehelper.PageHelper;
import lombok.Getter;
import net.renfei.domain.blog.Category;
import net.renfei.domain.blog.Post;
import net.renfei.domain.comment.Comment;
import net.renfei.domain.system.SystemTypeEnum;
import net.renfei.domain.user.User;
import net.renfei.exception.BlogPostNeedPasswordException;
import net.renfei.exception.NotExistException;
import net.renfei.exception.SecretLevelException;
import net.renfei.model.CommentStatus;
import net.renfei.model.PostStatus;
import net.renfei.model.SecretLevel;
import net.renfei.repositories.BlogCategoryMapper;
import net.renfei.repositories.BlogPostsMapper;
import net.renfei.repositories.model.BlogCategory;
import net.renfei.repositories.model.BlogCategoryExample;
import net.renfei.repositories.model.BlogPostsExample;
import net.renfei.repositories.model.BlogPostsWithBLOBs;
import net.renfei.utils.ApplicationContextUtil;
import net.renfei.utils.ListUtils;
import net.renfei.utils.PasswordUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 博客领域的聚合根
 * 与博客相关的领域对象都在这里
 *
 * @author renfei
 */
public final class BlogDomain {
    @Getter
    private final Post post;
    @Getter
    private final User author;
    @Getter
    private final Category category;
    @Getter
    private final List<Comment> commentList;
    private final BlogPostsMapper blogPostsMapper;
    private final BlogCategoryMapper categoryMapper;

    {
        blogPostsMapper = (BlogPostsMapper) ApplicationContextUtil.getBean("blogPostsMapper");
        categoryMapper = (BlogCategoryMapper) ApplicationContextUtil.getBean("blogCategoryMapper");
    }

    /**
     * 私有的默认构造，禁止外部直接实例化默认配置
     */
    private BlogDomain() {
        post = null;
        author = null;
        category = null;
        commentList = null;
    }

    /**
     * 加载一篇博客文章
     *
     * @param id       博客文章ID
     * @param user     当前登陆用户
     * @param password 博客文章密码
     * @param isAdmin  是否是管理员管理操作
     * @throws NotExistException 文章不存在异常
     */
    public BlogDomain(Long id, User user, String password, boolean isAdmin)
            throws NotExistException, BlogPostNeedPasswordException, SecretLevelException {
        if (id == null) {
            throw new NotExistException("博客文章不存在");
        }
        post = initPost(id);
        author = new User(post.getPostAuthor());
        category = initCategory(post.getCategoryId());
        commentList = new CommentDomain(SystemTypeEnum.BLOG, id).getCommentList();
        if (!isAdmin) {
            // 如果不是管理员操作，需要更多限制判断
            // 判断文章状态
            if (!PostStatus.PUBLISH.equals(post.getPostStatus())) {
                throw new NotExistException("文章当前状态不可被阅读。");
            }
            // 判断密码正确性
            if (!ObjectUtils.isEmpty(post.getPostPassword())) {
                if (ObjectUtils.isEmpty(password)) {
                    throw new BlogPostNeedPasswordException("文章需要密码才能查看。");
                } else {
                    // 判断密码是否正确
                    if (!PasswordUtils.verifyPassword(password, post.getPostPassword())) {
                        throw new BlogPostNeedPasswordException("文章需要密码才能查看。");
                    }
                }
            }
        }
        // 判断保密等级，管理员也需要判断
        if (user != null) {
            if (user.getSecretLevel().getLevel() < post.getSecretLevel().getLevel()) {
                throw new SecretLevelException("您当前的保密等级无权查看此文章内容。");
            }
        } else if (SecretLevel.UNCLASSIFIED.getLevel() < post.getSecretLevel().getLevel()) {
            throw new SecretLevelException("当前文章内容受到保密系统保护，请先登陆后查看。");
        }
    }

    /**
     * 博文增加浏览量
     */
    public void view() {
        BlogPostsExample example = new BlogPostsExample();
        example.createCriteria().andIdEqualTo(post.getId());
        BlogPostsWithBLOBs blogPost = ListUtils.getOne(blogPostsMapper.selectByExampleWithBLOBs(example));
        blogPost.setPostViews(blogPost.getPostViews() + 1);
        blogPostsMapper.updateByExampleWithBLOBs(blogPost, example);
    }

    /**
     * 获取博客领域下所有分类
     *
     * @param user 当前登陆用户
     * @return
     */
    public static List<Category> allBlogCategory(User user) {
        BlogDomain blogDomain = new BlogDomain();
        return blogDomain.getAllBlogCategory(user);
    }

    /**
     * 获取博客领域下所有分类
     *
     * @param user 当前登陆用户
     * @return
     */
    private List<Category> getAllBlogCategory(User user) {
        BlogCategoryExample example = new BlogCategoryExample();
        BlogCategoryExample.Criteria criteria = example.createCriteria();
        if (user != null) {
            // 保密等级判断
            criteria.andSecretLevelLessThanOrEqualTo(user.getSecretLevel().getLevel());
        } else {
            criteria.andSecretLevelEqualTo(SecretLevel.UNCLASSIFIED.getLevel());
        }
        List<BlogCategory> blogCategories = categoryMapper.selectByExample(example);
        List<Category> categories = new CopyOnWriteArrayList<>();
        blogCategories.forEach(blogCategory -> categories.add(initCategory(blogCategory.getId())));
        return categories;
    }

    /**
     * 最热文章Top10
     *
     * @param user 当前登陆用户
     * @return
     */
    public static List<Post> hotPostTop10(User user) {
        BlogDomain blogDomain = new BlogDomain();
        return blogDomain.getHotPostTop10(user);
    }

    /**
     * 最热文章Top10
     *
     * @param user 当前登陆用户
     * @return
     */
    private List<Post> getHotPostTop10(User user) {
        BlogPostsExample example = new BlogPostsExample();
        BlogPostsExample.Criteria criteria = example.createCriteria();
        if (user != null) {
            // 保密等级判断
            criteria.andSecretLevelLessThanOrEqualTo(user.getSecretLevel().getLevel());
        } else {
            criteria.andSecretLevelEqualTo(SecretLevel.UNCLASSIFIED.getLevel());
        }
        criteria
                .andPostStatusEqualTo(PostStatus.PUBLISH.toString())
                .andPostDateLessThanOrEqualTo(new Date());
        example.setOrderByClause("avg_views DESC,post_date DESC");
        PageHelper.startPage(1, 10);
        List<BlogPostsWithBLOBs> blogPostList = blogPostsMapper.selectByExampleWithBLOBs(example);
        List<Post> postList = new CopyOnWriteArrayList<>();
        blogPostList.forEach(blogPost -> postList.add(convert(blogPost)));
        return postList;
    }

    private Post initPost(Long id) throws NotExistException {
        BlogPostsExample example = new BlogPostsExample();
        example.createCriteria().andIdEqualTo(id);
        BlogPostsWithBLOBs blogPost = ListUtils.getOne(blogPostsMapper.selectByExampleWithBLOBs(example));
        if (blogPost == null || PostStatus.DELETED.toString().equals(blogPost.getPostStatus())) {
            throw new NotExistException("博客文章不存在");
        }
        return convert(blogPost);
    }

    private Category initCategory(Long categoryId) {
        BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(categoryId);
        if (blogCategory == null) {
            return null;
        }
        return Category.builder()
                .id(blogCategory.getId())
                .enName(blogCategory.getEnName())
                .zhName(blogCategory.getZhName())
                .secretLevel(SecretLevel.valueOf(blogCategory.getSecretLevel()))
                .build();
    }

    private Post convert(BlogPostsWithBLOBs blogPost) {
        return Post.builder()
                .id(blogPost.getId())
                .title(blogPost.getPostTitle())
                .keyword(blogPost.getPostKeyword())
                .excerpt(blogPost.getPostExcerpt())
                .content(blogPost.getPostContent())
                .featuredImage(blogPost.getFeaturedImage())
                .isOriginal(blogPost.getIsOriginal())
                .sourceName(blogPost.getSourceName())
                .sourceUrl(blogPost.getSourceUrl())
                .postDate(blogPost.getPostDate())
                .postAuthor(blogPost.getPostAuthor())
                .categoryId(blogPost.getCategoryId())
                .postStatus(PostStatus.valueOf(blogPost.getPostStatus()))
                .commentStatus(CommentStatus.valueOf(blogPost.getCommentStatus()))
                .postPassword(blogPost.getPostPassword())
                .postModified(blogPost.getPostModified())
                .postParent(blogPost.getPostParent())
                .thumbsUp(blogPost.getThumbsUp())
                .thumbsDown(blogPost.getThumbsDown())
                .avgViews(blogPost.getAvgViews())
                .avgComment(blogPost.getAvgComment())
                .pageRank(blogPost.getPageRank())
                .secretLevel(SecretLevel.valueOf(blogPost.getSecretLevel()))
                .build();
    }
}
