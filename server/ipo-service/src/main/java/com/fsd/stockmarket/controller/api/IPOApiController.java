package com.fsd.stockmarket.controller.api;

import com.fsd.stockmarket.dto.IPODetailDto;
import com.fsd.stockmarket.entity.IPODetail;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.pojo.Result;
import com.fsd.stockmarket.service.IPODetailService;
import com.fsd.stockmarket.vo.request.CreateOrUpdateIPODetailReqVo;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/ipo")
public class IPOApiController {

	@Autowired
	private IPODetailService ipoDetailService;
	
    @GetMapping("/sayHello")
    public Result<String> hello() {
      log.info("ipo service /api/ipo/sayHello run");
      return new Result<String>("hello, this is the greet from ipo service /api/ipo/sayHello, it's a oauth protect resource.");
    }
    
    /**
     * page query params: page=0&size=2&sort=id,desc
     * @param pageable
     * @return
     */
    @GetMapping("/page")
    public Result<Page<IPODetailDto>> pageIPODetails(Pageable pageable) {
      log.info("ipo service /api/ipo/page run");
      // Pageable pageable = PageRequest.of(pageNum-1, pageSize, Sort.Direction.DESC, "id");
      return new Result<Page<IPODetailDto>>(this.ipoDetailService.pageIPODetails(pageable));
    }
    
    @GetMapping("/{ipoDetailId}")
    public Result<IPODetail> getByDetailId(@PathVariable(value="ipoDetailId",required = true) long ipoDetailId) throws ServiceException {
      log.info("ipo service /api/ipo/"+ipoDetailId+" run");
      return new Result<IPODetail>(this.ipoDetailService.getByDetailId(ipoDetailId));
    }

    //@PostMapping("/create")
    @PostMapping("/admin/create")
    public Result<IPODetail> createIPODetail(@Valid @RequestBody CreateOrUpdateIPODetailReqVo requestVo) throws ServiceException {
    	log.info("ipo service /api/ipo/create run");
        return new Result<IPODetail>(this.ipoDetailService.createIPODetail(requestVo));
    }
    
    //@PostMapping("/{ipoDetailId}/update")
    @PostMapping("/admin/{ipoDetailId}/update")
    public Result<IPODetail> updateIPODetail(@PathVariable(value="ipoDetailId",required = true) Long ipoDetailId, 
    		@Valid @RequestBody CreateOrUpdateIPODetailReqVo requestVo) throws ServiceException {
    	log.info("ipo service /api/ipo/"+ipoDetailId+"/update run");
        return new Result<IPODetail>(this.ipoDetailService.updateIPODetail(ipoDetailId, requestVo));
    }
    
    //@GetMapping("/{ipoDetailId}/delete")
    @GetMapping("/admin/{ipoDetailId}/delete")
    public Result<String> deleteIPODetail(@PathVariable(value="ipoDetailId",required = true) Long ipoDetailId) throws ServiceException {
    	log.info("ipo service /api/ipo/"+ipoDetailId+"/delete run");
    	this.ipoDetailService.deleteIPODetail(ipoDetailId);
        return new Result<String>("ok");
    }
}
