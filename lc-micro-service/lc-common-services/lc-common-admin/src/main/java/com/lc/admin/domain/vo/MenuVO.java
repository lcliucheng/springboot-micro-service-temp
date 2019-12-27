package com.lc.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 *
 * @author liucheng
 * @since 2019-12-16
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "Menu对象", description = "菜单")
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父主键")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "序号")
    private Integer sortNo;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "页面地址")
    private String menuPage;

    @ApiModelProperty(value = "访问路径")
    private String path;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "重定向路径")
    private String redirect;

    @ApiModelProperty(value = "是否隐藏  0：否 1：是")
    private Integer hidden;

    @ApiModelProperty(value = "是否下线  0：否 1：是")
    private Integer offline;

    @ApiModelProperty(value = "是否删除  0：否 1：是")
    private Integer deleted;

    /**
     * 子菜单
     */
    private List<MenuVO> children;

    /**
     * 资源
     */
    private List<FunctionVO> functions;

}
