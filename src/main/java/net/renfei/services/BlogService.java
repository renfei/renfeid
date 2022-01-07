package net.renfei.services;

import net.renfei.domain.user.User;
import net.renfei.exception.NeedPasswordException;
import net.renfei.exception.NotExistException;
import net.renfei.exception.SecretLevelException;
import net.renfei.model.ListData;
import net.renfei.model.blog.PostSidebarVO;
import net.renfei.model.system.BlogVO;

/**
 * 博客服务
 *
 * @author renfei
 */
public interface BlogService {
    /**
     * 获取所有文章列表
     *
     * @param user
     * @param isAdmin
     * @param pages
     * @param rows
     * @return
     */
    ListData<BlogVO> getAllPostList(User user, boolean isAdmin, int pages, int rows);

    /**
     * 根据ID获取公开的博客文章
     *
     * @param id   文章ID
     * @param user 当前查看的用户
     * @return BlogDomain
     * @throws NotExistException     文章不存在异常
     * @throws NeedPasswordException 文章需要密码异常
     * @throws SecretLevelException  保密等级异常
     */
    BlogVO getBlogById(Long id, User user) throws NotExistException,
            NeedPasswordException, SecretLevelException;

    /**
     * 根据ID获取公开的博客文章
     *
     * @param id       文章ID
     * @param user     当前查看的用户
     * @param password 查看文章的密码
     * @return BlogDomain
     * @throws NotExistException     文章不存在异常
     * @throws NeedPasswordException 文章需要密码异常
     * @throws SecretLevelException  保密等级异常
     */
    BlogVO getBlogById(Long id, User user, String password) throws NotExistException,
            NeedPasswordException, SecretLevelException;

    /**
     * 根据ID、密码获取公开的博客文章
     *
     * @param id       文章ID
     * @param user     当前查看的用户
     * @param password 查看文章的密码
     * @param isAdmin  是否是管理员操作
     * @return BlogDomain
     * @throws NotExistException     文章不存在异常
     * @throws NeedPasswordException 文章需要密码异常
     * @throws SecretLevelException  保密等级异常
     */
    BlogVO getBlogById(Long id, User user, String password, boolean isAdmin)
            throws NotExistException, NeedPasswordException, SecretLevelException;

    /**
     * 增加博客文章的阅读量，交给线程池异步执行
     *
     * @param blogVO 博文领域对象
     */
    void view(BlogVO blogVO, User user, String password);

    /**
     * 构建博客侧边栏内容
     *
     * @return
     */
    PostSidebarVO buildPostSidebar(User user);

    String getJsonld(BlogVO blogDomain);

    void updatePageRank();
}
