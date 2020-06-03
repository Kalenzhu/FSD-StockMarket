package com.fsd.stockmarket.controller.rest;

import com.fsd.stockmarket.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zuogan
 * @date 2020-04-10
 */
@Slf4j
@RestController
@RequestMapping("/rest/company")
public class CompanyRestController {

    @GetMapping("/hello")
    public Result<String> hello() {
      log.info("company service /rest/company/hello run");
      return new Result<String>("hello, this is the greet from company service /rest/company/hello, it's a common api.");
    }

}
