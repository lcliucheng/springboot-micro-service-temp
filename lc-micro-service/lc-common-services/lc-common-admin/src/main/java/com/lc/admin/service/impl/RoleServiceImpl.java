package com.lc.admin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.admin.domain.entity.Role;
import com.lc.admin.domain.entity.RoleFunction;
import com.lc.admin.domain.entity.RoleMenu;
import com.lc.admin.repository.RoleFunctionRepository;
import com.lc.admin.repository.RoleMenuRepository;
import com.lc.admin.repository.RoleRepository;
import com.lc.admin.service.IRoleService;
import com.lc.exception.ErrorCode;
import com.lc.exception.ErrorInfo;
import com.lc.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author liucheng
 * @since 2019-12-23
 */
@Service
@DS("admin")
public class RoleServiceImpl
        extends ServiceImpl<RoleRepository, Role>
        implements IRoleService {

    private final RoleMenuRepository roleMenuRepository;
    private final RoleFunctionRepository roleFunctionRepository;

    public RoleServiceImpl(final RoleMenuRepository roleMenuRepository,
                           final RoleFunctionRepository roleFunctionRepository) {
        this.roleMenuRepository = roleMenuRepository;
        this.roleFunctionRepository = roleFunctionRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveMenus(Long roleId, Set<RoleMenu> roleMenus) throws ServiceException {
        try {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            UpdateWrapper<RoleMenu> deleteWrapper = new UpdateWrapper<>(roleMenu);
            roleMenuRepository.delete(deleteWrapper);
            roleMenus.forEach(roleMenuRepository::insert);
        } catch (Exception e) {
            throw new ServiceException(ErrorInfo.error(ErrorCode.DATA_OPERATE_EXCEPTION), e);
        }
    }

    @Override
    public List<RoleMenu> findMenus(Long roleId) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>(roleMenu);
        return roleMenuRepository.selectList(queryWrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveFunctions(Long roleId, Set<RoleFunction> roleFunctions, Set<RoleMenu> menuIdArray) throws ServiceException {
        try {
            RoleFunction roleFunction = new RoleFunction();
            roleFunction.setRoleId(roleId);
            UpdateWrapper<RoleFunction> deleteWrapper = new UpdateWrapper<>(roleFunction);
            roleFunctionRepository.delete(deleteWrapper);
            roleFunctions.forEach(roleFunctionRepository::insert);

            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            UpdateWrapper<RoleMenu> deleteMenuWrapper = new UpdateWrapper<>(roleMenu);
            roleMenuRepository.delete(deleteMenuWrapper);
            menuIdArray.forEach(roleMenuRepository::insert);
        } catch (Exception e) {
            throw new ServiceException(ErrorInfo.error(ErrorCode.DATA_OPERATE_EXCEPTION), e);
        }
    }

    @Override
    public List<RoleFunction> findFunctions(Long roleId) {
        RoleFunction roleFunction = new RoleFunction();
        roleFunction.setRoleId(roleId);
        QueryWrapper<RoleFunction> queryWrapper = new QueryWrapper<>(roleFunction);
        return roleFunctionRepository.selectList(queryWrapper);
    }

}
