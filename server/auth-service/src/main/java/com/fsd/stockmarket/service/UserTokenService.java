package com.fsd.stockmarket.service;

import javax.servlet.http.HttpServletRequest;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.pojo.AuthResponse;

public interface UserTokenService {

	public AuthResponse getUserDetailFromToken(HttpServletRequest request) throws ServiceException;
}
