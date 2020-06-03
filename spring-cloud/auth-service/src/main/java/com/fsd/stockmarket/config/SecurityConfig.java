package com.fsd.stockmarket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fsd.stockmarket.security.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	  @Autowired
	  private UserAuthenticationEntryPoint userAuthenticationEntryPoint;
	  @Autowired
	  private UserAccessDeniedHandler userAccessDeniedHandler;
	  @Autowired
	  private JWTAuthenticationFilter jwtAuthenticationFilter;

	  @Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }

	  @Bean
	  @Override
	  public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	  }

	  @Override
	  protected void configure(HttpSecurity httpSecurity) throws Exception {
	      httpSecurity.csrf().disable() // diable csrf
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // use JWT，don't create session
		    .and()
		    .exceptionHandling().accessDeniedHandler(userAccessDeniedHandler)
		    .authenticationEntryPoint(userAuthenticationEntryPoint) //
		    .and().authorizeRequests() // enable authorize HttpServletRequest
		    .antMatchers("/login").permitAll() // permit for login
		    .antMatchers("/signup").permitAll() // permit for sign up
		    .antMatchers("/confirmed/**").permitAll() // permit for confirm user
		    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // 给所有预请求方法放行
		    .antMatchers("/admin/**").hasRole("admin") // only allowed for role "admin" case-sensitive
		    .anyRequest().authenticated() // need authorize for all the others
		    .and()
		    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // JWT based security filter
		    .headers().cacheControl(); // disable page caching
	  }
}
