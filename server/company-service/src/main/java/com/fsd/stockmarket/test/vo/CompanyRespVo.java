package com.fsd.stockmarket.test.vo;

import java.io.Serializable;
import java.util.List;

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
public class CompanyRespVo implements Serializable {
	
	private static final long serialVersionUID = -2943910272696088356L;

	private long id;

    private String companyName;

    private float turnover;

    private String ceo;

    private String boardDirectors;

    private long sectorId;

    private String briefWriteUp;

    private String picUrl;
    
    private SectorRespVo sector;
    
    private List<StockExchangeRespVo> stockExchangeList;
}
