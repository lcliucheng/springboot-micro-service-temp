package com.lc.common;


import com.lc.common.jwt.JwtToken;


/**
 * 授权凭证上下文
 *
 * @author liucheng
 * @since 2019-11-23
 */
public class AuthContext {

    public static final String CURRENT_AUDITOR = "Current-Auditor";

    private static ThreadLocal<JwtToken<Object>> threadLocal = new ThreadLocal<>();


    /**
     * 获取JwtToken
     *
     * @return JwtToken<Object>
     */
    public static JwtToken<Object> getJwtToken() {
        return threadLocal.get();
    }

    /**
     * 设置JwtToken
     *
     * @param jwtToken JwtToken<Object>
     */
    public static void setJwtToken(JwtToken<Object> jwtToken) {
        threadLocal.set(jwtToken);
    }


    /**
     * 清除上下文
     */
    public static void clear() {
        threadLocal.remove();
    }




}
