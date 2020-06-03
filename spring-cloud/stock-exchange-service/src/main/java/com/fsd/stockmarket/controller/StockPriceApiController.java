package com.fsd.stockmarket.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fsd.stockmarket.entity.StockPrice;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.pojo.Result;
import com.fsd.stockmarket.service.IStockPriceService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/stockprice")
public class StockPriceApiController {

	@Autowired
	private IStockPriceService stockPriceService;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	@GetMapping("/sayHello")
	public Result<String> hello() {
		log.info("stock price service /api/stockprice/sayHello run");
		return new Result<String>("hello, this is the greet from stock price service /api/stockprice/sayHello, it's a oauth protect resource.");
	}
	
	@GetMapping("/{companyId}/stockexchange/{stockExchangeId}/latest")
    public Result<StockPrice> getLatestStockPrice(
    		@PathVariable(value="companyId",required = true) long companyId,
    		@PathVariable(value="stockExchangeId",required = true) long stockExchangeId) throws ServiceException {
		log.info("stock price service /api/stockprice/"+companyId+"/stockexchange/"+stockExchangeId+"/latest run");
		return new Result<StockPrice>(this.stockPriceService.getLatestStockPrice(companyId, stockExchangeId));
	}
	
	@GetMapping("/{companyId}/stockexchange/{stockExchangeId}/list")
    public Result<List<StockPrice>> getPriceListBetween(
    		@PathVariable(value="companyId",required = true) long companyId,
    		@PathVariable(value="stockExchangeId",required = true) long stockExchangeId,
    		@RequestParam(value="startDateStr",required = false) String startDateStr,
    		@RequestParam(value="endDateStr",required = false) String endDateStr) throws ServiceException {
		log.info("stock price service /api/stockprice/"+companyId+"/stockexchange/"+stockExchangeId+"/list run");
		Date startDate = null;
		if(!StringUtils.isEmpty(startDateStr)) {
			try {
				startDate = new Date(this.format.parse(startDateStr).getTime());
			} catch (ParseException e) {
				throw  new ServiceException(ServiceErrorCode.BAD_REQUEST, "start date format error, correct format: yyyy-mm-ddThh:mm:ss");
			}
		}
		Date endDate = null;
		if(!StringUtils.isEmpty(endDateStr)) {
			try {
				endDate = new Date(this.format.parse(endDateStr).getTime());
			} catch (ParseException e) {
				throw  new ServiceException(ServiceErrorCode.BAD_REQUEST, "end date format error, correct format: yyyy-mm-ddThh:mm:ss");
			}
		}
		return new Result<List<StockPrice>>(this.stockPriceService.getPriceListBetween(companyId, stockExchangeId, startDate, endDate));
    }
}
