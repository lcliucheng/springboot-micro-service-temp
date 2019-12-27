package com.lc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 *
 * @author liucheng
 * @since 2019-12-15
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients

public class AdminApplication {

    public static void main(String[] args) {
        try {
            System.setProperty("es.set.netty.runtime.available.processors", "false");
            SpringApplication.run( AdminApplication.class, args);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}

