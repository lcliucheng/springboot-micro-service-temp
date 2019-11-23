package com.lc.common.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 凭证对象
 *
 * @author liucheng
 * @since 2019-11-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
public class JwtToken<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 登陆手机号
     */
    private String mobile;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 授权作用域
     */
    private String scope;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 过期时间，单位（秒）
     */
    private Integer expiresIn;

    /**
     * 用户
     */
    private T userDetails;

}
