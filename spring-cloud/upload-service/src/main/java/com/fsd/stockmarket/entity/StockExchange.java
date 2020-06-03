package com.fsd.stockmarket.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "t_stockexchange")
public class StockExchange implements Serializable {

	private static final long serialVersionUID = -3421554730836444225L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "stockexchange")
    private String stockExchange;

    @Column(name = "brief")
    private String brief;

    @Column(name = "contact_address")
    private String contactAddress;
    
    @Column(name = "remarks")
    private String remarks;
}
