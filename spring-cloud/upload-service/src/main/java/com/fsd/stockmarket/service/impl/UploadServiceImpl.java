package com.fsd.stockmarket.service.impl;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fsd.stockmarket.dto.ExcelCellDto;
import com.fsd.stockmarket.entity.CompanyStockExchange;
import com.fsd.stockmarket.entity.StockExchange;
import com.fsd.stockmarket.entity.StockPrice;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.repository.CompanyStockExchangeRepository;
import com.fsd.stockmarket.repository.StockExchangeRepository;
import com.fsd.stockmarket.repository.StockPriceRepository;
import com.fsd.stockmarket.service.IExcelImportService;
import com.fsd.stockmarket.service.IUploadService;
import com.fsd.stockmarket.vo.UploadResultVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UploadServiceImpl implements IUploadService {
	
	@Autowired
	private StockExchangeRepository stockExchangeRepository;
	@Autowired
	private CompanyStockExchangeRepository companyStockExchangeRepository;
	@Autowired
	private StockPriceRepository stockPriceRepository;
	@Autowired
	private IExcelImportService excelImportService;
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public UploadResultVo saveExcelStockPrices(InputStream inputStream, String filename) throws ServiceException {
		List<ExcelCellDto> dtoList = this.excelImportService.getListByExcel(inputStream, filename);
		if(dtoList.size() == 0) {
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "excel has no price data");
		}
		CompanyStockExchange cse = this.getCompanyStockExchangeFromDto(dtoList);
		List<StockPrice> stockPriceList = dtoList.stream().map(dto -> {
			StockPrice stockPrice = new StockPrice();
			stockPrice.setCompanyStockExchange(cse);
			stockPrice.setCurrentPrice(dto.getPricePerShare());
			stockPrice.setPriceDate(dto.getDate());
			stockPrice.setPriceTime(dto.getTime());
			return stockPrice;
		}).sorted(
			Comparator.comparing(StockPrice::getPriceDate, Comparator.reverseOrder())
			.thenComparing(StockPrice::getPriceTime, Comparator.reverseOrder()))
		.collect(Collectors.toList());
		
		List<StockPrice> result = this.stockPriceRepository.saveAll(stockPriceList);
		
		UploadResultVo vo = new UploadResultVo();
		vo.setCompanyName(cse.getCompany().getCompanyName());
		vo.setStockExchange(cse.getStockexchange().getStockExchange());
		vo.setTotalRecords(result.size());
		vo.setStartDateStr(stockPriceList.get(stockPriceList.size() - 1).getTimeStampStr());
		vo.setEndDateStr(stockPriceList.get(0).getTimeStampStr());
		log.info("saveExcelStockPrices result: " + vo.toString());
		return vo;
	}
	
	private CompanyStockExchange getCompanyStockExchangeFromDto(List<ExcelCellDto> dtoList) throws ServiceException {
		List<String> companyCodeList = dtoList.stream().map(ExcelCellDto::getCompanyCode).distinct().collect(Collectors.toList());
		if(companyCodeList.size() != 1) {
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "company code error, each excel only contains one company");
		}
		String companyCode = companyCodeList.get(0);
		StockExchange stockExchange = this.getStockExchangeFromDto(dtoList);
		
		List<CompanyStockExchange> cseList = this.companyStockExchangeRepository.findByCompanyCodeAndStockexchange_Id(companyCode, Long.valueOf(stockExchange.getId()));
		if(cseList.size() == 0) {
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "company isn't in this stock exchange");
		}
		CompanyStockExchange cse = cseList.get(0);
		return cse;
	}
	
	private StockExchange getStockExchangeFromDto(List<ExcelCellDto> dtoList) throws ServiceException {
		List<String> stockExchangeList = dtoList.stream().map(ExcelCellDto::getStockExchange).distinct().collect(Collectors.toList());
		if(stockExchangeList.size() != 1) {
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "stock change error, each excel only contains one stock exchange");
		}
		
		String stockExchangeStr = stockExchangeList.get(0);
		List<StockExchange> stockExchangeEntities = this.stockExchangeRepository.findByStockExchange(stockExchangeStr);
		if(stockExchangeEntities.size() == 0) {
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "stock exchange can't found");
		}
		StockExchange stockExchange = stockExchangeEntities.get(0);
		return stockExchange;
	}
}
