package com.fsd.stockmarket.service.impl;

import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import com.fsd.stockmarket.entity.Company;
import com.fsd.stockmarket.entity.CompanyStockExchange;
import com.fsd.stockmarket.entity.StockPrice;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.repository.CompanyRepository;
import com.fsd.stockmarket.service.IStockPriceService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StockPriceServiceImpl implements IStockPriceService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public StockPrice getLatestStockPrice(Long companyId, Long stockExchangeId) throws ServiceException {
		Company company = this.companyRepository.findById(Long.valueOf(companyId))
			.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "company id not found"));
		List<CompanyStockExchange> cseList = company.getStockExchanges().stream().filter(item -> item.getStockExchange().getId() == stockExchangeId.longValue())
			.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(cseList)) {
			CompanyStockExchange cse = cseList.get(0);
			List<StockPrice> priceList = cse.getStockPrice().stream().sorted(
				Comparator.comparing(StockPrice::getPriceDate, Comparator.reverseOrder())
				.thenComparing(StockPrice::getPriceTime, Comparator.reverseOrder()))
				.collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(priceList)) {
				return priceList.get(0);
			}
		}
		return null;
	}
	
	@Override
	public List<StockPrice> getPriceListBetween(Long companyId, Long stockExchangeId, Date startDate, Date endDate) throws ServiceException {
		Company company = this.companyRepository.findById(Long.valueOf(companyId))
			.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "company id not found"));
		
		List<CompanyStockExchange> cseList = company.getStockExchanges().stream().filter(item -> item.getStockExchange().getId() == stockExchangeId.longValue())
			.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(cseList)) {
			CompanyStockExchange cse = cseList.get(0);
			
			List<StockPrice> priceList = cse.getStockPrice().stream().filter(stockPrice -> {
					boolean result1 = true;
					boolean result2 = true;
					if(startDate != null) {
						result1 = stockPrice.after(startDate);
					}
					if(endDate != null) {
						result2 = stockPrice.before(endDate);
					}
					return result1 && result2;
				}).sorted(Comparator.comparing(StockPrice::getPriceDate, Comparator.reverseOrder())
						.thenComparing(StockPrice::getPriceTime, Comparator.reverseOrder()))
				.collect(Collectors.toList());
			return priceList;
		}
		return Lists.newArrayList();
	}
}
