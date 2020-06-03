package com.fsd.stockmarket.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class ExcelCellDto implements Serializable {

	private static final long serialVersionUID = -6754462301871827028L;

	private String companyCode;
	
	private String stockExchange;
	
	private BigDecimal pricePerShare;
	
	private Date date;
	
	private Time time;
	
	@JsonIgnore
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Long dateTimeToMillis() {
		try {
			java.util.Date d = this.format.parse(this.getTimeStampStr());
			return Long.valueOf(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return 0L;
		}
	}
	
	public String getTimeStampStr() {
		String timestampStr = this.date.toString() + " " + this.time.toString();
		return timestampStr;
	}
	
	@Override
    public int hashCode() {
		//log.info("**** ExcelCellDto hashCode method is running");
		//return this.dateTimeToMillis().intValue();
		return Long.hashCode(this.dateTimeToMillis().longValue());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
            return false;
        }
        if (!ExcelCellDto.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final ExcelCellDto another = (ExcelCellDto) obj;
        return this.hashCode() == another.hashCode();
	}
}
