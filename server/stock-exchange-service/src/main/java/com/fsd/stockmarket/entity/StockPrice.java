package com.fsd.stockmarket.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "t_stock_price")
public class StockPrice implements Serializable {

	private static final long serialVersionUID = 1985772137571391860L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	@JsonIgnore
    private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_stockexchange_id", nullable = false)
	@JsonIgnore
    private CompanyStockExchange companyStockExchange;
	
	@Column(name = "current_price")
	private BigDecimal currentPrice;
	
	@Column(name = "price_date")
	private Date priceDate;
	
	@Column(name = "price_time")
	private Time priceTime;

	public boolean before(Date date) {
		return this.compareDate(date, true);
	}
	
	public boolean after(Date date) {
		return this.compareDate(date, false);
	}
	
	private boolean compareDate(Date date, boolean compareBefore) {
		String timestampStr = this.priceDate.toString() + " " + this.priceTime.toString();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date d1 = format.parse(timestampStr);
			java.util.Date d2 = new java.util.Date(date.getTime());
			return compareBefore ? d1.before(d2) : d1.after(d2);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
}
