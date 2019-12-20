package com.lc.auth.service.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;

/**
 * 自定义资源权限认证器
 *
 * <p>
 * 认证用户是否拥有所请求资源的权限，
 * 接口AccessDecisionManager也是必须实现的。 decide方法里面写的就是授权策略了，需要什么策略，可以自己写其中的策略逻辑
 * 认证通过就返回，不通过抛异常就行了，spring security会自动跳到权限不足处理类（WebSecurityConfig 类中 配置文件上配的）
 * </p>
 *
 * @author liucheng
 * @since 2019-05-21
 */
@Slf4j
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    @SuppressWarnings("unchecked")
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();

        if (CollectionUtils.isEmpty(configAttributes)) {
            return;
        }

        Collection<CustomConfigAttribute> customConfigAttributes = (Collection) configAttributes;

        for (CustomConfigAttribute configAttribute : customConfigAttributes) {
            if (configAttribute.isAuthenticated() && authentication instanceof UsernamePasswordAuthenticationToken) {
                return;
            }

            String attribute = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                //grantedAuthority 为用户所被赋予的权限。 attribute 为访问相应的资源应该具有的权限。
                //判断两个请求的url的权限和用户具有的权限是否相同，如相同，允许访问
                if (Objects.equals(attribute.trim(), grantedAuthority.getAuthority().trim())) {
                    //匹配到对应的角色，则允许通过
                    return;
                }
            }
        }

        throw new AccessDeniedException(String.format("无权访问[%s]", request.getRequestURI()));
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
