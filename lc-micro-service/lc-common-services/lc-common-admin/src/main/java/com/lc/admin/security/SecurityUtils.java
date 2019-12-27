package com.lc.admin.security;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限工具类
 *
 * @author liucheng
 * @since 2019-12-22
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    public static final String SPRING_SECURITY_FORM_SCOPE_KEY = "scope";

    /**
     * 获取密码
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String obtainPassword(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
    }

    /**
     * 获取用户名
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String obtainUsername(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
    }

    /**
     * 获取作用域
     *
     * @param request HttpServletRequest
     * @return Scope
     */
    public static String obtainScope(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_SCOPE_KEY);
    }

    /**
     * 获取凭证
     *
     * @param request HttpServletRequest
     * @param header  凭证请求头名称
     * @return String
     */
    public static String obtainAuthorization(HttpServletRequest request, String header) {
        String authorization = request.getHeader(header);
        if (StrUtil.isBlank(authorization)) {
            authorization = request.getParameter(header);
        }

        return authorization;
    }

}
