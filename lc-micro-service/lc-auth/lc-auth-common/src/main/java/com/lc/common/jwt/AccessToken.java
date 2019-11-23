package com.lc.common.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 凭证
 *
 * @author liucheng
 * @since 2019-11-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 授权凭证
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 过期时间，单位（秒）
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;

}
