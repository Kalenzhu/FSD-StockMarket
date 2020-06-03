package com.fsd.stockmarket.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zuogan
 * @date 2020-04-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceException extends Exception {

	private static final long serialVersionUID = -281930670838372504L;

	private Integer errorCode;
	
	private String errorMessage;
	
	public ServiceException(ServiceErrorCode codeEnum, String errorMessage) {
		this(codeEnum.value(), errorMessage);
	}
}
