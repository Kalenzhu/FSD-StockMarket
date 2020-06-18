package com.fsd.stockmarket.controller;

import com.fsd.stockmarket.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

/**
 * provider some info about user
 * @author zuogan
 * @date 2020-04-03
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class TestController {

    @GetMapping("/user/current")
    public Result<Principal> getUser(Principal user) {
        log.info("/user/current, name：{}",user.getName());
        return new Result<Principal>(user);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/test1")
    public Result<String> test1() {
        log.info("/user/test1 run");
        return new Result<String>("This is result from auth server /user/test1, it is isAuthenticated()");
    }
    
    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/user/test2")
    public Result<String> test2() {
        log.info("/user/test2 run");
        return new Result<String>("This is result from auth server /user/test2, it is hasAnyRole('admin')");
    }
        
    @PreAuthorize("hasAnyRole('user')")
    @GetMapping("/user/test3")
    public Result<String> test3() {
        log.info("/user/test3 run");
        return new Result<String>("This is result from auth server /user/test3, it is hasAnyRole('user')");
    }
}
