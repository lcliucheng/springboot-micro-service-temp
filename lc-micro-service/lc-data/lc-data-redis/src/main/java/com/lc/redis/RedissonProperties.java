package com.lc.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


/**
 * Redisson配置属性
 *
 * @author liucheng
 * @since 2019-04-13
 */
@Data
public class RedissonProperties {

    /**
     * 集群节点"redis://127.0.0.1:7000,redis://127.0.0.1:7001"
     */
    @Value("${redis.cluster.nodeAddress}")
    private String nodeAddress;

    /**
     * 集群状态扫描间隔时间，单位：毫秒
     */
    @Value("${redis.cluster.scanInterval:5000}")
    private Integer scanInterval;

    /**
     * 连接超时时间，单位：毫秒
     */
    @Value("${redis.cluster.connectTimeout:3000}")
    private Integer connectTimeout;

    /**
     * 无法执行命令的redis slave节点从可用节点的内部列表中排除的间隔时间，单位：毫秒
     */
    @Value("${redis.cluster.failedSlaveCheckInterval:180000}")
    private Integer failedSlaveCheckInterval;

}
