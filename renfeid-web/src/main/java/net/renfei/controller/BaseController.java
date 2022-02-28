package net.renfei.controller;

import net.renfei.application.ApplicationContextUtil;
import net.renfei.config.SystemConfig;
import net.renfei.domain.UserDomain;
import net.renfei.domain.user.User;
import net.renfei.model.PageView;
import net.renfei.model.system.UserDetail;
import net.renfei.services.PaginationService;
import net.renfei.services.SysService;
import net.renfei.utils.SentryUtils;
import net.renfei.utils.UserDetailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static net.renfei.application.ApplicationContextUtil.checkSystemConfig;
import static net.renfei.config.SystemConfig.SESSION_AUTH_MODE;
import static net.renfei.config.SystemConfig.SESSION_KEY;

/**
 * Controller 基类
 *
 * @author renfei
 */
public abstract class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    protected SystemConfig systemConfig;
    @Autowired
    protected SysService sysService;
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected UserDetailUtils userDetailUtils;

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder WebDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    @ModelAttribute
    public void modelAttribute(ModelAndView mv) {
        assert systemConfig != null;
        checkSystemConfig(systemConfig);
        mv.addObject("active", systemConfig.getActive());
        mv.addObject("account", getSignUser());
    }

    protected void noHandlerFoundException() throws NoHandlerFoundException {
        HttpHeaders headers = new HttpHeaders();
        throw new NoHandlerFoundException(request.getMethod(), request.getRequestURL().toString(), headers);
    }

    protected void setPagination(PaginationService paginationService, ModelAndView mv, String page, Long count, String link) {
        setPagination(paginationService, mv, page, count, 10, link);
    }

    protected void setPagination(PaginationService paginationService, ModelAndView mv, String page, Long count, int rows, String link) {
        int totalPage = Integer.parseInt((count / rows) + "");
        if (count % rows > 0) {
            totalPage++;
        }
        if (totalPage <= 0) {
            totalPage = 1;
        }
        assert systemConfig != null;
        assert paginationService != null;
        mv.addObject("paginationList", paginationService.getPagination(page, totalPage, systemConfig.getSiteDomainName() + link));
    }

    protected User getSignUser() {
        UserDetail userDetail = userDetailUtils.getUserDetail(request);
        Optional<UserDetail> optUserDetail = Optional.ofNullable(userDetail);
        return optUserDetail
                .flatMap(UserDetail::getUserDomain)
                .flatMap(UserDomain::getUser)
                .orElse(null);
    }

    protected void updateSignUser(User user) {
        assert systemConfig != null;
        if (SESSION_AUTH_MODE.equals(systemConfig.getAuthMode())) {
            request.getSession().setAttribute(SESSION_KEY, user);
        }
    }

    protected ModelAndView checkSigned() {
        if (getSignUser() == null) {
            return new ModelAndView("redirect:/auth/signIn?callback=" + request.getRequestURL());
        }
        return null;
    }

    protected String getCallBack(String callback) {
        if (!ObjectUtils.isEmpty(callback)) {
            try {
                URL url = new URL(callback);
                String host = url.getHost();
                assert systemConfig != null;
                if (host.endsWith(systemConfig.getBaseDomainName())) {
                    return callback;
                }
            } catch (MalformedURLException ignored) {
                return "";
            }
        }
        return "";
    }

    /**
     * 构建一个通用的页面返回对象
     *
     * @param clazz  返回对象子类
     * @param object 数据负载对象
     * @param <T>    子类泛型
     * @return
     */
    protected <T extends PageView<T>> T buildPageView(Class<T> clazz, Object object) {
        Constructor<T> constructor;
        T result;
        try {
            constructor = clazz.getDeclaredConstructor(Object.class);
            result = constructor.newInstance(object);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error("构建通用的页面返回对象时出错", e);
            SentryUtils.captureException(e);
            return null;
        }
        if (sysService == null) {
            sysService = (SysService) ApplicationContextUtil.getBean("sysServiceImpl");
        }
        assert sysService != null;
        result.setPageHead(sysService.getPageHead());
        result.setPageHeader(sysService.getPageHeader(request));
        result.setPageFooter(sysService.getPageFooter());
        return result;
    }
}