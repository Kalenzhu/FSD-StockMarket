package com.fsd.stockmarket.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fsd.stockmarket.dto.CompanyDto;
import com.fsd.stockmarket.entity.Company;
import com.fsd.stockmarket.entity.CompanySector;
import com.fsd.stockmarket.entity.CompanyStockExchange;
import com.fsd.stockmarket.entity.Sector;
import com.fsd.stockmarket.entity.StockExchange;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.repository.CompanyStockExchangeRepository;
import com.fsd.stockmarket.repository.CompanyRepository;
import com.fsd.stockmarket.repository.CompanySectorRepository;
import com.fsd.stockmarket.repository.SectorRepository;
import com.fsd.stockmarket.repository.StockExchangeRepository;
import com.fsd.stockmarket.service.ICompanyService;
import com.fsd.stockmarket.vo.request.CompanyStockExchangeRequestVo;
import com.fsd.stockmarket.vo.request.CreateCompanyReqVo;
import com.fsd.stockmarket.vo.request.UpdateCompanyReqVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private SectorRepository sectorRepository;
	@Autowired
	private StockExchangeRepository stockExchangeRepository;
	@Autowired
	private CompanyStockExchangeRepository companyStockExchangeRepository;
	@Autowired
	private CompanySectorRepository companySectorRepository;
	
	/**
	 * fuzzy search company detail list by name or code
	 */
	@Override
	public List<CompanyDto> listDetailByNameOrCode(String nameOrCode) {
		List<CompanyDto> result = this.companyRepository.listDetailByNameOrCode(nameOrCode);
		return result;
	}
	
	/**
	 * fuzzy search company detail page by name or code
	 */
	@Override
	public Page<CompanyDto> pageDetailByNameOrCode(String nameOrCode, Pageable pageable) {
		Page<CompanyDto> result = this.companyRepository.pageDetailByNameOrCode(nameOrCode, pageable);
		return result;
	}
		
	/**
	 * fuzzy search company entity list by name
	 */
	@Override
	public List<Company> findByCompanyNameLike(String companyName) {
		List<Company> result = this.companyRepository.findByCompanyNameLike(companyName);
		return result;
	}
	
	@Override
	public Company getByCompanyId(long companyId) throws ServiceException {
		return this.companyRepository.findById(Long.valueOf(companyId))
				.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "company id not found"));
	}
	
	/**
	 * save company entity
	 */
