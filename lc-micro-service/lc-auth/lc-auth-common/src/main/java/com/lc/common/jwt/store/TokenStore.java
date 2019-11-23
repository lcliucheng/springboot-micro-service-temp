package com.lc.common.jwt.store;

import com.lc.common.jwt.JwtToken;

/**
 * 凭证存储
 *
 * @author liucheng
 * @since 2019-11-23
 */
public interface TokenStore {

    /**
     * 保存凭证
     *
     * @param jwtToken JwtToken
     * @return boolean
     */
    boolean saveToken(JwtToken jwtToken);

    /**
     * 续租凭证
     *
     * @param jwtToken JwtToken
     * @return boolean
     */
    boolean reletToken(JwtToken jwtToken);

    /**
     * 获取凭证
     *
     * @param userId 用户编号
     * @param scope  作用域
     * @return JwtToken
     */
    JwtToken getToken(String userId, String scope);

    /**
     * 删除凭证
     *
     * @param userId 用户编号
     * @param scope  作用域
     * @return boolean
     */
    boolean deleteToken(String userId, String scope);

    /**
     * 删除凭证
     *
     * @param userId 用户编号
     * @return boolean
     */
    boolean deleteToken(String userId);

}
