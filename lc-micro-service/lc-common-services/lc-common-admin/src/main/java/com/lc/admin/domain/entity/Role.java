package com.lc.admin.domain.entity;

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

/**
 * <p> 角色 </p>
 *
 * @author liucheng
 * @since 2019-12-16
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Role对象", description = "角色")
@TableName("sys_role")
public class Role extends BaseFullEntity {

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "描述")
    @TableField("`desc`")
    private String desc;

    @ApiModelProperty(value = "是否禁用  0：否 1：是")
    @TableField("is_disabled")
    private Integer disabled;

    @ApiModelProperty(value = "是否删除  0：否 1：是")
    @TableField("is_deleted")
    private Integer deleted;

}
