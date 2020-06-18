package com.fsd.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fsd.stockmarket.entity.Sector;
//import java.util.List;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import com.fsd.stockmarket.dto.CompanyDto;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

	
//	@Query(value = "SELECT new com.fsd.stockmarket.dto.CompanyDto(c, s, cse, se) " +
//	   		"FROM Sector s\n" +
//	   		"LEFT JOIN CompanySector cs ON s.id = cs.sectorId\n" +
//	   		"LEFT JOIN cs.company c\n" +
//	   		"LEFT JOIN c.stockExchanges cse\n" +
//	   		"LEFT JOIN cse.stockExchange se\n" +
//	   		"WHERE s.sectorName = :sectorName")
//	public List<CompanyDto> listCompaniesByNameOrCode(@Param("sectorName") String sectorName);
}
