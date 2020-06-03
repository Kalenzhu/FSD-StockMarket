package com.fsd.stockmarket.service;

import java.util.List;

import com.fsd.stockmarket.entity.CompanySector;
import com.fsd.stockmarket.entity.Sector;
import com.fsd.stockmarket.exception.ServiceException;

public interface ISectorService {

	public List<Sector> findAll();
	
	public List<CompanySector> findCompaniesBySectorId(Long sectorId) throws ServiceException;
}
