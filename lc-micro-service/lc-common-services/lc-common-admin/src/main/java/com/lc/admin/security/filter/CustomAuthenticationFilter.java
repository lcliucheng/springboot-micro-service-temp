package com.lc.admin.security.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.lc.admin.security.SecurityUserDetails;
import com.lc.admin.security.SecurityUtils;
import com.lc.admin.security.handler.AuthenticationHandler;
import com.lc.common.exception.AuthException;
import com.lc.common.jwt.JwtToken;
import com.lc.exception.ErrorCode;
import com.lc.response.Response;
import com.lc.web.utils.WebUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义认证过滤器
 *
 * @author liucheng
 * @since 2019-12-21
 */
@SuppressWarnings("ALL")
@Slf4j
@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationHandler authenticationHandler;

    public CustomAuthenticationFilter(final AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String token = SecurityUtils.obtainAuthorization(request, authenticationHandler.getHeader());
        if (StrUtil.isBlank(token)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Claims claims = authenticationHandler.getClaims(token);

            if (CollectionUtil.isNotEmpty(claims)
                    && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                JwtToken<SecurityUserDetails> jwtToken = null;
                try {
                    jwtToken = authenticationHandler.getJwtToken(claims.getAudience(), claims.getSubject());
                }catch (Exception e) {
                    WebUtils.write(response, Response.failed(ErrorCode.UNAUTHORIZED.getValue(),"登录已失效，请重新登录"));
                    return;
                }
                if (!Objects.isNull(jwtToken) && Objects.equals(jwtToken.getId(), claims.getId())) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            jwtToken.getUserDetails(), token, jwtToken.getUserDetails().getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            chain.doFilter(request, response);
        } catch (AuthException e) {
            WebUtils.write(response, Response.failed(e.getErrorInfo().getCode(), e.getErrorInfo().getMsg()));
        }
    }

}
