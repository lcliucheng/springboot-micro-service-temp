package com.lc.auth.service.security;

import cn.hutool.core.util.StrUtil;
import com.lc.auth.service.security.handler.AuthenticationHandler;
import com.lc.exception.ErrorCode;
import com.lc.response.Response;
import com.lc.web.util.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决匿名用户访问无权限资源时的异常
 *
 * @author liucheng
 * @since 2019-05-21
 */
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthenticationHandler authenticationHandler;

    public CustomAuthenticationEntryPoint(final AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String token = SecurityUtils.obtainAuthorization(request, authenticationHandler.getHeader());
        if (StrUtil.isBlank(token)) {
            WebUtils.write(response, Response.failed(ErrorCode.UNAUTHORIZED));
        } else {
            WebUtils.write(response, Response.failed(ErrorCode.TOKEN_ERROR));
        }
    }
}
