package com.fsd.stockmarket.test.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.fsd.stockmarket.entity.Company;
import com.fsd.stockmarket.entity.CompanyStockExchange;
import com.fsd.stockmarket.entity.Sector;
import com.fsd.stockmarket.entity.StockExchange;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.repository.CompanyStockExchangeRepository;
import com.fsd.stockmarket.repository.CompanyRepository;
import com.fsd.stockmarket.repository.SectorRepository;
import com.fsd.stockmarket.repository.StockExchangeRepository;
import com.fsd.stockmarket.test.vo.CompanyRespVo;
import com.fsd.stockmarket.test.vo.SectorRespVo;
import com.fsd.stockmarket.test.vo.StockExchangeRespVo;
import com.fsd.stockmarket.vo.request.CreateCompanyReqVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamTestService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private SectorRepository sectorRepository;
	@Autowired
	private StockExchangeRepository stockExchangeRepository;
	@Autowired
	private CompanyStockExchangeRepository companyAndStockExchangeRepository;
	
	public List<CompanyRespVo> findByNameOrCode(String nameOrCode) {
		return null;
	}
	
	public Page<CompanyRespVo> pageFindByNameOrCode(String nameOrCode, Pageable pageable) {
		return null;
	}
	
	public CompanyRespVo findById(long companyId) {
		Optional<Company> result = this.companyRepository.findById(Long.valueOf(companyId));
//		return result.isPresent() ? result.get() : null;
		return null;
	}
	
	public CompanyRespVo saveCompany(CreateCompanyReqVo companyRequestVo) throws ServiceException {
		return null;
	}
	
	public CompanyRespVo updateCompany(CreateCompanyReqVo companyRequestVo) throws ServiceException {
		return null;
	}
	
	public void deleteCompany(long companyId) throws ServiceException {
		
	}
	
	private CompanyRespVo fattenCompany(Company company) {
		CompanyRespVo companyRsp = new CompanyRespVo();
		BeanUtils.copyProperties(company, companyRsp);
		
//		if(company.getSector()!=null) {
		if(company!=null) {
//			Optional<Sector> result = sectorRepository.findById(company.getSectorId());
			Optional<Sector> result = Optional.ofNullable(company.getCompanySector().getSector());
			if(result.isPresent()) {
				SectorRespVo sectorRsp = new SectorRespVo();
				BeanUtils.copyProperties(result.get(), sectorRsp);
				companyRsp.setSector(sectorRsp);
			}
		}
		List<Long> stockExchangeIds = this.companyAndStockExchangeRepository.findByCompanyIdIn(
				Lists.newArrayList(Long.valueOf(company.getId()))).stream()
//			.map(CompanyAndStockExchange::getStockExchangeId)
			.map(CompanyStockExchange::getId)
			.distinct().collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(stockExchangeIds)) {
			companyRsp.setStockExchangeList(
				this.stockExchangeRepository.findAllById(stockExchangeIds).stream().map(item -> {
					StockExchangeRespVo vo = new StockExchangeRespVo();
					BeanUtils.copyProperties(item, vo);
					return vo;
				}).collect(Collectors.toList())
			);
		}
		return companyRsp;
	}
	
	private List<CompanyRespVo> fattenCompanies(List<Company> companies) {
		if(CollectionUtils.isEmpty(companies)) return Lists.newArrayList();
		
		List<CompanyRespVo> companyVos = companies.stream().map(item -> {
			CompanyRespVo companyVo = new CompanyRespVo();
			BeanUtils.copyProperties(item, companyVo);
//			if(item.getSectorId() > 0) {
			if(item.getCompanySector().getSector() != null) {
				SectorRespVo sectorVo = new SectorRespVo();
				sectorVo.setId(item.getCompanySector().getSector().getId());
				companyVo.setSector(sectorVo);
			}
			return companyVo;
		}).collect(Collectors.toList());
		
		List<Long> sectorIds = companies.stream().map(Company::getId)
				.distinct().filter(id -> id.longValue() > 0).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(sectorIds)) {
			// key: sector id,  value: sector vo object
			Map<Long, SectorRespVo> sectorMap = this.sectorRepository.findAllById(sectorIds).stream().collect(
					Collectors.toMap(
						// key
						item -> Long.valueOf(item.getId()), 
						// value
						item -> {
							SectorRespVo vo = new SectorRespVo();
							BeanUtils.copyProperties(item, vo);
							return vo;
						}, 
						// if key duplicated, use former value
						(v1, v2) -> v1
					)
				);
			companyVos.stream().forEach(item -> {
				if(item.getSector() != null) {
					item.setSector(sectorMap.getOrDefault(Long.valueOf(item.getSector().getId()), null));
				}
			});
		}
		
		List<Long> companyIds = companies.stream().map(Company::getId)
			.distinct().collect(Collectors.toList());
		List<CompanyStockExchange> cseList = this.companyAndStockExchangeRepository.findByCompanyIdIn(companyIds);
		
		List<Long> stockExchangeIds = cseList.stream()
//				.map(CompanyAndStockExchange::getStockExchangeId)
				.map(CompanyStockExchange::getId)
				.distinct().filter(item -> item.longValue() > 0).collect(Collectors.toList());
		// key: stock exchange id,  value: stock exchange entity object
		Map<Long, StockExchange> stockExchangeMap = this.stockExchangeRepository.findAllById(stockExchangeIds).stream().collect(
					Collectors.toMap(
						// key
						item -> Long.valueOf(item.getId()), 
						// value
						v -> v, 
						// if key duplicated, use former value
						(v1, v2) -> v1
					)
				);
		
		// key: company id,  value: StockExchange vo list
		Map<Long, List<StockExchangeRespVo>> stockExchangeVoMap = cseList.stream()
			.collect(Collectors.groupingBy(
					// key
//					CompanyAndStockExchange::getCompanyId,
					CompanyStockExchange::getId,
					// value (StockExchangeRespVo list)
					Collectors.mapping(item -> {
						StockExchangeRespVo vo = new StockExchangeRespVo();
//						vo.setId(item.getStockExchangeId());
						vo.setCompanyCode(item.getCompanyCode());
//						if(stockExchangeMap.containsKey(Long.valueOf(item.getStockExchangeId()))) {
						if(stockExchangeMap.containsKey(Long.valueOf(item.getId()))) {
//							StockExchange stockExchange = stockExchangeMap.get(Long.valueOf(item.getStockExchangeId()));
							StockExchange stockExchange = stockExchangeMap.get(Long.valueOf(item.getId()));
							vo.setStockExchange(stockExchange.getStockExchange());
						} else {
							vo.setStockExchange(null);
						}
						//vo.setLatestProce(null);
						return vo;
					}, Collectors.toList())
			));
		companyVos.stream().forEach(item -> {
			if(stockExchangeVoMap.containsKey(Long.valueOf(item.getId()))) {
				item.setStockExchangeList(stockExchangeVoMap.get(Long.valueOf(item.getId())));
			} else {
				item.setStockExchangeList(null);
			}
		});
		return companyVos;
	}
}
