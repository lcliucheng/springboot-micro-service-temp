package com.lc.admin.domain.dto.role;

import com.lc.admin.domain.entity.RoleFunction;
import com.lc.admin.domain.entity.RoleMenu;
import com.lc.admin.domain.vo.MenuVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单资源树
 *
 * @author liucheng
 * @date 2019/4/26
 */
@Data
@Accessors(chain = true)
public class RoleMenuFunctionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单资源树
     */
    private List<MenuVO> menus;

    /**
     * 角色资源关系
     */
    private List<RoleFunction> roleFunctions;

    /**
     * 角色菜单关系
     */
    private List<RoleMenu> roleMenus;

}
