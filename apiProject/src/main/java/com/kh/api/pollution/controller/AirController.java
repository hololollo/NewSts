package com.kh.api.pollution.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pollution")
public class AirController {
	
	public static final String SERVICE_KEY = "3Y%2BPLDqbVXUpxDAc4HTSjOwhPeeiJeuQea%2BnKPr68DrloY8x%2Bp4cXDTqOnJB%2FKIQ%2BU5JjdH9bLOgR7wJpMRHuw%3D%3D";
	
	@GetMapping(produces="application/json; charset=UTF-8")
	public String airPollution(String sidoName) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty");
		sb.append("?serviceKey=");
		sb.append(SERVICE_KEY);
		// ?serviceKey= + 일반 인증키(Encoding)
		sb.append("&sidoName=");
		sb.append(URLEncoder.encode(sidoName, "UTF-8"));
		sb.append("&returnType=json");
		//서울 이라는 한글로 인하여 오류가 발생할 수 있다. 예외처리를 해줘야 한다.
		String url = sb.toString();
		
		// System.out.println(url);
		// Java코드를 가지고 URL로 요청을 보낼 것!
		// HttpURLConnection이라는 객체를 활용해서 API서버로 요청해야 한다.
		// HttpURLConnection을 얻어내기 위해서 필요한 객체 : URL
		// 1. java.net.URL로 객체 생성 => 생성자 호출 시 url값을 인자값으로 전달!
		URL requestUrl = new URL(url);
		// 2. 생성한 url객체를 가지고 HttpURLConnection객체를 생성
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection(); // openConnection메서드 활용
		// openConnection메서드는 HttpURLConnection의 상위클래스인 URLConnection을 반환한다.
		// 3. 요청에 필요한 설정
		urlConnection.setRequestMethod("GET"); // 값이 많으면 PROPERTY로.
		// 순수 자바에서 요청하고 요청받는 것을 입력(input)과 출력(output)이라고 한다. 즉, Stream(통로)이 필요.
		
		//4.API서버와 스트림 연결
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		
		String responseData = br.readLine();
		//자원반납
		br.close();
		urlConnection.disconnect();
		
		return responseData;
	
	}
	
	
	@GetMapping(value="/xml", produces="application/xml; charset=UTF-8")
	public String xmlPollution(String sidoName) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty");
		sb.append("?serviceKey=");
		sb.append(SERVICE_KEY);
		// ?serviceKey= + 일반 인증키(Encoding)
		sb.append("&sidoName=");
		sb.append(URLEncoder.encode(sidoName, "UTF-8"));
		sb.append("&returnType=xml");
		//서울 이라는 한글로 인하여 오류가 발생할 수 있다. 예외처리를 해줘야 한다.
		String url = sb.toString();
		
		// System.out.println(url);
		// Java코드를 가지고 URL로 요청을 보낼 것!
		// HttpURLConnection이라는 객체를 활용해서 API서버로 요청해야 한다.
		// HttpURLConnection을 얻어내기 위해서 필요한 객체 : URL
		// 1. java.net.URL로 객체 생성 => 생성자 호출 시 url값을 인자값으로 전달!
		URL requestUrl = new URL(url);
		// 2. 생성한 url객체를 가지고 HttpURLConnection객체를 생성
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection(); // openConnection메서드 활용
		// openConnection메서드는 HttpURLConnection의 상위클래스인 URLConnection을 반환한다.
		// 3. 요청에 필요한 설정
		urlConnection.setRequestMethod("GET"); // 값이 많으면 PROPERTY로.
		// 순수 자바에서 요청하고 요청받는 것을 입력(input)과 출력(output)이라고 한다. 즉, Stream(통로)이 필요.
		
		//4.API서버와 스트림 연결
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		
		String responseData = "";
		String line;
		
		while((line = br.readLine()) != null) {
			responseData += line;
		}
		//자원반납
		br.close();
		urlConnection.disconnect();
		
		return responseData;
	
	}
}
