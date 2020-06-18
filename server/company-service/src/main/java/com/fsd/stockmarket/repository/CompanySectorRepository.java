package com.fsd.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsd.stockmarket.entity.CompanySector;

@Repository
public interface CompanySectorRepository extends JpaRepository<CompanySector, Long> {

	public CompanySector findOneByCompanyId(Long companyId);
	
//	public CompanySector findByCompanyIdAndSectorId(Long companyId, Long sectorId);
}
