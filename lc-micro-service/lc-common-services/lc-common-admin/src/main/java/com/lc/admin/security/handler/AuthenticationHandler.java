package com.lc.admin.security.handler;

import com.lc.admin.security.SecurityUserDetails;
import com.lc.common.exception.AuthException;
import com.lc.common.jwt.AccessToken;
import com.lc.common.jwt.JwtToken;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.LockedException;

/**
 * 认证处理接口
 *
 * @author liucheng
 * @since 2019-12-24
 */
public interface AuthenticationHandler {

    /**
     * 认证成功
     *
     * @param securityUserDetails SecurityUserDetails
     * @return AccessToken
     */
    AccessToken success(SecurityUserDetails securityUserDetails);

    /**
     * 认证失败
     *
     * @param username 用户名
     * @return long 失败次数
     * @throws LockedException 锁定异常
     */
    long failure(String username) throws LockedException;

    /**
     * 退出登录
     *
     * @param securityUserDetails SecurityUserDetails
     */
    void logout(SecurityUserDetails securityUserDetails);

    /**
     * 清除用户凭证
     *
     * @param userId 用户编号
     */
    void clear(String userId);

    /**
     * 获取 Claims
     *
     * @param token 凭证
     * @return Claims
     */
    Claims getClaims(String token) throws AuthException;

    /**
     * 获取 JwtToken
     *
     * @param userId 用户编号
     * @param scope  作用域
     * @return JwtToken
     */
    JwtToken<SecurityUserDetails> getJwtToken(String userId, String scope);

    /**
     * 未锁定
     *
     * @param username 用户名
     * @return boolean
     */
    boolean nonLocked(String username);

    /**
     * 获取请求头
     *
     * @return String
     */
    String getHeader();

}
