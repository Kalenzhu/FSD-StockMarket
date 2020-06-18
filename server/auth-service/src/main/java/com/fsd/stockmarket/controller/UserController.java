package com.fsd.stockmarket.controller;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fsd.stockmarket.entity.User;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.pojo.Result;
import com.fsd.stockmarket.repository.UserRepository;
import com.fsd.stockmarket.service.MailService;
import com.fsd.stockmarket.utils.BeanUtilsCopy;
import com.fsd.stockmarket.vo.req.SignUpReqVo;
import com.fsd.stockmarket.vo.req.UpdateSettingReqVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {

	@Value("${spring.mail.loginlink}")
	private String loginlink;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailService mailService;

	@PostMapping("/signup")
	public Result<String> signup(@RequestBody SignUpReqVo signUpReqVo) throws Exception {
		User existUser = userRepository.findByUsername(signUpReqVo.getUsername());
		if(existUser != null) {
			throw new ServiceException(ServiceErrorCode.BAD_REQUEST.value(), "username already exist");
		}
		User user = new User();
		user.setConfirmed(false);
		user.setUserType("user"); // user type =1, admin = 0
		BeanUtilsCopy.copyPropertiesNoNull(signUpReqVo, user);

		try {
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("db error >>> ", e.getMessage());
			return new Result<String>("Sign up failed, please check your enters, you can try once again!", ServiceErrorCode.INTERNAL_ERROR.value());
		}

		try {
			// send email
			mailService.sendHTMLMail(signUpReqVo.getEmail(), signUpReqVo.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("html email send failed!", e.getMessage());
			return new Result<String>("Email sent failed since net issue! Pleasae re-signup later", INTERNAL_SERVER_ERROR.value());
		}
		return new Result<String>("Confirmation email have sent to you, please go to your mailbox to confirm first!");
	}

	@GetMapping("/confirmed/{username}")
	public Result<String> activeUserByUsername(@PathVariable("username") String username) throws Exception {
		if (userRepository.saveUserByUsernameAndConfirmed(username, true) > 0) {
			log.info("User have confirmed!");
			return new Result<String>("<a href='" + loginlink + "'>please click here to login system</a>");
		}
		return new Result<String>("User confirm action failed!");
	}

	@PostMapping("/settings")
	public Result<String> updateUserInfoList(@RequestBody UpdateSettingReqVo updateSettingReqVo) throws Exception {
		String username = updateSettingReqVo.getUsername();
		String oldpw = updateSettingReqVo.getPassword();
		String newpw = updateSettingReqVo.getNewPassword();

		// validate old pw
		User oneuser = userRepository.findByUsername(username);
		if (oneuser == null || !oneuser.getPassword().equals(oldpw)) {
			return new Result<String>("Your old password is not correct", ServiceErrorCode.BAD_REQUEST.value());
		}

		// update pw
		oneuser.setPassword(newpw);
		try {
			userRepository.save(oneuser);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Passwordw db error >>> ", e.getMessage());
			return new Result<String>("Update password failed", ServiceErrorCode.INTERNAL_ERROR.value());
		}
		
		try {
			// send email
			String email = oneuser.getEmail();
			mailService.sendNewPasswordEmail(email, newpw);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Send txt fail." + e.getMessage());
		}
		return new Result<String>("Password change success, New password sent to your mail, please re-login with new pasword!");
	}

	@GetMapping("/logout/{username}")
	public Result<String> logout(@PathVariable("username") String username) throws Exception {
		return new Result<String>("You have logout successfully!");
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(UNAUTHORIZED)
	public Result<String> handleAuthentication401Exception(AuthenticationException exception)
			throws Exception {
		return new Result<String>(exception.getMessage(), UNAUTHORIZED.value());
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(FORBIDDEN)
	public Result<String> handleAuthentication403Exception(AuthenticationException exception)
			throws Exception {
		return new Result<String>(exception.getMessage(), FORBIDDEN.value());
	}
}
