package com.lc.auth.service.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 资源
 *
 * @author liucheng
 * @since 2019-05-23
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "FunctionVO对象", description = "资源")
public class FunctionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "菜单主键")
    private Long menuId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "序号")
    private Integer sortNo;

    @ApiModelProperty(value = "方法")
    private String method;

    @ApiModelProperty(value = "地址")
    private String url;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "是否下线  0：否 1：是")
    private Integer offline;

    @ApiModelProperty(value = "是否正则  0：否 1：是")
    private Integer regex;

    @ApiModelProperty(value = "是否授权  0：否 1：是")
    private Integer authenticated;

    @ApiModelProperty(value = "是否删除  0：否 1：是")
    private Integer deleted;

}
