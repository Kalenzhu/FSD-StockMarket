package com.fsd.stockmarket.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fsd.stockmarket.pojo.Result;
//采用feign方法调用远程服务，必须编写一个接口，此接口声明需要调用远端服务的声明方法
//feign根据GetMapping中的路径加上对应服务的地址找到对应的方法，如类上注解的服务名+GetMapping中的路径

@FeignClient(name = "auth-service") //name是指要请求的服务名称
public interface AuthCheckFeignClient {
	
    // verify admin role
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    Result<String> isAdmin(@RequestHeader(name = "Authorization") String authHeader);

    // verify token
    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    Result<String> hasToken(@RequestHeader(name = "Authorization") String authHeader);

}
