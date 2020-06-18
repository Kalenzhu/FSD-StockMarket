package com.fsd.stockmarket.service;

import java.sql.Date;
import java.util.List;

import com.fsd.stockmarket.entity.StockPrice;
import com.fsd.stockmarket.exception.ServiceException;

public interface IStockPriceService {

	public StockPrice getLatestStockPrice(Long companyId, Long stockExchangeId) throws ServiceException;
	
	public List<StockPrice> getPriceListBetween(Long companyId, Long stockExchangeId, Date startDate, Date endDate) throws ServiceException;
}
