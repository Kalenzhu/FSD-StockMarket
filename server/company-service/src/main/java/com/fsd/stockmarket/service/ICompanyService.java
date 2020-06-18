package com.fsd.stockmarket.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fsd.stockmarket.dto.CompanyDto;
import com.fsd.stockmarket.entity.Company;
import com.fsd.stockmarket.entity.CompanyStockExchange;
import com.fsd.stockmarket.entity.StockExchange;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.vo.request.CreateCompanyReqVo;
//import com.fsd.stockmarket.vo.response.CompanyResponseVo;
import com.fsd.stockmarket.vo.request.UpdateCompanyReqVo;

public interface ICompanyService {

	public List<CompanyDto> listDetailByNameOrCode(String nameOrCode);
	
	public Page<CompanyDto> pageDetailByNameOrCode(String nameOrCode, Pageable pageable);
	
	public List<Company> findByCompanyNameLike(String companyName);
	
	public Company getByCompanyId(long companyId) throws ServiceException;

	public Company createCompany(CreateCompanyReqVo companyReqVo) throws ServiceException;
	
	public Company updateCompany(Long companyId, UpdateCompanyReqVo companyReqVo) throws ServiceException;
	
	public void deleteCompany(Long companyId) throws ServiceException;
	
	
	
	
	public CompanyStockExchange addStockExchangeToCompany(Long companyId, Long stockExchangeId, String companyCode) throws ServiceException;
	
	public void deleteStockExchangeFromCompany(Long companyId, Long stockExchangeId) throws ServiceException;
	
	public void deleteStockExchange(Long stockExchangeId) throws ServiceException;
	
	public CompanyStockExchange testLoadCompanyAndStockExchange(long id) throws ServiceException;
	
	public StockExchange testLoadStockExchange(long id) throws ServiceException;
}
