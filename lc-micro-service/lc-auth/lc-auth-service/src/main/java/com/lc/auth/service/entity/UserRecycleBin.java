package com.lc.auth.service.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户回收站
 *
 * @author liucheng
 * @since 2019-04-16
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserRecycleBin对象", description = "用户回收站")
@TableName("sys_user_recycle_bin")
public class UserRecycleBin implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

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
    @TableField("name")
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

    @ApiModelProperty(value = "扩展字段")
    @TableField(value = "ext", el = "ext, typeHandler=com.zzjr.data.mybatis.handler.JsonMapTypeHandler")
    private Map<String, Object> ext;

    @ApiModelProperty(value = "创建人")
    @TableField("created_by")
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "修改人")
    @TableField("last_modified_by")
    private String lastModifiedBy;

    @ApiModelProperty(value = "修改时间")
    @TableField("last_modified_at")
    private LocalDateTime lastModifiedAt;

    @ApiModelProperty(value = "版本号")
    @TableField("version")
    private Long version;

    @ApiModelProperty(value = "删除人")
    @TableField(value = "deleted_by", fill = FieldFill.INSERT)
    private String deletedBy;

    @ApiModelProperty(value = "删除时间")
    @TableField(value = "deleted_at", fill = FieldFill.INSERT)
    private LocalDateTime deletedAt;

}
