package com.fsd.stockmarket.service.impl;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

//import com.fsd.stockmarket.entity.User;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.pojo.AuthResponse;
//import com.fsd.stockmarket.repository.UserRepository;
import com.fsd.stockmarket.service.UserTokenService;
import com.fsd.stockmarket.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserTokenServiceImpl implements UserTokenService {

//	@Autowired
//	private UserRepository userRepository;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	public AuthResponse getUserDetailFromToken(HttpServletRequest request) throws ServiceException {
		String authToken = this.retrieveToken(request);
		if(StringUtils.isEmpty(authToken)) {
			log.error("getUserDetailFromToken: empty token");
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "empty jwt token");
		}
		if (JwtTokenUtil.isExpiration(authToken)) {
			log.error("getUserDetailFromToken: expired token");
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST, "expired jwt token");
		}
		String username = JwtTokenUtil.getUsername(authToken);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setUsername(userDetails.getUsername());
		@SuppressWarnings("unchecked")
		Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) userDetails.getAuthorities();
		authResponse.setUsertype(authorities.toArray()[0].toString());
		authResponse.setJwtToken(authToken);
		authResponse.setHeader(authToken);
		return authResponse;
	}
	
	private String retrieveToken(HttpServletRequest request) {
		String authToken = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
		if (authToken != null && authToken.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
			authToken = authToken.substring(JwtTokenUtil.TOKEN_PREFIX.length());
			if (authToken.equals("null")) {
				return "";
			}
		} else {
			authToken = request.getParameter("JWT-Tonken");
			if (authToken == null) {
				return "";
			}
		}
		return authToken;
	}
}
