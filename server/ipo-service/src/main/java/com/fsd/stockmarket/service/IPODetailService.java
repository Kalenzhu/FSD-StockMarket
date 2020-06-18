package com.fsd.stockmarket.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.fsd.stockmarket.dto.IPODetailDto;
import com.fsd.stockmarket.entity.IPODetail;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.vo.request.CreateOrUpdateIPODetailReqVo;

public interface IPODetailService {

	public Page<IPODetailDto> pageIPODetails(Pageable pageable);

	public IPODetail getByDetailId(long ipoDetailId) throws ServiceException;

	public IPODetail createIPODetail(CreateOrUpdateIPODetailReqVo reqVo) throws ServiceException;

	public IPODetail updateIPODetail(Long ipoDetailId, CreateOrUpdateIPODetailReqVo reqVo) throws ServiceException;
	
	public void deleteIPODetail(Long ipoDetailId) throws ServiceException;

}
