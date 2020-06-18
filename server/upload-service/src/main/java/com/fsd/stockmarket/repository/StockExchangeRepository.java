package com.fsd.stockmarket.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fsd.stockmarket.entity.StockExchange;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchange, Long>{

	public List<StockExchange> findByStockExchange(String stockExchange);
}
