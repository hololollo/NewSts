package com.kh.spring.mail;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailTest1 {
	// 빈 등록 안하고
	public static JavaMailSenderImpl sender;
	
	public static void main(String[] args) {
		
		JavaMailSenderImpl impl = new JavaMailSenderImpl(); // 편지봉투
		
		//계정에 관련된 설정
		impl.setHost("smtp.gmail.com"); // 어떤 smtp서버를 사용할 건지
		impl.setPort(587); // 구글 포트번호
		impl.setUsername("kangmh1021"); // 구글 계정 명
		impl.setPassword("7월 10일자 확인"); // 앱 비밀번호 발급받은 번호(16자리) =>  
		
		//보안 관련 설정
		Properties prop = new Properties(); // properties : map과 같은 역할수행(String, String으로 와야 함.)
		prop.setProperty("msil.smtp.auth", "true"); // put은 오브젝트타입이니까 
		prop.setProperty("mail.smtp.starttls.enable", "true");
		
		impl.setJavaMailProperties(prop);
		sender = impl;
		
		//메세지 작성
		SimpleMailMessage message = new SimpleMailMessage();
		
		//메세지 정보 세팅 : 제목, 내용, 첨부파일(simpleMail에선 불가), 받는사람, 참조, 숨은참조
		
		// 제목
		message.setSubject("제목");
		
		// 본문
		message.setText("본문");
		
		// 받는 이
		//message.setTo("받는사람메일주소");
		// String to = "받는사람메일주소";
		
		String[] toArr = {"kangmh1021@gmail.com","khacademy1002@gmail.com","qjatn092028@gmail.com","realnicesaturday@gmail.com"};
		message.setTo(toArr);
		
		/*
		 * 참조
		 * 
		 * 메세지객체.setCc(참조할 주소)
		 * 
		 * 숨은 참조
		 * 
		 * 메세지 객체.setBcc(숨은 참조할 주소)
		 * 
		 */
		
		// 보내기 버튼
		sender.send(message);
	}
	
	// root-context.xml에 빈등록하고 MailTest2
}
