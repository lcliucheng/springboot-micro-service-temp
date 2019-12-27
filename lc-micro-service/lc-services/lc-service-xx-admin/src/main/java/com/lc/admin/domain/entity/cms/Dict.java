package com.lc.admin.domain.entity.cms;

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

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author liucheng
 * @since 2019-12-05
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Dict对象", description = "数据字典")
@TableName("cms_dict")
public class Dict extends BaseFullEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "代码")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "值")
    @TableField(value = "value")
    private String value;

    @ApiModelProperty(value = "名称")
    @TableField(value = "name",condition = SqlCondition.LIKE_RIGHT)
    private String name;

    @ApiModelProperty(value = "描述")
    @TableField(value = "`desc`",condition = SqlCondition.LIKE)
    private String desc;

    @ApiModelProperty(value = "排序号")
    @TableField("sort_no")
    private Integer sortNo;

    @ApiModelProperty(value = "是否禁用 0：否 1：是")
    @TableField("is_disabled")
    private Integer disabled;

    @ApiModelProperty(value = "是否删除 0：否 1：是")
    @TableField("is_deleted")
    private Integer deleted;

}
