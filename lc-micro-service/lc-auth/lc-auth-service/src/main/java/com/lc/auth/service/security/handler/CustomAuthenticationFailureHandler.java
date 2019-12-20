package com.lc.auth.service.security.handler;

import com.zzjr.auth.service.security.SecurityUtils;
import com.zzjr.common.exception.ErrorCode;
import com.zzjr.common.response.Response;
import com.zzjr.service.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理类
 *
 * @author liucheng
 * @since 2019-05-21
 */
@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final AuthenticationHandler authenticationHandler;

    public CustomAuthenticationFailureHandler(final AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        try {
            authenticationHandler.failure(SecurityUtils.obtainUsername(request));
            if (exception instanceof UsernameNotFoundException
                    || exception instanceof BadCredentialsException) {
                WebUtils.write(response, Response.failed(ErrorCode.USERNAME_OR_PASSWORD_ERROR));
            } else if (exception instanceof LockedException) {
                WebUtils.write(response, Response.failed(ErrorCode.USER_LOCKED_ERROR));
            } else if (exception instanceof DisabledException) {
                WebUtils.write(response, Response.failed(ErrorCode.USER_DISABLED));
            } else {
                WebUtils.write(response, Response.failed(ErrorCode.LOGIN_ERROR));
            }
        } catch (LockedException e) {
            WebUtils.write(response, Response.failed(ErrorCode.USER_LOCKED_ERROR.getValue(), e.getMessage()));
        } catch (Exception e) {
            WebUtils.write(response, Response.failed(ErrorCode.LOGIN_ERROR));
        }
    }
}
