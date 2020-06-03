package com.fsd.stockmarket.service;

import java.util.List;

import com.fsd.stockmarket.entity.StockExchange;

public interface IStockExchangeService {

	public List<StockExchange> findAll();
}
