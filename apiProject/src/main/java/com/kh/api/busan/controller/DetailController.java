package com.kh.api.busan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DetailController {
	
	@PostMapping("foods/detail")
	public String detailPage(String title,
							 String lat,
							 String lng,
							 String description,
							 String img,
							 Model model) {
		
	model.addAttribute("title", title);
	model.addAttribute("lat", lat);
	model.addAttribute("lng", lng);
	
	String des = description.replaceAll("\n", ""); // 개행문자로
	des = des.replaceAll("\r", "");
	model.addAttribute("description", description);
	model.addAttribute("img", img);
	

	return "busan/detail";
	
	}
				
			
			
	
}	
