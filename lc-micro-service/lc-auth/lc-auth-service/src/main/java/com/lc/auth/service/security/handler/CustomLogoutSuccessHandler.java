package com.lc.auth.service.security.handler;

import com.zzjr.auth.service.security.SecurityUserDetails;
import com.zzjr.common.response.Response;
import com.zzjr.service.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录成功处理
 *
 * @author liucheng
 * @since 2019-05-21
 */
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final AuthenticationHandler authenticationHandler;

    public CustomLogoutSuccessHandler(final AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication)
            throws IOException, ServletException {
        SecurityUserDetails securityUserDetails = (SecurityUserDetails) authentication.getPrincipal();
        authenticationHandler.logout(securityUserDetails);
        WebUtils.write(response, Response.success());
    }

}
