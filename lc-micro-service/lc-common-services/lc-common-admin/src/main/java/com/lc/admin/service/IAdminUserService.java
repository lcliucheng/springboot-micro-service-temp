package com.lc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.admin.domain.entity.User;
import com.lc.admin.domain.entity.UserRecycleBin;
import com.lc.admin.domain.entity.UserRole;
import com.lc.exception.ServiceException;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author liucheng
 * @since 2019-12-05
 */
public interface IAdminUserService extends IService<User> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);

    /**
     * 回收
     *
     * @param id 用户主键
     * @return 用户回收站
     * @throws ServiceException 业务异常
     */
    UserRecycleBin recovery(Long id) throws ServiceException;

    /**
     * 保存用户角色
     *
     * @param userId    用户编号
     * @param userRoles 用户角色
     * @throws ServiceException 业务异常
     */
    void saveRoles(Long userId, Set<UserRole> userRoles) throws ServiceException;

    /**
     * 根据用户编号查询用户角色
     *
     * @param userId 用户编号
     * @return 用户角色集合
     */
    List<UserRole> findRoles(Long userId);

}
