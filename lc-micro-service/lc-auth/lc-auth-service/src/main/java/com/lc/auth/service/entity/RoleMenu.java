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
import java.time.LocalDateTime;

/**
 * 角色菜单
 *
 * @author liucheng
 * @since 2019-04-16
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "RoleMenu对象", description = "角色菜单")
@TableName("sys_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "角色主键")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "菜单主键")
    @TableField("menu_id")
    private Long menuId;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

}
