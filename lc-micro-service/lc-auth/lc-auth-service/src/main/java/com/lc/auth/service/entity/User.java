package com.lc.auth.service.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lc.data.mybatis.domain.BaseFullEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 用户
 *
 * @author liucheng
 * @since 2019-04-16
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "User对象", description = "用户")
@TableName("sys_user")
public class User extends BaseFullEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "用户类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "username",condition = SqlCondition.LIKE)
    private String username;

    @ApiModelProperty(value = "商务用户名")
    @TableField("busi_user_name")
    private String busiUserName;

    @ApiModelProperty(value = "商务姓名")
    @TableField("busi_name")
    private String busiName;

    @ApiModelProperty(value = "推广链接")
    @TableField("push_link")
    private String pushLink;

    @ApiModelProperty(value = "手机号码")
    @TableField(value = "mobile", el = "mobile, typeHandler=com.zzjr.data.mybatis.handler.DataSecurityTypeHandler")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    @TableField(value = "email", el = "email, typeHandler=com.zzjr.data.mybatis.handler.DataSecurityTypeHandler")
    private String email;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "盐值")
    @TableField("salt")
    private String salt;

    @ApiModelProperty(value = "名称")
    @TableField(value = "name",condition = SqlCondition.LIKE)
    private String name;

    @ApiModelProperty(value = "性别 N：未知 M：男性 F：女性")
    @TableField("gender")
    private String gender;

    @ApiModelProperty(value = "生日")
    @TableField("birthday")
    private LocalDate birthday;

    @ApiModelProperty(value = "头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "国家")
    @TableField("country")
    private String country;

    @ApiModelProperty(value = "省份")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "城市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "区县")
    @TableField("district")
    private String district;

    @ApiModelProperty(value = "详细地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "是否禁用 0：否 1：是")
    @TableField("is_disabled")
    private Integer disabled;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

}
