package com.fsd.stockmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zuogan
 * @date 2020-04-02
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class AuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }

    @RequestMapping("/hello")
	public String hello(){
		return "Hello, this is the greet from authentication service";
	}
    
    @RequestMapping("/oauth/test")
   	public String oauthTest(){
   		return "Hello, this is the greet from oauth test";
   	}
}
