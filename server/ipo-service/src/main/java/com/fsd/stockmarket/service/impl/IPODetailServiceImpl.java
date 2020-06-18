package com.fsd.stockmarket.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fsd.stockmarket.dto.IPODetailDto;
import com.fsd.stockmarket.entity.Company;
import com.fsd.stockmarket.entity.IPODetail;
import com.fsd.stockmarket.entity.StockExchange;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.repository.CompanyRepository;
import com.fsd.stockmarket.repository.IPODetailRepository;
import com.fsd.stockmarket.repository.StockExchangeRepository;
import com.fsd.stockmarket.service.IPODetailService;
import com.fsd.stockmarket.vo.request.CreateOrUpdateIPODetailReqVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IPODetailServiceImpl implements IPODetailService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private IPODetailRepository ipoDetailRepository;
	@Autowired
	private StockExchangeRepository stockExchangeRepository;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public Page<IPODetailDto> pageIPODetails(Pageable pageable) {
		Page<IPODetailDto> result = this.ipoDetailRepository.pageIPODetails(pageable);
		return result;
	}
	
	@Override
	public IPODetail getByDetailId(long ipoDetailId) throws ServiceException {
		return this.ipoDetailRepository.findById(Long.valueOf(ipoDetailId))
				.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "ipo detail id not found"));
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public IPODetail createIPODetail(CreateOrUpdateIPODetailReqVo reqVo) throws ServiceException {	
		IPODetail ipoDetail = new IPODetail();
		return this.saveOrUpdateIPODetail(ipoDetail, reqVo);
	}
		
	@Transactional(rollbackFor=Exception.class)
	@Override
	public IPODetail updateIPODetail(Long ipoDetailId, CreateOrUpdateIPODetailReqVo reqVo) throws ServiceException {
		IPODetail dbIPODetail = this.ipoDetailRepository.findById(ipoDetailId)
			.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "ipo id not found"));
		return this.saveOrUpdateIPODetail(dbIPODetail, reqVo);
	}
	
	private IPODetail saveOrUpdateIPODetail(IPODetail ipoDetailEntity, CreateOrUpdateIPODetailReqVo reqVo) throws ServiceException {	
		BeanUtils.copyProperties(reqVo, ipoDetailEntity);
		
		try {
			ipoDetailEntity.setOpenDatetime(new Timestamp(this.format.parse(reqVo.getOpenDatetimeStr()).getTime()));
		} catch (ParseException e) {
			log.error("open datetime format invalid", e);
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "open datetime format invalid, format: yyyy-MM-dd hh:mm:ss");
		}
		
		// company
		Company company = this.findCompanyById(reqVo.getCompanyId());
		ipoDetailEntity.setCompany(company);
		company.getIpoDetails().add(ipoDetailEntity);

		// stock exchange
		StockExchange stockExchange = this.findStockExchangeById(reqVo.getStockexchangeId());
		ipoDetailEntity.setStockExchange(stockExchange);
		stockExchange.getIpoDetails().add(ipoDetailEntity);
		
		return this.ipoDetailRepository.save(ipoDetailEntity);
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteIPODetail(Long ipoDetailId) throws ServiceException {
		this.ipoDetailRepository.delete(
			this.ipoDetailRepository.findById(ipoDetailId).orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "ipo detail id '"+ipoDetailId+"' not found"))
		);
	}
	
	private Company findCompanyById(Long companyId) throws ServiceException {
		Company company = this.companyRepository.findById(companyId)
				.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "company id not found"));
		log.info("Check result: company (id = '"+companyId+"' exist.");
		return company;
	}
	
	private StockExchange findStockExchangeById(Long stockExchangeId) throws ServiceException {
		StockExchange stockExchange = this.stockExchangeRepository.findById(stockExchangeId).orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "stock exchange not found"));
		log.info("Check result: StockExchange (id = '"+stockExchangeId+"' exist.");
		return stockExchange;
	}
}
