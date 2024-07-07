package com.kh.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardController {
	@GetMapping("air")
	public String air() {
		return "air/air-pollution";
	}
	
	@GetMapping("busan")
	public String busan() {
		return "busan/beach";
	}
	
	@GetMapping("naver")
	public String shop() {
		return "naver/shopping";
	}
	@GetMapping("map")
	public String map() {
		return "kakao/map";	
	}
	@GetMapping("foods")
	public String food() {
		return "busan/busan";
	}
	@GetMapping("login")
	public String login() {
		return "kakao/kakao-login";
	}
}