//	@Transactional
//	@Override
//	public Company saveCompany(CreateCompanyReqVo companyRequestVo) throws ServiceException {		
//		return this.createCompanyInternal(companyRequestVo);
//		Company companyEntity = new Company();
//		BeanUtils.copyProperties(companyRequestVo, companyEntity);
//		
//		
//
//		// stock exchange
//		List<CompanyStockExchangeRequestVo> updateCseVoList = companyRequestVo.getCompanyStockExchangeList();
//		if(CollectionUtils.isNotEmpty(updateCseVoList)) {
//			List<CompanyStockExchange> cseList = updateCseVoList.stream()
//				.map(item -> {
//					StockExchange se = null;
//					try {
//						se = this.stockExchangeRepository.findById(item.getStockExchangeId())
//							.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "stock exchange '"+item.getStockExchangeId()+"' not found"));
//						CompanyStockExchange cse = new CompanyStockExchange();
//						cse.setCompany(companyEntity);
//						cse.setCompanyCode(item.getCompanyCode());
//						cse.setStockExchange(se);
//						return cse;
//					} catch (ServiceException e) {
//						e.printStackTrace();
//						return null;
//					}
//				}).filter(item -> item != null).collect(Collectors.toList());
//			companyEntity.setStockExchanges(new HashSet(cseList));
//		}
//		Company savedCompany = this.companyRepository.save(companyEntity);
//		return this.getByCompanyId(Long.valueOf(savedCompany.getId()));
//	}
	
	@Transactional
	@Override
	public Company createCompany(CreateCompanyReqVo companyReqVo) throws ServiceException {	
		Company company = new Company();
		BeanUtils.copyProperties(companyReqVo, company);
		
		// sector
		Sector newSector = null;
		if(companyReqVo.getSectorId() != null) {
			newSector = this.findSectorById(companyReqVo.getSectorId());
			
			CompanySector companySector = new CompanySector();
			companySector.setSector(newSector);
			companySector.setCompany(company);
			company.setCompanySector(companySector);
		}
		
		List<CompanyStockExchangeRequestVo> reqStockExchangeList = companyReqVo.getCompanyStockExchangeList();
		if(CollectionUtils.isNotEmpty(reqStockExchangeList)) {
			reqStockExchangeList = reqStockExchangeList.stream().distinct().collect(Collectors.toList());
			for(CompanyStockExchangeRequestVo vo : reqStockExchangeList) {
				StockExchange stockExchange = this.findStockExchangeById(vo.getStockExchangeId());
				
				if(CollectionUtils.isNotEmpty(
						this.companyStockExchangeRepository.findByStockExchangeIdAndCompanyCode(vo.getStockExchangeId(), vo.getCompanyCode())
				)) {
					throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "company code already exist in stock exchange '"+vo.getStockExchangeId()+"'");
				}
				
				CompanyStockExchange cse = new CompanyStockExchange();
				cse.setCompany(company);
				cse.setCompanyCode(vo.getCompanyCode());
				cse.setStockExchange(stockExchange);
				company.getStockExchanges().add(cse);
			}
		}
		
		return this.companyRepository.save(company);
	}
	
	@Transactional
	@Override
	public Company updateCompany(Long companyId, UpdateCompanyReqVo companyReqVo) throws ServiceException {
		Company dbCompany = this.companyRepository.findById(companyId)
			.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "company not found"));
		
		BeanUtils.copyProperties(companyReqVo, dbCompany);
		
		Sector newSector = companyReqVo.getSectorId() != null? this.findSectorById(companyReqVo.getSectorId()) : null;
		Sector oldSector = dbCompany.getCompanySector() != null? dbCompany.getCompanySector().getSector() : null;
		boolean saveSector = false;
		if(newSector != null) {
			if(oldSector != null) {
				if(newSector.getId() != oldSector.getId()) {
					this.companySectorRepository.delete(dbCompany.getCompanySector());
					saveSector = true;
				}
			} else {
				saveSector = true;
			}
		} else {
			if(oldSector != null) {
				this.companySectorRepository.delete(dbCompany.getCompanySector());
				dbCompany.setCompanySector(null);
			}
		}
		if(saveSector && newSector != null) {
			CompanySector companySector = new CompanySector();
			companySector.setCompany(dbCompany);
			companySector.setSector(newSector);
			dbCompany.setCompanySector(companySector);
		}
		return this.companyRepository.save(dbCompany);
	}
	
	/**
	 * @param companyId
	 * @throws ServiceException
	 */
	@Transactional
	@Override
	public void deleteCompany(Long companyId) throws ServiceException {
		this.companyRepository.delete(
			this.companyRepository.findById(companyId).orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "company id '"+companyId+"' not found"))
		);
	}
	
	private Sector findSectorById(Long sectorId) throws ServiceException {
		Sector sector = this.sectorRepository.findById(sectorId)
				.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "sector not found"));
		log.info("Check result: sector (id = '"+sectorId+"' exist.");
		return sector;
	}
	
	private StockExchange findStockExchangeById(Long stockExchangeId) throws ServiceException {
		StockExchange stockExchange = this.stockExchangeRepository.findById(stockExchangeId)
				.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "stock exchange not found"));
		log.info("Check result: StockExchange (id = '"+stockExchangeId+"' exist.");
		return stockExchange;
	}
	

	@Transactional
	@Override
	public CompanyStockExchange addStockExchangeToCompany(Long companyId, Long stockExchangeId, String companyCode) throws ServiceException {
		Company company = this.companyRepository.findById(companyId)
				.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "company not found"));
		StockExchange stockExchange = this.stockExchangeRepository.findById(stockExchangeId)
				.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "stock exchange not found"));
		
		if(CollectionUtils.isNotEmpty(this.companyStockExchangeRepository.findByCompanyIdAndStockExchangeId(companyId, stockExchangeId))) {
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "company exist in the stock exchange");
		}
		if(CollectionUtils.isNotEmpty(
			this.companyStockExchangeRepository.findByCompanyIdNotAndStockExchangeIdAndCompanyCode(companyId, stockExchangeId, companyCode))
		) {
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "company code already exist");
		}
		CompanyStockExchange cse = new CompanyStockExchange();
		cse.setCompany(company);
		cse.setStockExchange(stockExchange);
		cse.setCompanyCode(companyCode);
		return this.companyStockExchangeRepository.save(cse);
	}
	
	@Transactional
	@Override
	public void deleteStockExchangeFromCompany(Long companyId, Long stockExchangeId) throws ServiceException {
		this.companyStockExchangeRepository.deleteByCompanyIdAndStockExchangeId(companyId, stockExchangeId);
	}
	
	@Override
	public void deleteStockExchange(Long stockExchangeId) throws ServiceException {
		this.stockExchangeRepository.delete(
			this.stockExchangeRepository.findById(stockExchangeId).orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "stock exchange id '"+stockExchangeId+"' not found"))
		);
	}
	
	@Override
	public CompanyStockExchange testLoadCompanyAndStockExchange(long id) throws ServiceException {
		return this.companyStockExchangeRepository.findById(Long.valueOf(id))
				.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "CompanyAndStockExchange id not found"));
	}
	
	@Override
	public StockExchange testLoadStockExchange(long id) throws ServiceException {
		return this.stockExchangeRepository.findById(Long.valueOf(id))
				.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "StockExchange id not found"));
	}
}
