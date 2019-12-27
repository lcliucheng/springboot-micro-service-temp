package com.lc.redis.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.lc.uitls.JsonUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 配置文件
 *
 * @author liucheng
 * @since 2019-12-13
 */
@Configuration
@EnableApolloConfig
@EnableCaching(proxyTargetClass = true)
public class RedissonConfig extends CachingConfigurerSupport {

    /**
     * Redisson 属性
     */
    @Bean
    public RedissonProperties redissonProperties() {
        return new RedissonProperties();
    }

    /**
     * 初始化Redisson客户端
     *
     * @return Redisson客户端
     */
    @Bean(destroyMethod = "shutdown", name = "redissonClient")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec(JsonUtil.getObjectMapper()));
        if(redissonProperties ().getIsCluster ()){
            config.useClusterServers().setScanInterval(redissonProperties().getScanInterval())
                    .addNodeAddress(redissonProperties().getNodeAddress().split(","))
                    .setPingConnectionInterval(1000)
                    .setConnectTimeout(redissonProperties().getConnectTimeout())
                    .setFailedSlaveCheckInterval(redissonProperties().getFailedSlaveCheckInterval());
        }else{
            config.useSingleServer ().setPingConnectionInterval (1000).
                    setAddress (redissonProperties ().getNodeAddress ()).
                    setConnectTimeout(redissonProperties().getConnectTimeout());
        }


        return Redisson.create(config);
    }

}