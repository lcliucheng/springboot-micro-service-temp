package com.lc.redis.utils;

import com.lc.uitls.SpringHelper;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

/**
 * @Author daijs
 * @Date 2019/4/22 15:07
 */
public class RedisUtil {

    private static RedissonClient redisson = SpringHelper.getBean("redissonClient");

    public static void setStringWithKey(String key, String value) {
        if (StringUtils.isAnyBlank(key, value)) {
            return;
        }
        RBucket<String> keyObject = redisson.getBucket(key);
        keyObject.set(value);
    }

    public static String getStringWithKey(String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        RBucket<String> keyObject = redisson.getBucket(key);
        return keyObject.get();
    }

    /**
     * 清空指定Map
     *
     * @author yanghy
     * @date 2019/11/13
     */
    public static void clearMap(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        RMap<Object, Object> map = redisson.getMap(key);
        map.clear();
    }


}
