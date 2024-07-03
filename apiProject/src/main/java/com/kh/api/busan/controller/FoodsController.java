package com.kh.api.busan.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.api.pollution.controller.AirController;

@RestController
@RequestMapping("foods")
public class FoodsController {
	@GetMapping(value="/{pageNo}", produces="application/json; charset=UTF-8")
	public String foods(@PathVariable int pageNo) throws IOException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("http://apis.data.go.kr/6260000/FoodService/getFoodKr");
		sb.append("?serviceKey=");
		sb.append(AirController.SERVICE_KEY);
		sb.append("&pageNo=" + pageNo);
		sb.append("&numOfRows=10");
		sb.append("&resultType=json"); // 리턴타입 아니다. 문서확인
		
		String url = sb.toString();

		URL requestUrl = new URL(url);

		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection(); 

		urlConnection.setRequestMethod("GET"); 

		

		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		
		String responseData = br.readLine();
		
		//자원반납
		br.close();
		urlConnection.disconnect();
		
		return responseData;
	}
}
