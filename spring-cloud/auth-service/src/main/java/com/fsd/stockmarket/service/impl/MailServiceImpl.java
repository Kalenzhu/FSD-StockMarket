package com.fsd.stockmarket.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.fsd.stockmarket.service.MailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
	
	@Value("${spring.mail.maillink}")
	private String maillink;
	  
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private JavaMailSender javaMailSender;

    /**
    * 发送简单文本文件 for test
     */
    public void sendSimpleEmail(){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("zuogan@aliyun.com");
            message.setTo("zuogan@cn.ibm.com");
            message.setSubject("hello");
            message.setText("helloha");
            message.setCc("413231621@qq.com");
            mailSender.send(message);

        }catch (Exception e){
        	e.printStackTrace();
            log.error("Send txt fail.", e);
        }
    }

    /**
     * a发送html文本
     * @param
     * @throws MessagingException 
     */
    @Async
    public void sendHTMLMail(String email, String username) throws MessagingException{
    
    	MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("zuogan@aliyun.com");
            messageHelper.setTo(email);
            messageHelper.setSubject("Welcome to stock macket system");
            messageHelper.setText("<a href='"+ maillink + username + "'>Please click here to confirm your sign up!</a>", true);
            mailSender.send(mimeMessage);
            log.info("Send new register mail success");
    }
    
    /**
     * a发送new password
     */
    public void sendNewPasswordEmail(String email, String newpassword){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("zuogan@aliyun.com");
            message.setTo(email);
            message.setSubject("Your New Password to Login stock macket system");
            message.setText("Your New Password >>>> "+ newpassword);
            mailSender.send(message);
            log.info("Send new password mail success");
    }
        
}
