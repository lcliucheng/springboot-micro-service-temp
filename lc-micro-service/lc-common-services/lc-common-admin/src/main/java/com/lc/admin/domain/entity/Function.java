package com.lc.admin.domain.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lc.admin.domain.vo.FunctionVO;
import com.lc.admin.security.CustomConfigAttribute;
import com.lc.data.mybatis.domain.BaseFullEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 资源
 *
 * @author liucheng
 * @since 2019-12-16
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Function对象", description = "资源")
@TableName("sys_function")
public class Function extends BaseFullEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "菜单主键")
    @TableField("menu_id")
    private Long menuId;

    @ApiModelProperty(value = "菜单名称")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "序号")
    @TableField("sort_no")
    private Integer sortNo;

    @ApiModelProperty(value = "方法")
    @TableField("method")
    private String method;

    @ApiModelProperty(value = "地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "描述")
    @TableField("`desc`")
    private String desc;

    @ApiModelProperty(value = "是否下线  0：否 1：是")
    @TableField("is_offline")
    private Integer offline;

    @ApiModelProperty(value = "是否正则  0：否 1：是")
    @TableField("is_regex")
    private Integer regex;

    @ApiModelProperty(value = "是否授权  0：否 1：是")
    @TableField("is_authenticated")
    private Integer authenticated;

    @ApiModelProperty(value = "是否删除  0：否 1：是")
    @TableField("is_deleted")
    private Integer deleted;

    /**
     * 转化成FunctionVO
     *
     * @return FunctionVO
     */
    public FunctionVO convert() {
        FunctionVO function = new FunctionVO ();
        BeanUtil.copyProperties (this, function);
        return function;
    }

    /**
     * 转化成CustomConfigAttribute
     *
     * @return CustomConfigAttribute
     */
    public CustomConfigAttribute convertCustomConfigAttribute() {
        return new CustomConfigAttribute (this);
    }

}
