package com.fsd.stockmarket.vo.req;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSettingReqVo {

	@NotEmpty(message = "user name can't be empty")
	private String username;
	
	@NotEmpty(message = "password can't be empty")
	private String password;
	
	@NotEmpty(message = "new password can't be empty")
	private String newPassword;
}
