package com.fsd.stockmarket.service;

import java.util.List;

import com.fsd.stockmarket.dto.CompanyDto;
import com.fsd.stockmarket.entity.StockExchange;

public interface IStockExchangeService {

	public List<StockExchange> findAll();
	
	public List<CompanyDto> listCompaniesByStockExchangeId(long stockExchangeId);
	
	public List<CompanyDto> listCompaniesByStockExchangeName(String stockExchangeName);
}
