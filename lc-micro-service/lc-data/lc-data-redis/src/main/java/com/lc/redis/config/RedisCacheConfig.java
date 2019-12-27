package com.lc.redis.config;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> 缓存配置 </p>
 *
 * @author liucheng
 * @since 2019-12-03
 */
@SuppressWarnings("ALL")
@Configuration
@EnableCaching
public class RedisCacheConfig {

    private final RedissonClient redissonClient;

    public RedisCacheConfig(final RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * Redisson缓存
     *
     * @return Redisson缓存管理器
     */
    @Bean("redisCacheManager")
    public CacheManager cacheManager() {
        return new RedissonSpringCacheManager(redissonClient, "classpath:/cache/redisson.json");
    }

}
