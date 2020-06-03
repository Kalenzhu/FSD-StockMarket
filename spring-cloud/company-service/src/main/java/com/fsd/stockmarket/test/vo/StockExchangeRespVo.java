package com.fsd.stockmarket.test.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zuogan
 * @date 2020-04-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockExchangeRespVo implements Serializable {

	private static final long serialVersionUID = 6065826449984764212L;

	private long id;
	
	private String stockExchange;
    
    private String companyCode;
    
    private Double latestProce;
    
    //private String brief;

    //private String contactAddress;

    //private String remarks;
}
