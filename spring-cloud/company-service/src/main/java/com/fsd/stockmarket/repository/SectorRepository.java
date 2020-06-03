package com.fsd.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fsd.stockmarket.entity.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

}
