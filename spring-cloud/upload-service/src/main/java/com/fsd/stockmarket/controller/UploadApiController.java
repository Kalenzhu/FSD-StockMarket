package com.fsd.stockmarket.controller;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.pojo.Result;
import com.fsd.stockmarket.service.IUploadService;
import com.fsd.stockmarket.vo.UploadResultVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/upload")
public class UploadApiController {

	@Autowired
	private IUploadService uploadService;
	
    @GetMapping("/sayHello")
    public Result<String> hello() {
      log.info("upload service /api/upload/sayHello run");
      return new Result<String>("hello, this is the greet from upload service /api/upload/sayHello, it's a oauth protect resource.");
    }
    
    //@PostMapping("/file")
    @PostMapping("/admin/file")
    public Result<UploadResultVo> saveExcelStockPrices(@RequestParam("uploadFile") MultipartFile file) throws ServiceException {
    	log.info("upload service /api/upload/file run");

        if (file.isEmpty()) {
        	throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "excel file can't be empty");
        }
		try {
			InputStream inputStream = file.getInputStream();
			return new Result<UploadResultVo>(this.uploadService.saveExcelStockPrices(inputStream, file.getOriginalFilename()));
		} catch (IOException e) {
			log.error("open excel file input stream failed", e);
			throw new ServiceException(ServiceErrorCode.INTERNAL_ERROR, "read excel file failed");
		}
    }
}
