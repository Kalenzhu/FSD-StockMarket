package com.fsd.stockmarket.config;

import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Configuration
//@EnableResourceServer
public class ResourceServerConfig {
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

//	@Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.exceptionHandling()
//            .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//            .and()
//            .requestMatchers()
//            // the resource you want to protect, 
//            // the url that resource server protect, means you can use valid oauth token to access it
//            .antMatchers("/api/**")
//            .and()
//            .authorizeRequests()
//            .anyRequest()
//            .authenticated()
//            .and()
//            .csrf()
//        	.disable();
//    }
}
