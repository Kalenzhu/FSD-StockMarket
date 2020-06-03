package com.fsd.stockmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fsd.stockmarket.filter.PreFilter;

//@Configuration
public class ZuulFilterConfig {
	
//	  @Bean
	  public PreFilter preFilter() {
		  return new PreFilter();
	  }

}
