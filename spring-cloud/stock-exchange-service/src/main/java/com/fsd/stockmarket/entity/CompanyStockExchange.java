package com.fsd.stockmarket.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Notice: don't use lombok @Data, it will trigger infinite json serialize error.\
 * Because lombok @Data generate hashcode and equals method which triggers this infinite error.
 * 
 * @author zuogan
 * @date 2020-04-16
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "t_company_stockexchange")
public class CompanyStockExchange implements Serializable {

	private static final long serialVersionUID = 4650224640932490391L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	@JsonIgnore
    private long id;

    @Column(name = "company_code")
    private String companyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockexchange_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StockExchange stockExchange;
    
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "companyStockExchange")
    private Set<StockPrice> stockPrice = new HashSet<StockPrice>();
    
    public CompanyStockExchange(Company company, StockExchange stockExchange, String companyCode) {
    	this.company = company;
    	this.stockExchange = stockExchange;
    	this.companyCode = companyCode;
    }
}
