package net.renfei.controller.photo;

import net.renfei.annotation.OperationLog;
import net.renfei.controller.BaseController;
import net.renfei.domain.AlbumDomain;
import net.renfei.domain.photo.Album;
import net.renfei.model.ListData;
import net.renfei.model.album.AlbumPageView;
import net.renfei.model.system.SystemTypeEnum;
import net.renfei.services.PaginationService;
import net.renfei.services.photo.PhotoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 网络相册栏目
 *
 * @author renfei
 */
@Controller
@RequestMapping("/photo")
public class PhotoController extends BaseController {
    private final PhotoServiceImpl photoService;
    private final PaginationService paginationService;

    public PhotoController(PhotoServiceImpl photoService, PaginationService paginationService) {
        this.photoService = photoService;
        this.paginationService = paginationService;
    }

    /**
     * 网络相册列表
     *
     * @param page 页码
     * @param mv   每页容量
     * @return
     */
    @RequestMapping("")
    @OperationLog(module = SystemTypeEnum.ALBUM, desc = "访问相册首页")
    public ModelAndView getAllPhotoList(@RequestParam(value = "page", required = false) String page,
                                        ModelAndView mv) {
        ListData<Album> albumListData = photoService.getAllAlbumList(page, "10");
        AlbumPageView<ListData<Album>> pageView = buildPageView(AlbumPageView.class, albumListData);
        assert systemConfig != null;
        pageView.getPageHead().setTitle("任霏的网络相册 - " + systemConfig.getSiteName());
        mv.addObject("pageView", pageView);
        setPagination(paginationService, mv, page, albumListData.getTotal(), "/photo?page=");
        mv.setViewName("photo/index");
        return mv;
    }

    /**
     * 网络相册列表错误地址重定向
     *
     * @return
     */
    @RequestMapping({
            "index.html", "index.htm", "index.xhtml", "index.shtml",
            "index.php", "index.asp", "index.aspx", "index.do",
            "index.jsp", "index.dll", "index.php3", "index.pl",
            "index.cgi",
            "default.html", "default.htm", "default.xhtml", "default.shtml",
            "default.php", "default.asp", "default.aspx", "default.do",
            "default.jsp", "default.dll", "default.php3", "default.pl",
            "default.cgi"
    })
    @OperationLog(module = SystemTypeEnum.ALBUM, desc = "访问相册首页Index.html")
    public RedirectView getPhotoListDir() {
        assert systemConfig != null;
        RedirectView redirectView = new RedirectView(systemConfig.getSiteDomainName() + "/photo");
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return redirectView;
    }

    @RequestMapping("{id}")
    @OperationLog(module = SystemTypeEnum.ALBUM, desc = "访问相册详情页")
    public ModelAndView getPhotoById(@PathVariable("id") Long id, ModelAndView mv) throws NoHandlerFoundException {
        AlbumDomain albumDomain = photoService.getPhotoImgByPhotoId(id);
        if (albumDomain.getAlbum() == null) {
            noHandlerFoundException();
        }
        AlbumPageView<AlbumDomain> pageView = buildPageView(AlbumPageView.class, albumDomain);
        assert systemConfig != null;
        assert albumDomain.getAlbum() != null;
        pageView.getPageHead().setTitle(albumDomain.getAlbum().getTitle() + " - 任霏的网络相册 - " + systemConfig.getSiteName());
        mv.addObject("pageView", pageView);
        mv.setViewName("photo/show");
        return mv;
    }
}