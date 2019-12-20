package com.lc.auth.service.security;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.lc.auth.service.entity.RoleFunction;
import com.lc.auth.service.entity.User;
import com.lc.auth.service.entity.UserRole;
import com.lc.auth.service.security.handler.AuthenticationHandler;
import com.lc.auth.service.service.IAdminUserService;
import com.lc.auth.service.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 配置用户权限认证
 *
 * <p>
 * 当用户登录时会进入此类的loadUserByUsername方法对用户进行验证，验证成功后会被保存在当前回话的principal对象中
 * 系统获取当前登录对象信息方法 WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 异常信息：
 * UsernameNotFoundException     用户找不到
 * BadCredentialsException       坏的凭据
 * AccountExpiredException       账户过期
 * LockedException               账户锁定
 * DisabledException             账户不可用
 * CredentialsExpiredException   证书过期
 * </p>
 *
 * @author liucheng
 * @since 2019-05-21
 */
@Slf4j
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final IAdminUserService userService;
    private final IRoleService roleService;
    private final AuthenticationHandler authenticationHandler;

    public CustomUserDetailsServiceImpl(final IAdminUserService userService,
                                        final IRoleService roleService,
                                        final AuthenticationHandler authenticationHandler) {
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationHandler = authenticationHandler;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }

        List<GrantedAuthority> authorities = Lists.newLinkedList();
        List<UserRole> userRoles = userService.findRoles(user.getId());
        if (CollectionUtil.isNotEmpty(userRoles)) {
            userRoles.forEach(userRole -> {
                List<RoleFunction> roleFunctions = roleService.findFunctions(userRole.getRoleId());
                roleFunctions.forEach(roleFunction -> authorities.add((GrantedAuthority) () -> roleFunction.getFunctionId().toString()));
            });
        }

        return new SecurityUserDetails(user, authorities, authenticationHandler.nonLocked(username));
    }

}
