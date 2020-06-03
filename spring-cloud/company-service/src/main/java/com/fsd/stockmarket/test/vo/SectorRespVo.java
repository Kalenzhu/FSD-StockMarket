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
public class SectorRespVo implements Serializable {

	private static final long serialVersionUID = -987530876085825360L;

	private long id;
	
	private String sectorName;
	
	//private String brief;
}
