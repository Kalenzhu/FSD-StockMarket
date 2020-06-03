package com.fsd.stockmarket.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fsd.stockmarket.entity.StockExchange;
import com.fsd.stockmarket.pojo.Result;
import com.fsd.stockmarket.service.IStockExchangeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/stockexchange")
public class StockExchangeApiController {

	@Autowired
	private IStockExchangeService stockExchangeService;
	
	@GetMapping("/sayHello")
	public Result<String> hello() {
		log.info("stock exchange service /api/stockexchange/sayHello run");
		return new Result<String>("hello, this is the greet from stock exchange service /api/stockexchange/sayHello, it's a oauth protect resource.");
	}
	    
	@GetMapping("/list")
	public Result<List<StockExchange>> findAll() {
		log.info("stock exchange service /api/stockexchange/list run");
	    return new Result<List<StockExchange>>(this.stockExchangeService.findAll());
	}
}
