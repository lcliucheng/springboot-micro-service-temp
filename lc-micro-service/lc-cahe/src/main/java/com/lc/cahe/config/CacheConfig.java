package com.lc.cahe.config;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> 缓存配置 </p>
 *
 * @author Tortoise
 * @since 2019-06-03
 */
@SuppressWarnings("ALL")
@Configuration
@EnableCaching
public class CacheConfig {

    private final RedissonClient redissonClient;

    public CacheConfig(final RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * Redisson缓存
     *
     * @return Redisson缓存管理器
     */
    @Bean("cacheManager")
    public CacheManager cacheManager() {
        return new RedissonSpringCacheManager(redissonClient, "classpath:/cache/redisson.json");
    }

}
