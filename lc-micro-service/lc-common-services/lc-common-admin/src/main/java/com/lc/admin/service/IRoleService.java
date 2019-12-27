package com.lc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.admin.domain.entity.Role;
import com.lc.admin.domain.entity.RoleFunction;
import com.lc.admin.domain.entity.RoleMenu;
import com.lc.exception.ServiceException;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author liucheng
 * @since 2019-12-05
 */
public interface IRoleService extends IService<Role> {

    /**
     * 保存角色菜单
     *
     * @param roleId    角色编号
     * @param roleMenus 角色菜单
     * @throws ServiceException 业务异常
     */
    void saveMenus(Long roleId, Set<RoleMenu> roleMenus) throws ServiceException;

    /**
     * 根据角色编号查询角色菜单
     *
     * @param roleId 角色编号
     * @return 角色菜单集合
     */
    List<RoleMenu> findMenus(Long roleId);

    /**
     * 保存角色资源
     *
     * @param roleId        角色编号
     * @param roleFunctions 角色资源
     * @param menuIdArray
     * @throws ServiceException 业务异常
     */
    void saveFunctions(Long roleId, Set<RoleFunction> roleFunctions, Set<RoleMenu> menuIdArray) throws ServiceException;

    /**
     * 根据角色编号查询角色资源
     *
     * @param roleId 角色编号
     * @return 角色资源集合
     */
    List<RoleFunction> findFunctions(Long roleId);

}
