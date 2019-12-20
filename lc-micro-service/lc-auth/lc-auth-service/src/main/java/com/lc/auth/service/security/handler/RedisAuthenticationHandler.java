package com.lc.auth.service.security.handler;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.lc.auth.service.security.SecurityUserDetails;
import com.lc.common.config.AuthProperties;
import com.lc.common.exception.AuthException;
import com.lc.common.jwt.AccessToken;
import com.lc.common.jwt.JwtToken;
import com.lc.common.jwt.JwtTokenUtils;
import com.lc.common.jwt.store.TokenStore;
import io.jsonwebtoken.Claims;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Redis认证处理接口实现
 *
 * @author liucheng
 * @since 2019-05-24
 */
@Component
public class RedisAuthenticationHandler implements AuthenticationHandler {

    private final RedissonClient redissonClient;
    private final AuthProperties authProperties;
    private final TokenStore tokenStore;
    private final JwtTokenUtils jwtTokenUtils;

    public RedisAuthenticationHandler(final RedissonClient redissonClient, final AuthProperties authProperties,
                                      final TokenStore tokenStore, final JwtTokenUtils jwtTokenUtils) {
        this.redissonClient = redissonClient;
        this.authProperties = authProperties;
        this.tokenStore = tokenStore;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    public AccessToken success(SecurityUserDetails securityUserDetails) {
        RAtomicLong loginFailAtomicLong = redissonClient.getAtomicLong(
                authProperties.getLoginFailPrefix() + ":" + securityUserDetails.getUsername());
        loginFailAtomicLong.delete();

        JwtToken<SecurityUserDetails> jwtToken = new JwtToken<> ();
        jwtToken.setId(IdWorker.getIdStr())
                .setUserId(securityUserDetails.getUser().getId().toString())
                .setUsername(securityUserDetails.getUsername())
                .setExpiresIn(authProperties.getExpiresIn())
                .setScope(securityUserDetails.getScope().name())
                .setUserDetails(securityUserDetails)
                .setCreatedAt(new Date());
        tokenStore.saveTokenFst(jwtToken);
        return jwtTokenUtils.generate(jwtToken, authProperties.getIssuer());
    }

    @Override
    public long failure(String username) throws LockedException {
        RAtomicLong loginFailAtomicLong = redissonClient.getAtomicLong(
                authProperties.getLoginFailPrefix() + ":" + username);
        long loginFailNum = loginFailAtomicLong.incrementAndGet();
        if (loginFailNum > authProperties.getLoginFailNum()) {
            throw new LockedException(String.format("超过最大失败次数[%d]", authProperties.getLoginFailNum()));
        }

        return loginFailNum;
    }

    @Override
    public void logout(SecurityUserDetails securityUserDetails) {
        tokenStore.deleteToken(securityUserDetails.getUser().getId().toString(), securityUserDetails.getScope().name());
    }

    @Override
    public void clear(String userId) {
        tokenStore.deleteToken(userId);
    }

    @Override
    public Claims getClaims(String token) throws AuthException {
        return jwtTokenUtils.parse(token);
    }

    @SuppressWarnings("unchecked")
    @Override
    public JwtToken<SecurityUserDetails> getJwtToken(String userId, String scope) {
        return tokenStore.getTokenFst(userId, scope);
    }

    @Override
    public boolean nonLocked(String username) {
        RAtomicLong loginFailAtomicLong = redissonClient.getAtomicLong(
                authProperties.getLoginFailPrefix() + ":" + username);
        long loginFailNum = loginFailAtomicLong.get();
        return loginFailNum < authProperties.getLoginFailNum();
    }

    @Override
    public String getHeader() {
        return authProperties.getHeader();
    }

}
