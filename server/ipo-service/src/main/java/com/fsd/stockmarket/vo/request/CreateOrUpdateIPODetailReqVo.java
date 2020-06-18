package com.fsd.stockmarket.vo.request;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateIPODetailReqVo implements Serializable {

	private static final long serialVersionUID = -1740524019461581256L;

	@NotNull(message = "company id can't be empty")
	@Min(value = 1, message = "company id at least is 1 or above")
	private Long companyId;

	@NotNull(message = "stock exchange id can't be empty")
	@Min(value = 1, message = "stock exchange id at least is 1 or above")
	private Long stockexchangeId;
	
	@NotNull(message = "price per share can't be empty")
	private BigDecimal pricePerShare;
	
	@Min(value = 0, message = "total shares must positive number")
	private long totalShares;

	@NotEmpty(message = "open datetime can't be empty, format: yyyy-mm-dd hh:mm:ss")
    private String openDatetimeStr;

    private String remarks;

}
