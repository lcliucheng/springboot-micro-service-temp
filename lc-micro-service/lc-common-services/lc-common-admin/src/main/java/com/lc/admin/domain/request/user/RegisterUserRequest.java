package com.lc.admin.domain.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 注册用户参数
 *
 * @author liucheng
 * @since 2019-12-15
 **/
@ApiModel(value = "RegisterUserRequest", description = "注册用户请求参数")
@Data
@Accessors(chain = true)
public class RegisterUserRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户类型")
    private String type;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商务用户名")
    private String busiUserName;

    @ApiModelProperty(value = "商务姓名")
    private String busiName;

    @ApiModelProperty(value = "推广链接")
    private String pushLink;

    @ApiModelProperty(value = "是否禁用 0：否 1：是")
    private Integer disabled;

    @ApiModelProperty(value = "备注")
    private String remark;

}
