package com.fsd.stockmarket.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsd.stockmarket.entity.CompanySector;
import com.fsd.stockmarket.entity.Sector;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.repository.CompanySectorRepository;
import com.fsd.stockmarket.repository.SectorRepository;
import com.fsd.stockmarket.service.ISectorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SectorServiceImpl implements ISectorService {

	@Autowired
	private SectorRepository sectorRepository;
	
	@Autowired
	private CompanySectorRepository companySectorRepository;
	
	@Override
	public List<Sector> findAll() {
		List<Sector> result = this.sectorRepository.findAll();
		return result;
	}
	
	@Override
	public List<CompanySector> findCompaniesBySectorId(Long sectorId) throws ServiceException {
		this.sectorRepository.findById(Long.valueOf(sectorId))
			.orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST, "sector id not found"));
		return this.companySectorRepository.findBySectorId(Long.valueOf(sectorId));
	}
}
