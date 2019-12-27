package com.lc.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户角色
 *
 * @author liucheng
 * @since 2019-12-16
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserRole对象", description = "用户角色")
@TableName("sys_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "用户主键")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "角色主键")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

}
