package com.lc.admin.domain.request.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户绑定菜单资源接口数据
 *
 * @author liucheng
 * @date 2019/4/28
 */
@Data
@Accessors(chain = true)
public class BindMenuFuncRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源编号列表")
    private String functs;

    @ApiModelProperty(value = "菜单编号列表")
    private String menus;

    @ApiModelProperty(value = "用户编号")
    private String roleId;

}
