package com.fsd.stockmarket.vo.request;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zuogan
 * @date 2020-04-18
 */
@Slf4j
//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyStockExchangeRequestVo implements Serializable {

	private static final long serialVersionUID = 978769854973140766L;

	@Min(1)
	private Long stockExchangeId;
	
	@NotEmpty(message = "company code can't be empty")
	private String companyCode;
	
	@Override
    public int hashCode() {
		log.info("**** CompanyStockExchangeRequestVo hashCode method is running");
		return this.stockExchangeId.intValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		log.info("**** CompanyStockExchangeRequestVo equals method is running");
		if (obj == null) {
			log.info("**** anther obj is null");
            return false;
        }
        if (!CompanyStockExchangeRequestVo.class.isAssignableFrom(obj.getClass())) {
        	log.info("**** anther obj is not CompanyStockExchangeRequestVo class");
            return false;
        }
        final CompanyStockExchangeRequestVo another = (CompanyStockExchangeRequestVo) obj;
		if((this.stockExchangeId != null && another.getStockExchangeId() == null)
				|| (this.stockExchangeId == null && another.getStockExchangeId() != null)) {
			log.info("**** anther obj and me has one whose stock exchange id is null");
			return false;
		} else {
			if(this.stockExchangeId != null && another.getStockExchangeId() != null) {
				log.info("**** my id ="+this.stockExchangeId.longValue()+" another id = "+ another.getStockExchangeId().longValue()
					+", compare is = "+(this.stockExchangeId.longValue() == another.getStockExchangeId().longValue()));
				return this.stockExchangeId.longValue() == another.getStockExchangeId().longValue();
			} else {
				log.info("**** anther obj and me both stock exchange id is null");
				return true;
			}
		}
	}
        
//		if(obj.getClass() != CompanyStockExchangeRequestVo.class) {
//			return false;
//		}
//		CompanyStockExchangeRequestVo another = (CompanyStockExchangeRequestVo) obj;
//		if((this.stockExchangeId != null && another.getStockExchangeId() == null)
//			|| (this.stockExchangeId == null && another.getStockExchangeId() != null)) {
//			return false;
//		} else {
//			if(this.stockExchangeId != null && another.getStockExchangeId() != null) {
//				log.info("my id ="+this.stockExchangeId.longValue()+" another id = "+ another.getStockExchangeId().longValue()
//						+", compare is = "+(this.stockExchangeId.longValue() == another.getStockExchangeId().longValue()));
//				return this.stockExchangeId.longValue() == another.getStockExchangeId().longValue();
//			} else {
//				return true;
//			}
//		}
}
