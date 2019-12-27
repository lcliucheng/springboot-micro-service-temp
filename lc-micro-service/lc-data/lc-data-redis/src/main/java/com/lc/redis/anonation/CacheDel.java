package com.lc.redis.anonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liucheng
 * @since 2019/12/13 14:30
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheDel {

    /**
     * 需要删除的缓存数组
     */
    String[] value();

}
