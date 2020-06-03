package com.fsd.stockmarket.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fsd.stockmarket.entity.StockExchange;
import com.fsd.stockmarket.repository.StockExchangeRepository;
import com.fsd.stockmarket.service.IStockExchangeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StockExchangeServiceImpl implements IStockExchangeService {

	@Autowired
	private StockExchangeRepository stockExchangeRepository;
	
	@Override
	public List<StockExchange> findAll() {
		List<StockExchange> result = this.stockExchangeRepository.findAll();
		return result;
	}
}
