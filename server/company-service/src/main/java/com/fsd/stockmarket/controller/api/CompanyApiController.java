package com.fsd.stockmarket.controller.api;

import com.fsd.stockmarket.dto.CompanyDto;
import com.fsd.stockmarket.entity.Company;
import com.fsd.stockmarket.entity.CompanyStockExchange;
import com.fsd.stockmarket.entity.StockExchange;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.pojo.Result;
import com.fsd.stockmarket.service.ICompanyService;
import com.fsd.stockmarket.vo.request.CreateCompanyReqVo;
import com.fsd.stockmarket.vo.request.UpdateCompanyReqVo;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * @author zuogan
 * @date 2020-04-10
 */
@Slf4j
@RestController
@RequestMapping("/api/company")
public class CompanyApiController {

	@Autowired
	private ICompanyService companyService;
	
    @GetMapping("/sayHello")
    public Result<String> hello() {
      log.info("company service /api/company/sayHello run");
      return new Result<String>("hello, this is the greet from company service /api/company/sayHello, it's a oauth protect resource.");
    }
    
    @GetMapping("/list")
    public Result<List<CompanyDto>> listByNameOrCode(@RequestParam(value="searchText",required = false) String nameOrCode) {
      log.info("company service /api/company/list run");
      return new Result<List<CompanyDto>>(this.companyService.listDetailByNameOrCode(nameOrCode));
    }
    
    /**
     * page query params: page=0&size=2&sort=id,desc
     * @param nameOrCode
     * @param pageable
     * @return
     */
    @GetMapping("/page")
    public Result<Page<CompanyDto>> pageByNameOrCode(@RequestParam(value="searchText",required = false) String nameOrCode, Pageable pageable) {
      log.info("company service /api/company/page run");
//      Pageable pageable = PageRequest.of(pageNum-1, pageSize, Sort.Direction.DESC, "id");
      return new Result<Page<CompanyDto>>(this.companyService.pageDetailByNameOrCode(nameOrCode, pageable));
    }
    
    @GetMapping("/namelike/{namelike}")
    public Result<List<Company>> findByCompanyNameLike(@PathVariable(value="namelike",required = true) String namelike) throws ServiceException {
      log.info("company service /api/company/namelike/"+namelike+" run");
      return new Result<List<Company>>(this.companyService.findByCompanyNameLike(namelike));
    }
    
    @GetMapping("/{companyId}")
    public Result<Company> getDetailByCompanyId(@PathVariable(value="companyId",required = true) long companyId) throws ServiceException {
      log.info("company service /api/company/"+companyId+" run");
      return new Result<Company>(this.companyService.getByCompanyId(companyId));
    }

    //@PostMapping("/create")
    @PostMapping("/admin/create")
    public Result<Company> createCompany(@Valid @RequestBody CreateCompanyReqVo companyRequestVo) throws ServiceException {
    	log.info("company service /api/company/create run");
        return new Result<Company>(this.companyService.createCompany(companyRequestVo));
    }
    
    //@PostMapping("/{companyId}/update")
    @PostMapping("/admin/{companyId}/update")
    public Result<Company> updateCompany(@PathVariable(value="companyId",required = true) Long companyId, 
    		@Valid @RequestBody UpdateCompanyReqVo updateCompanyReqVo) throws ServiceException {
    	log.info("company service /api/company/"+companyId+"/update run");
        return new Result<Company>(this.companyService.updateCompany(companyId, updateCompanyReqVo));
    }
    
    //@GetMapping("/{companyId}/delete")
    @GetMapping("/admin/{companyId}/delete")
    public Result<String> deleteCompany(@PathVariable(value="companyId",required = true) Long companyId) throws ServiceException {
    	log.info("company service /api/company/"+companyId+"/delete run");
    	this.companyService.deleteCompany(companyId);
        return new Result<String>("ok");
    }
    
    //@GetMapping("/{companyId}/stockexchange/{stockExchangeId}/add/{companyCode}")
    @GetMapping("/admin/{companyId}/stockexchange/{stockExchangeId}/add/{companyCode}")
    public Result<CompanyStockExchange> addStockExchangeToCompany(
    		@PathVariable(value="companyId",required = true) Long companyId,
    		@PathVariable(value="stockExchangeId",required = true) Long stockExchangeId,
    		@PathVariable(value="companyCode",required = true) String companyCode) throws ServiceException {
    	log.info("company service /api/company/{companyId}/stockexchange/{stockExchangeId}/add/{companyCode}");
    	return new Result<CompanyStockExchange>(this.companyService.addStockExchangeToCompany(companyId, stockExchangeId, companyCode));
    }
    
    //@GetMapping("/{companyId}/stockexchange/{stockExchangeId}/del")
    @GetMapping("/admin/{companyId}/stockexchange/{stockExchangeId}/del")
    public Result<String> deleteStockExchangeFromCompany(
    		@PathVariable(value="companyId",required = true) Long companyId,
    		@PathVariable(value="stockExchangeId",required = true) Long stockExchangeId) throws ServiceException {
    	log.info("company service /api/company/{companyId}/stockexchange/{stockExchangeId}/del");
    	this.companyService.deleteStockExchangeFromCompany(companyId, stockExchangeId);
    	return new Result<String>("ok");
    }
        
    @Deprecated
    @GetMapping("/stockexchange/{stockexchangeId}/delete")
    public Result<String> deleteStockExchange(@PathVariable(value="stockexchangeId",required = true) Long stockexchangeId) throws ServiceException {
    	log.info("company service /api/company/stockexchange/{stockexchangeId}/delete");
    	this.companyService.deleteStockExchange(stockexchangeId);
        return new Result<String>("ok");
    }
    
    /**
     * test api, deprecated in future
     * @param id
     * @return
     * @throws ServiceException
     */
    @Deprecated
    @GetMapping("/test1/{id}")
    public Result<CompanyStockExchange> testLoadCompanyAndStockExchange(@PathVariable(value="id",required = true) long id) throws ServiceException {
      return new Result<CompanyStockExchange>(this.companyService.testLoadCompanyAndStockExchange(id));
    }
    
    /**
     * test api, deprecated in future
     * @param id
     * @return
     * @throws ServiceException
     */
    @Deprecated
    @GetMapping("/test2/{id}")
    public Result<StockExchange> testLoadStockExchange(@PathVariable(value="id",required = true) long id) throws ServiceException {
      return new Result<StockExchange>(this.companyService.testLoadStockExchange(id));
    }
}
