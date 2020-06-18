package com.fsd.stockmarket.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.stockmarket.dto.CompanyDto;
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
	
	@GetMapping("/{stockExchangeId}/listCompaniesById")
	public Result<List<CompanyDto>> listCompaniesByStockExchangeId(
			@PathVariable(value="stockExchangeId",required = true) long stockExchangeId) {
		log.info("stock exchange service /api/stockexchange/{}/listCompaniesById run", stockExchangeId);
	    return new Result<List<CompanyDto>>(this.stockExchangeService.listCompaniesByStockExchangeId(stockExchangeId));
	}
	
	@GetMapping("/{stockExchangeName}/listCompaniesByName")
	public Result<List<CompanyDto>> listCompaniesByStockExchangeName(
			@PathVariable(value="stockExchangeName",required = true) String stockExchangeName) {
		log.info("stock exchange service /api/stockexchange/{}/listCompaniesByName run", stockExchangeName);
	    return new Result<List<CompanyDto>>(this.stockExchangeService.listCompaniesByStockExchangeName(stockExchangeName));
	}
}
