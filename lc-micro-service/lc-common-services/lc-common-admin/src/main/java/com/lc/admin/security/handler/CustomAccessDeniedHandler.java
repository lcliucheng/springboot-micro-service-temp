package com.lc.admin.security.handler;

import com.lc.exception.ErrorCode;
import com.lc.response.Response;
import com.lc.web.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证过的用户访问无权限资源时的异常
 *
 * @author liucheng
 * @since 2019-12-21
 */
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        WebUtils.write(response, Response.failed(ErrorCode.FORBIDDEN.getValue(), accessDeniedException.getMessage()));
    }

}
