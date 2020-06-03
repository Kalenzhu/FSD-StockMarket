package com.fsd.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsd.stockmarket.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{

}
