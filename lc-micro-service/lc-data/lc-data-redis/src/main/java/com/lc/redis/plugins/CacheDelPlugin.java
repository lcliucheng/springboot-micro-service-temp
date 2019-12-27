package com.lc.redis.plugins;

import cn.hutool.core.util.ArrayUtil;
import com.lc.redis.anonation.CacheDel;
import com.lc.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 *  删除缓存拦截器
 * @author liucheng
 * @since 2019/12/13 14:35
 */
@Slf4j
@Component
public class CacheDelPlugin implements MethodInterceptor {

    /**
     *  需要删除缓存的方法,不细分至同名重载方法
     */
    private static final String[] CACHE_DEL_METHOD = {"save","saveBatch","saveOrUpdate","saveOrUpdateBatch",
            "updateById","update","updateBatchById",
            "remove","removeById","removeByIds","removeByMap"};

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //首先判断方法上是否有注解，如果有，删除相关缓存
        Object target = methodInvocation.getThis();
        Class<?> targetClass = target.getClass();
        Method targetClassMethod = targetClass.getMethod(methodInvocation.getMethod().getName(), methodInvocation.getMethod().getParameterTypes());
        CacheDel cacheDel = targetClassMethod.getAnnotation(CacheDel.class);
        CacheDel objCacheDel = targetClass.getAnnotation(CacheDel.class);
        if(Objects.nonNull(cacheDel)) {
            this.delCache(cacheDel);
        } else if(Objects.nonNull(objCacheDel)) {
            //如果是类上注解，判断当前方法是否在公共删除方法内
            if(ArrayUtil.contains(CACHE_DEL_METHOD,targetClassMethod.getName())) {
                this.delCache(objCacheDel);
            }
        }

        return methodInvocation.proceed();
    }

    /**
     *  删除缓存
     * @param cacheDel 缓存注解
     */
    private void delCache(CacheDel cacheDel) {
        String[] delKey = cacheDel.value();
        for(String key:delKey) {
            RedisUtil.clearMap(key);
        }
    }
}
