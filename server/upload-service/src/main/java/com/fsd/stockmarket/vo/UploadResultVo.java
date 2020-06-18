package com.fsd.stockmarket.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultVo implements Serializable {

	private static final long serialVersionUID = -7340453310825671654L;

	private String companyName;
	
	private String stockExchange;
	
	private Integer totalRecords;
	
	private String startDateStr;
	
	private String endDateStr;
	
	@Override
	public String toString() {
		return "companyName: "+companyName + ", "
			+ "stockExchange: "+stockExchange + ", "
			+ "totalRecords: "+totalRecords + ", "
			+ "startDateStr: "+startDateStr + ", "
			+ "endDateStr: "+endDateStr;
	}
}
