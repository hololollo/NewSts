package com.kh.spring.mail;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailTest4 {
	@Autowired
	private JavaMailSender sender;
	
	@GetMapping("file-send")
	public String sendFile() throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		
		helper.setTo("kangmh1021@gmail.com");
		helper.setSubject("파일입니당");
		helper.setText("내용은 없습니당");
		
		// 파일첨부
		// javax.activation.DataSource
		DataSource source = new FileDataSource("C:\\Users\\kangm\\OneDrive\\바탕 화면\\학원\\부누부누.jpg");
		helper.addAttachment(source.getName(), source);
		
		//보내기
		sender.send(message);
		
		return "redirect:/";
	}
}
