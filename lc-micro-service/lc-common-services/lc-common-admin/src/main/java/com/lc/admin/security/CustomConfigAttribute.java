package com.lc.admin.security;

import com.lc.admin.domain.entity.Function;
import com.lc.enums.common.YesOrNo;
import lombok.Data;
import org.springframework.security.access.ConfigAttribute;

import java.util.Objects;

/**
 * 资源属性
 *
 * @author liucheng
 * @since 2019-12-21
 */
@Data
public class CustomConfigAttribute implements ConfigAttribute {

    private static final long serialVersionUID = 1L;

    /**
     * 资源
     */
    private Function function;

    public CustomConfigAttribute(Function function) {
        this.function = function;
    }

    @Override
    public String getAttribute() {
        return function.getId().toString();
    }

    /**
     * 获取方法
     *
     * @return String
     */
    public String getMethod() {
        return function.getMethod();
    }

    /**
     * 获取地址
     *
     * @return String
     */
    public String getUrl() {
        return function.getUrl();
    }

    /**
     * 是否授权
     *
     * @return boolean
     */
    public boolean isAuthenticated() {
        return Objects.equals(YesOrNo.YES.getValue(), function.getAuthenticated());
    }

    /**
     * 是否正则
     *
     * @return boolean
     */
    public boolean isRegex() {
        return Objects.equals(YesOrNo.YES.getValue(), function.getRegex());
    }

}
