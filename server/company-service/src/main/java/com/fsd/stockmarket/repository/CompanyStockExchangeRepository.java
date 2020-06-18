package com.fsd.stockmarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.fsd.stockmarket.entity.CompanyStockExchange;

@Repository
public interface CompanyStockExchangeRepository extends JpaRepository<CompanyStockExchange, Long> {

	public List<CompanyStockExchange> findByStockExchangeIdAndCompanyCode(Long stockExchangeId, String companyCode);
	
	
	
	public List<CompanyStockExchange> findByCompanyIdIn(List<Long> companyIds);

	public List<CompanyStockExchange> findByCompanyId(Long companyId);
	
	public Integer deleteByCompanyId(Long companyId);
	
	public List<CompanyStockExchange> findByCompanyIdAndStockExchangeId(Long companyId, Long stockExchangeId);
	
	public List<CompanyStockExchange> findByCompanyIdNotAndStockExchangeIdAndCompanyCode(Long companyId, Long stockExchangeId, String companyCode);
	
	public long deleteByCompanyIdAndStockExchangeId(Long companyId, Long stockExchangeId);
}
