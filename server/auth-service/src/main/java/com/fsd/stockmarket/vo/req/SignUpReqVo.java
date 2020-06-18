package com.fsd.stockmarket.vo.req;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReqVo {

	@NotEmpty(message = "user name can't be empty")
	private String username;
	
	@NotEmpty(message = "password can't be empty")
	private String password;
	
	@NotEmpty(message = "email can't be empty")
	private String email;
	
	@NotEmpty(message = "mobile number can't be empty")
	private String mobileNumber;
}
