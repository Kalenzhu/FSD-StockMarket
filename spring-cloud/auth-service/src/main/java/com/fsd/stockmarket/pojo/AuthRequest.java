package com.fsd.stockmarket.pojo;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

	@NotEmpty(message = "user name can't be empty")
	private String username;

	@NotEmpty(message = "password can't be empty")
	private String password;
}
