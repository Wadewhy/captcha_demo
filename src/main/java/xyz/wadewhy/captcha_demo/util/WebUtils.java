package xyz.wadewhy.captcha_demo.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA
 *
 * @Author 钟子豪
 * 创建日期: 2019/11/24
 * 创建时间: 20:39
 * 类功能: 网络控制类
 */
public class WebUtils {
    /**
     * 得到request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    /**
     * 得到session
     *
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
    /**
     * 得到当前线程的请求对象
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return getServletRequestAttributes().getRequest();
    }
    /**
     * 得到session对象
     */
    public static HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }
    /**
     * 清除session对象
     */
    public static void removeHttpSession(){
        getSession().invalidate();
    }
}
