package com.fsd.stockmarket.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_ipo_detail")
public class IPODetail implements Serializable {

	private static final long serialVersionUID = 5151751371837409761L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockexchange_id", nullable = false)
    private StockExchange stockExchange;
    
    @Column(name = "price_per_share")
	private BigDecimal pricePerShare;
    
    @Column(name = "total_shares")
    private long totalShares;
    
    @Column(name = "open_datetime")
    private Timestamp openDatetime;
    
    @Column(name = "remarks")
    private String remarks;
}
