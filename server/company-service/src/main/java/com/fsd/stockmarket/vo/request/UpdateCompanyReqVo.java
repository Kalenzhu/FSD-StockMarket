package com.fsd.stockmarket.vo.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

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
public class UpdateCompanyReqVo implements Serializable {
	
	private static final long serialVersionUID = 2680043395925800996L;
		
	@NotEmpty(message = "company name can't be empty")
	private String companyName;
	
	private float turnover;
	
	private String ceo;
	
	private String boardDirectors;
	
	private String briefWriteUp;
	
	private String picUrl;
	    
	//@Min(1)
	private Long sectorId;
}
