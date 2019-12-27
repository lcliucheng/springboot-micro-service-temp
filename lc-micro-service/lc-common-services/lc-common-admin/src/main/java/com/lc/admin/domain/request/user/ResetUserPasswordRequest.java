package com.lc.admin.domain.request.user;

import com.lc.validator.constraints.Password;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 重置用户密码参数
 *
 * @author liucheng
 * @since 2019-12-15
 **/
@ApiModel(value = "ResetUserPasswordRequest", description = " 重置用户密码请求参数")
@Data
@Accessors(chain = true)
public class ResetUserPasswordRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键")
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "新密码")
    @NotBlank
    @Password
    private String password;

}

