package com.kh.api.kakao.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kh.api.kakao.model.SocialMember;
import com.kh.api.kakao.model.service.KakaoService;

@Controller
public class LoginController {
	
	@Autowired
	private KakaoService kakaoService; // 필드주입
	
	@GetMapping("oauth")
	public String socialLogin(String code, HttpSession session) throws IOException, ParseException {
		
		//코드를 받아 쓴다. 쿼리스트링으로 넘어온 url의 값(key, value) => 파라미터
		// 토큰을 받아와야함.
		// System.out.println(code);
		
		//kakaoService.getToken(code);
		String accessToken = kakaoService.getToken(code);
		// 로그아웃에서도 엑세스 토큰을 써야하기 때문에 로그인할때 받아온 엑세스 토큰을 세션에 담아둔다.
		session.setAttribute("accessToken", accessToken);
		// 사용자정보 얻어오기. 토큰 가져오기(카카오서버로 요청-서비스)
		SocialMember sm = kakaoService.getUserInfo(accessToken);
		session.setAttribute("loginUser", sm);
		
		return "redirect:login";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		
		//세션에서 토큰 값 빼오기
		String accessToken = (String)session.getAttribute("accessToken");
		
		kakaoService.logout(accessToken);
		session.removeAttribute("loginUser");
		
	
		return "redirect:login"; // 로그아웃 성공하면 로그인페이지로 리다이렉트해준다.
	}
	
	
	
	
}
