package com.lc.common.config;


import com.lc.enums.common.YesOrNo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

/**
 * 授权配置属性
 *
 * @author liucheng
 * @since 2019-11-23
 */
@Data
public class AuthProperties {


    /**
     * 发行人
     */
    @Value("${lc.auth.issuer:lcAdmin}")
    private String issuer;

    /**
     * 请求头
     */
    @Value("${lc.auth.header:Authorization}")
    private String header;

    /**
     * 过期时间
     */
    @Value("${lc.auth.expiresIn:7200}")
    private Integer expiresIn;

    /**
     * 续租，如果续租，每次操作之后重置过期时间，模拟session过期,0:不续租 1：续租
     */
    @Value("${lc.auth.relet:0}")
    private Integer relet;

    /**
     * 登录失败前缀
     */
    @Value("${lc.auth.loginFailPrefix:loginFailPrefix}")
    private String loginFailPrefix;

    /**
     * 登录失败次数
     */
    @Value("${lc.auth.loginFailNum:5}")
    private Integer loginFailNum;

    /**
     * 是否续租
     *
     * @return true续租，false不续租
     */
    public boolean isRelet() {
        return Objects.equals (YesOrNo.YES.getValue (), relet);
    }

}
