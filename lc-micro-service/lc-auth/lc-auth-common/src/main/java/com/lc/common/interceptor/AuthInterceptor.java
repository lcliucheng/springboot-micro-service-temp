package com.lc.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.lc.common.AuthContext;
import com.lc.common.annotation.Signed;
import com.lc.common.config.AuthProperties;
import com.lc.common.jwt.JwtToken;
import com.lc.common.jwt.JwtTokenUtils;
import com.lc.common.jwt.store.TokenStore;
import com.lc.exception.ErrorCode;
import com.lc.response.Response;
import com.lc.web.utils.WebUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * <p> 授权拦截器 </p>
 *
 * @author liucheng
 * @since 2019-11-23
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthProperties authProperties;
    private final TokenStore tokenStore;
    private final JwtTokenUtils jwtTokenUtils;

    public AuthInterceptor(@Nullable final AuthProperties authProperties, @Nullable final TokenStore tokenStore,
                           @Nullable final JwtTokenUtils jwtTokenUtils) {
        this.authProperties = authProperties;
        this.tokenStore = tokenStore;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Assert.notNull(authProperties, "AuthProperties不能为空");
        Assert.notNull(tokenStore, "TokenStore不能为空");
        Assert.notNull(jwtTokenUtils, "JwtTokenUtils不能为空");
        String token = request.getParameter(authProperties.getHeader());
        if (StrUtil.isBlank(token)) {
            token = request.getHeader(authProperties.getHeader());
        }

        if (StrUtil.isNotBlank(token)) {
            try {
                Claims claims = jwtTokenUtils.parse(token);
                JwtToken<Object> jwtToken;
                if(StrUtil.isNotBlank(claims.getIssuer()) && claims.getIssuer().contains("Admin")){
                    jwtToken = tokenStore.getTokenFst(claims.getAudience(), claims.getSubject());
                }else{
                    jwtToken = tokenStore.getToken(claims.getAudience(), claims.getSubject());
                }
                if (!Objects.isNull(jwtToken) && Objects.equals(jwtToken.getId(), claims.getId())) {
                    if (authProperties.isRelet()) {
                        tokenStore.reletToken(jwtToken);
                    }
                    AuthContext.setJwtToken(jwtToken);
                }
            }catch (Exception e){
                log.warn(String.format("凭证异常[%s]",token),e);
                WebUtils.write(response, Response.failed(ErrorCode.UNAUTHORIZED.getValue(),"登录已失效，请重新登录"));
                return false;
            }
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(Signed.class)
                    || handlerMethod.getMethod().getDeclaringClass().isAnnotationPresent(Signed.class)) {
                if (Objects.isNull(AuthContext.getJwtToken())) {
                    WebUtils.write(response, Response.failed(ErrorCode.UNAUTHORIZED.getValue(),"登录已失效，请重新登录"));
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {

    }

}
