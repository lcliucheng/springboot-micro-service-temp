package com.lc.auth.service.security;

import com.google.common.collect.Lists;
import com.zzjr.auth.service.entity.Function;
import com.zzjr.auth.service.service.IFunctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 自定义加载资源与权限的对应关系
 *
 * @author liucheng
 * @since 2019-05-21
 */
@Slf4j
@Component
public class CustomInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final IFunctionService functionService;

    public CustomInvocationSecurityMetadataSource(final IFunctionService functionService) {
        this.functionService = functionService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        String method = request.getMethod();
        String url = request.getRequestURI().replaceAll("//+", "/");

        List<ConfigAttribute> configAttributes = Lists.newArrayList();
        Collection<CustomConfigAttribute> allConfigAttributes = getAllCustomConfigAttributes();

        for (CustomConfigAttribute configAttribute : allConfigAttributes) {
            if (Objects.equals(method, configAttribute.getMethod())) {
                boolean equal;
                if (configAttribute.isRegex()) {
                    Pattern pattern = Pattern.compile(configAttribute.getUrl());
                    equal = pattern.matcher(url).matches();
                } else {
                    equal = Objects.equals(configAttribute.getUrl(), url);
                }

                if (equal) {
                    configAttributes.add(configAttribute);
                }
            }
        }

        return configAttributes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return (Collection) getAllCustomConfigAttributes();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * 获取自定义配置
     *
     * @return List<CustomConfigAttribute>
     */
    private List<CustomConfigAttribute> getAllCustomConfigAttributes() {
        List<Function> list = functionService.list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        return list.stream().map(Function::convertCustomConfigAttribute).collect(Collectors.toList());
    }

}
