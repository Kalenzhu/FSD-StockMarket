package com.fsd.stockmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zuogan
 * @date 2020-03-31
 */
@SpringBootApplication
@EnableDiscoveryClient
//在客户端开启feign，封装http请求
@EnableFeignClients(basePackages = "com.fsd.stockmarket")
@EnableZuulProxy
public class ZuulProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulProxyApplication.class, args);
    }
}
