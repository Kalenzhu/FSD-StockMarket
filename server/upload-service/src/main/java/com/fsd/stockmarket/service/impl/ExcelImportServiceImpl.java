package com.fsd.stockmarket.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.fsd.stockmarket.dto.ExcelCellDto;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.service.IExcelImportService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExcelImportServiceImpl implements IExcelImportService {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		
    /**
     * @param inputStream
     * @param fileName
     * @return
     * @throws Exception
     */
	@Override
	public List<ExcelCellDto> getListByExcel(InputStream inputStream, String fileName) throws ServiceException {
    	List<ExcelCellDto> allRowValues = new ArrayList<ExcelCellDto>();
    	
        // create work book
        Workbook work = this.getWorkbook(inputStream, fileName);
        
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        // for-each sheets
        for(int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            int rowsNum = sheet.getPhysicalNumberOfRows();
            // skip the excel header
            for(int j = 1; j < rowsNum; j++) {
                row = sheet.getRow(j);
                if (!this.isValidRow(row)) {
                    continue;
                }

                ExcelCellDto dto = new ExcelCellDto();
                int cellsNum = row.getPhysicalNumberOfCells();
                for (int y = 0; y < cellsNum; y++) {
                    cell = row.getCell(y);

                    //switch(cellOffset) {
                    switch(y) {
	                    case 0: 
	                    	dto.setCompanyCode(cell.getStringCellValue());
	                    	break;
	                    case 1: 
	                    	dto.setStockExchange(cell.getStringCellValue());
	                    	break;
	                    case 2: 
	                    	dto.setPricePerShare(new BigDecimal(cell.getNumericCellValue()));
	                    	break;
	                    case 3: 
	                    	java.util.Date d = null;
	                    	try {
	                    		d = this.dateFormat.parse(cell.getStringCellValue());
	                    		dto.setDate(new Date(d.getTime()));
	                    	} catch(Exception e) {
	                    		log.error("parse cell date error", e);
	                    	}
	                    	break;
	                    case 4: 
	                    	java.util.Date t = null;
	                    	try {
	                    		t = this.timeFormat.parse(cell.getStringCellValue());
	                    		dto.setTime(new Time(t.getTime()));
	                    	} catch(Exception e) {
	                    		log.error("parse cell time error", e);
	                    	}
	                    	break;
	                    default:
	                    	break;
                    }
                }
                allRowValues.add(dto);
            }
        }
        try {
			work.close();
			inputStream.close();
		} catch (IOException e) {
			log.error("close work book and input stream failed", e);
			throw new ServiceException(ServiceErrorCode.INTERNAL_ERROR, "parse excel failed");
		}
        return allRowValues.stream().distinct().collect(Collectors.toList());
    }

    /**
     * @param inputStream
     * @param fileName
     * @return
     * @throws ServiceException
     */
    private Workbook getWorkbook(InputStream inputStream, String fileName) throws ServiceException {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            try {
				workbook = new HSSFWorkbook(inputStream);
			} catch (IOException e) {
				log.error("create HSSFWorkbook object failed", e);
				throw new ServiceException(ServiceErrorCode.INTERNAL_ERROR, "parse excel failed");
			}
        } else if (".xlsx".equals(fileType)) {
            try {
				workbook = new XSSFWorkbook(inputStream);
			} catch (IOException e) {
				log.error("create XSSFWorkbook object failed", e);
				throw new ServiceException(ServiceErrorCode.INTERNAL_ERROR, "parse excel failed");
			}
        } else {
            log.error("excel file tyoe can't support, file type: "+fileType);
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "only support xls/xlsx");
        }
        return workbook;
    }
    
	private boolean isValidRow(Row row) {
		if (row == null) {
            return false;
        }
		int cellsNum = row.getPhysicalNumberOfCells();
		for (int i = 0; i < cellsNum; i++) {
			Cell cell = row.getCell(i);
			if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return false;
			}
		}
		return true;
	}
}
