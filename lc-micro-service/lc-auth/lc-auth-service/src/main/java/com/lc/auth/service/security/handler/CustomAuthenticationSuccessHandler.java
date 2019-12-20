package com.lc.auth.service.security.handler;

import cn.hutool.core.util.StrUtil;
import com.zzjr.auth.common.consts.Scope;
import com.zzjr.auth.common.jwt.AccessToken;
import com.zzjr.auth.service.security.SecurityUserDetails;
import com.zzjr.auth.service.security.SecurityUtils;
import com.zzjr.common.exception.ErrorCode;
import com.zzjr.common.response.Response;
import com.zzjr.service.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理类
 *
 * @author liucheng
 * @since 2019-05-21
 */
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationHandler authenticationHandler;

    public CustomAuthenticationSuccessHandler(final AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        try {
            String scopeStr = SecurityUtils.obtainScope(request);
            if (StrUtil.isBlank(scopeStr)) {
                WebUtils.write(response, Response.failed(ErrorCode.DATA_VALID_ERROR.getValue(), "作用域不存在"));
                return;
            }

            SecurityUserDetails securityUserDetails = (SecurityUserDetails) authentication.getPrincipal();

            try {
                securityUserDetails.setScope(Scope.valueOf(scopeStr));
            } catch (Exception e) {
                WebUtils.write(response, Response.failed(ErrorCode.DATA_VALID_ERROR.getValue(), "作用域格式不正确"));
                return;
            }

            AccessToken accessToken = authenticationHandler.success(securityUserDetails);
            WebUtils.write(response, Response.success(accessToken));
        } catch (Exception e) {
            WebUtils.write(response, Response.failed(ErrorCode.SERVICE_CALL_ERROR));
        }
    }

}
