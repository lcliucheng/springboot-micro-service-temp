package com.lc.web.utils;

import cn.hutool.core.util.CharsetUtil;
import com.lc.exception.BaseRuntimeException;
import com.lc.uitls.JsonUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 网络工具类
 *
 * @author liucheng
 * @since 2019-12-16
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebUtils {

    /**
     * 未知
     */
    private static final String UNKNOWN = "unknown";
    /**
     * IP分隔符
     */
    private static final String IP_SPLIT = ",";

    /**
     * 获取请求的真实IP地址
     *
     * @param request 请求
     * @return 真实IP地址
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = getIp(request);
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (!StringUtils.isEmpty(ip)) {
            int index = ip.indexOf(IP_SPLIT);
            if (index > 0) {
                ip = ip.substring(0, index);
            }
        }
        return ip;
    }

    /**
     * 获取请求的IP地址
     *
     * @param request 请求
     * @return IP地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 添加Cookie
     *
     * @param response 客户端
     * @param request  请求
     * @param domain   域名
     * @param name     名称
     * @param value    值
     * @param maxAge   有效期
     */
    public static void addCookie(HttpServletResponse response, HttpServletRequest request, String domain, String name
            , String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        if (!StringUtils.isEmpty(domain)) {
            cookie.setDomain(domain);
        }
        String contextPath = request.getContextPath();
        String path = "/";
        if (!StringUtils.isEmpty(contextPath)) {
            path = contextPath;
        }

        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        cookie.setPath(path);
        cookie.setSecure(request.isSecure());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * 根据Cookie名称获取Cookie
     *
     * @param name    名称
     * @param request 请求
     * @return Cookie
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        throw new IllegalArgumentException("No matching cookie for [" + name + "]");
    }

    /**
     * 根据Cookie名称删除Cookie
     *
     * @param request 请求
     * @param name    名称
     */
    public static void deleteCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setMaxAge(0);
                }
            }
        }
    }

    /**
     * 回写数据
     *
     * @param response 客户端
     * @param object   回写内容
     */
    public static void write(HttpServletResponse response, Object object) {
        write(response, "application/json;charset=utf-8", object);
    }

    /**
     * 回写数据
     *
     * @param response    客户端
     * @param contentType 返回数据类型
     * @param object      回写内容
     */
    public static void write(HttpServletResponse response, String contentType, Object object) {
        response.setContentType(contentType);
        try (PrintWriter out = response.getWriter()) {
            String msg;
            if (object instanceof String) {
                msg = object.toString();
            } else {
                msg = JsonUtil.toStr(object);
            }

            out.write(msg);
            out.flush();
        } catch (IOException e) {
            throw new BaseRuntimeException ("回写数据失败", e);
        }
    }

    /**
     * 回写输出流数据
     *
     * @param response     客户端
     * @param outputStream 输出流
     * @param msg          消息
     */
    public static void write(HttpServletResponse response, ServletOutputStream outputStream, String msg) {
        if (outputStream != null) {
            response.setContentType("application/json;charset=utf-8");
            try {
                outputStream.write(msg.getBytes(CharsetUtil.defaultCharsetName()));
                outputStream.flush();
            } catch (IOException e) {
                throw new BaseRuntimeException("回写数据失败", e);
            }
        }
    }

}
