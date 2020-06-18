package com.fsd.stockmarket.dto;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fsd.stockmarket.entity.Company;
import com.fsd.stockmarket.entity.CompanyStockExchange;
import com.fsd.stockmarket.entity.Sector;
import com.fsd.stockmarket.entity.StockExchange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zuogan
 * @date 2020-04-16
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"companySector", "stockExchanges"})
public class CompanyDto extends Company {

	private static final long serialVersionUID = 7072917694387318518L;
    
	private Sector sector;
	
	private String companyCode;
	
	private StockExchange stockExchange;
	
	public CompanyDto(Company company, Sector sector, CompanyStockExchange cse, StockExchange se) {
		BeanUtils.copyProperties(company, this);
		this.sector = sector;
		this.companyCode = cse.getCompanyCode();
		this.stockExchange = se;
	}
}
