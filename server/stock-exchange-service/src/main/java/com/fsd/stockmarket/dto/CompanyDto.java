package com.fsd.stockmarket.dto;

import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fsd.stockmarket.entity.Company;
import com.fsd.stockmarket.entity.CompanyStockExchange;
import com.fsd.stockmarket.entity.StockExchange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"stockExchanges"})
public class CompanyDto extends Company {

	private static final long serialVersionUID = 4299699641016041237L;

	private String companyCode;
	
	private StockExchange stockExchange;
	
	public CompanyDto(Company company, CompanyStockExchange cse, StockExchange se) {
		BeanUtils.copyProperties(company, this);
		this.companyCode = cse.getCompanyCode();
		this.stockExchange = se;
	}
}
