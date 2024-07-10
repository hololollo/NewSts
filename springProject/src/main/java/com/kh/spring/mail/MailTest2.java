package com.kh.spring.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailTest2 {
	
	@Autowired
	private JavaMailSenderImpl sender;
	
	@GetMapping("mail-test")
	public String mail() {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		//제목, 내용, 첨부파일, 받는사람, 참조, 숨은참조
		
		message.setSubject("제목입니당");
		message.setText("제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당"
				+ "제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당"
				+ "제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당제목입니당"
				+ "제목입니당");
		String[] toArr = {"kangmh1021@gmail.com","khacademy1002@gmail.com","qjatn092028@gmail.com"};
		message.setTo(toArr);
		
		sender.send(message);
		
		return "redirect:/";
	}
}
