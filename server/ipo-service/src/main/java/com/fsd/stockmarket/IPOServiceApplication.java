package com.fsd.stockmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableDiscoveryClient
@SpringBootApplication
//@EnableGlobalMethodSecurity(
//		securedEnabled = true,
//		jsr250Enabled = true,
//		prePostEnabled = true
//)
public class IPOServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IPOServiceApplication.class, args);
    }

}
