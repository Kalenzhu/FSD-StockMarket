package com.fsd.stockmarket.vo.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zuogan
 * @date 2020-04-18
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyReqVo extends UpdateCompanyReqVo {

	@Valid
    List<CompanyStockExchangeRequestVo> companyStockExchangeList;

	//@Email(message = "email error")
	//private String username;
}
