package com.kh.spring.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailTest3 {
	
	@Autowired
	private JavaMailSender sender;
	
	@GetMapping("mime-send")
	public String sendMail() throws MessagingException {
		
		//Mime Message객체 생성
		MimeMessage message = sender.createMimeMessage();
		
		//마임메세지를 도와주는 헬퍼를 만들어야 함.(인자가 필수임.) 각각 만들어놓은 객체, 파일첨부여부, 인코딩방식
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
		
		helper.setSubject("형태가 있는 메일의 모양은??");
		helper.setText("<h1 style='color:orangered; font-size:40px'>헤헤</h1>", true);
		
		String str = "뭔가 고유한";
		helper.setText("<a href='http://localhost/spring/auth?str=" + str + "'>이걸로 인증하세요</a>", true);		
		
		helper.setTo("kangmh1021@gmail.com");
		
		sender.send(message);
		
		return "redirect:/";
	}
	//데이터 검증
	@GetMapping("auth")
	public String auth(String str) {
		if(str.equals("뭔가 고유한")) {
			System.out.println("인증성공");
		}else {
			System.out.println("인증실패");
		}
		return "redirect:/";
	}
	// 누가? 검증이 빠졌음.
	
}
