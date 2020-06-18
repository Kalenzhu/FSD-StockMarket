package com.fsd.stockmarket.service;

import java.io.InputStream;
import java.util.List;

import com.fsd.stockmarket.dto.ExcelCellDto;
import com.fsd.stockmarket.exception.ServiceException;

public interface IExcelImportService {

	public List<ExcelCellDto> getListByExcel(InputStream inputStream, String fileName) throws ServiceException;
}
