package com.fsd.stockmarket.service;

import java.io.InputStream;

import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.vo.UploadResultVo;

public interface IUploadService {

	public UploadResultVo saveExcelStockPrices(InputStream inputStream, String filename) throws ServiceException;
}
