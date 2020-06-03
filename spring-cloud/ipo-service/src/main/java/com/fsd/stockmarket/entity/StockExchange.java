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
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
 * @date 2020-04-15
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "t_stockexchange")
public class StockExchange implements Serializable {

	private static final long serialVersionUID = -4713818933698646460L;

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
    
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "stockExchange")
    @JsonIgnore
    private Set<IPODetail> ipoDetails = new HashSet<IPODetail>();
}
