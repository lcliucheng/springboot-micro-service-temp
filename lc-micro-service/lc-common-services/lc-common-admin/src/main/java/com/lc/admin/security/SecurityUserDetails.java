package com.lc.admin.security;

import com.lc.admin.domain.entity.User;
import com.lc.common.consts.Scope;
import com.lc.enums.common.YesOrNo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 用户详情
 *
 * @author liucheng
 * @since 2019-12-21
 */
public class SecurityUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户
     */
    private User user;

    /**
     * 作用域
     */
    private Scope scope;

    /**
     * 权限
     */
    private List<GrantedAuthority> authorities;

    /**
     * 未锁定
     */
    private boolean nonLocked;

    public SecurityUserDetails() {
    }

    public SecurityUserDetails(User user, List<GrantedAuthority> authorities, boolean nonLocked) {
        this.user = user;
        this.authorities = authorities;
        this.nonLocked = nonLocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(YesOrNo.NO.getValue(), user.getDisabled());
    }

    public User getUser() {
        return user;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
