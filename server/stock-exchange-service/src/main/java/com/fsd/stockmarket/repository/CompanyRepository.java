package com.fsd.stockmarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fsd.stockmarket.dto.CompanyDto;
import com.fsd.stockmarket.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{

	@Query(value = "select new com.fsd.stockmarket.dto.CompanyDto(co, cse, se) " +
	   		"from Company co\n" +
	   		"LEFT JOIN co.stockExchanges cse\n" +
	   		"LEFT JOIN cse.stockExchange se\n" +
	   		"where se.id = :stockExchangeId\n")
	public List<CompanyDto> listCompaniesByStockExchangeId(@Param("stockExchangeId") long stockExchangeId);
	
	@Query(value = "select new com.fsd.stockmarket.dto.CompanyDto(co, cse, se) " +
	   		"from Company co\n" +
	   		"LEFT JOIN co.stockExchanges cse\n" +
	   		"LEFT JOIN cse.stockExchange se\n" +
	   		"where se.stockExchange = :stockExchangeName\n")
	public List<CompanyDto> listCompaniesByStockExchangeName(@Param("stockExchangeName") String stockExchangeName);
}
