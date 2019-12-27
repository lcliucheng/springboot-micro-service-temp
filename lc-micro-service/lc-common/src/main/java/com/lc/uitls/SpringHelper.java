package com.lc.uitls;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Spring 帮助类
 *
 * @author Tortoise
 * @since 2019-12-15
 **/
@SuppressWarnings({"WeakerAccess", "unused"})
@Component
public class SpringHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    /**
     * 获取applicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        if (SpringHelper.applicationContext == null) {
            SpringHelper.applicationContext = applicationContext;
        }
    }

    /**
     * 通过名称获取Bean.
     *
     * @param name 名称
     * @param <T>  返回对象类型
     * @return T
     */
    @SuppressWarnings(value = {"unchecked"})
    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz 类型
     * @param <T>   返回对象类型
     * @return T
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过class获取Beans.
     *
     * @param <T>   返回对象类型
     * @param clazz 类型
     * @return T
     */
    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name  名称
     * @param clazz 类型
     * @param <T>   返回对象类型
     * @return T
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}