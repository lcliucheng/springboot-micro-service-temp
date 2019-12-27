package com.lc.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.admin.domain.entity.User;
import com.lc.admin.domain.entity.UserRecycleBin;
import com.lc.admin.domain.entity.UserRole;
import com.lc.admin.repository.AdminUserRepository;
import com.lc.admin.repository.UserRecycleBinRepository;
import com.lc.admin.repository.UserRoleRepository;
import com.lc.admin.service.IAdminUserService;
import com.lc.exception.ErrorCode;
import com.lc.exception.ErrorInfo;
import com.lc.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author liucheng
 * @since 2019-12-22
 */
@Service
@DS("admin")
public class AdminUserServiceImpl
        extends ServiceImpl<AdminUserRepository, User>
        implements IAdminUserService {

    private final UserRecycleBinRepository userRecycleBinRepository;
    private final UserRoleRepository userRoleRepository;

    public AdminUserServiceImpl(final UserRecycleBinRepository userRecycleBinRepository,
                                final UserRoleRepository userRoleRepository) {
        this.userRecycleBinRepository = userRecycleBinRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public User findByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 回收
     *
     * @param id 用户主键
     * @return 用户回收站
     * @throws ServiceException 业务异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public UserRecycleBin recovery(Long id) throws ServiceException {
        User user = baseMapper.selectById(id);
        if (Objects.isNull(user)) {
            throw new ServiceException(ErrorInfo.error(ErrorCode.DATA_NON_EXISTENT.getValue(), "用户不存在"));
        }

        boolean result = retBool(baseMapper.deleteById(id));
        if (!result) {
            throw new ServiceException(ErrorInfo.error(ErrorCode.DATA_OPERATE_EXCEPTION.getValue(),
                    "回收异常"));
        }

        UserRecycleBin userRecyclebin = new UserRecycleBin();
        BeanUtil.copyProperties(user, userRecyclebin);

        result = retBool(userRecycleBinRepository.insert(userRecyclebin));
        if (!result) {
            throw new ServiceException(ErrorInfo.error(ErrorCode.DATA_OPERATE_EXCEPTION.getValue(),
                    "回收异常"));
        }
        return userRecyclebin;
    }

    /**
     * 保存用户角色
     *
     * @param userId    用户编号
     * @param userRoles 用户角色
     * @throws ServiceException 业务异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveRoles(Long userId, Set<UserRole> userRoles) throws ServiceException {
        try {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            UpdateWrapper<UserRole> deleteWrapper = new UpdateWrapper<>(userRole);
            userRoleRepository.delete(deleteWrapper);
            userRoles.forEach(userRoleRepository::insert);
        } catch (Exception e) {
            throw new ServiceException(ErrorInfo.error(ErrorCode.DATA_OPERATE_EXCEPTION), e);
        }
    }

    /**
     * 根据用户编号查询用户角色
     *
     * @param userId 用户编号
     * @return 用户角色集合
     */
    @Override
    public List<UserRole> findRoles(Long userId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>(userRole);
        return userRoleRepository.selectList(queryWrapper);
    }

}
