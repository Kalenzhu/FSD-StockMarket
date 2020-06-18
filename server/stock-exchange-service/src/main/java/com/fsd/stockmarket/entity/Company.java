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
@Table(name = "t_company")
public class Company implements Serializable {

	private static final long serialVersionUID = -7592974436704571872L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "turnover")
    private float turnover;

    @Column(name = "ceo")
    private String ceo;
    
    @Column(name = "board_directors")
    private String boardDirectors;
        
    @Column(name = "brief_write_up")
    private String briefWriteUp;
    
    @Column(name = "pic_url")
    private String picUrl;
    
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "company")
    private Set<CompanyStockExchange> stockExchanges = new HashSet<CompanyStockExchange>();
}
