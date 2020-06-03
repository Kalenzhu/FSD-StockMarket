package com.fsd.stockmarket.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fsd.stockmarket.dto.CompanyDto;
import com.fsd.stockmarket.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	@Query(value = "select new com.fsd.stockmarket.dto.CompanyDto(co, s, cse, se) " +
	   		"from Company co\n" +
	   		"LEFT JOIN co.companySector cs\n" +
	   		"LEFT JOIN cs.sector s\n" +
	   		"LEFT JOIN co.stockExchanges cse\n" +
	   		"LEFT JOIN cse.stockExchange se\n" +
	   		"where co.companyName like CONCAT('%',:companyCode,'%')\n" +
	   		"or cse.companyCode like CONCAT('%',:companyCode,'%')")
	public List<CompanyDto> listDetailByNameOrCode(@Param("companyCode") String companyCode);
	
	@Query(value = "select new com.fsd.stockmarket.dto.CompanyDto(co, s, cse, se) " +
	   		"from Company co\n" +
	   		"LEFT JOIN co.companySector cs\n" +
	   		"LEFT JOIN cs.sector s\n" +
	   		"LEFT JOIN co.stockExchanges cse\n" +
	   		"LEFT JOIN cse.stockExchange se\n" +
	   		"where co.companyName like CONCAT('%',:companyCode,'%')\n" +
	   		"or cse.companyCode like CONCAT('%',:companyCode,'%')")
	public Page<CompanyDto> pageDetailByNameOrCode(@Param("companyCode") String companyCode, Pageable pageable);

	public List<Company> findByCompanyNameLike(String companyName);
	
//	@Modifying
//	@Query("update User bean set bean.recommend=?2,bean.birthDate=?3 where bean.id=?1")
//	public void recommended(Integer id,Integer recommend,Date birth);
	
	/**
	 * search child field's property example
	 * @param companyCode
	 * @return
	public List<Company> findByStockExchanges_CompanyCodeLike(String companyCode);
	 */
	
}
