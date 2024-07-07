package com.kh.api.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.api.model.vo.AirVO;

public class ApiJavaApp {
	
	public static final String SERVICE_KEY = "3Y%2BPLDqbVXUpxDAc4HTSjOwhPeeiJeuQea%2BnKPr68DrloY8x%2Bp4cXDTqOnJB%2FKIQ%2BU5JjdH9bLOgR7wJpMRHuw%3D%3D";
	
	public static void main(String[] args) throws IOException {
		
		
		// System.out.println("하이~!");
		// 순수 Java만으로 Client Program을 만들어서 시도별 API서버로 요청 보내고 응답 받기!
		
		// 요청 보낼 URL이 필요함.!! => String타입으로 만들자."문자열"
		
		StringBuilder sb = new StringBuilder();
		// 문자열을 만드는 클래스라고 생각하자.
		sb.append("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty");
		// 콜백 url복사 붙여넣기. ?(쿼리스트링) + 필수 속성, 타입 
		sb.append("?serviceKey=");
		sb.append(SERVICE_KEY);
		// ?serviceKey= + 일반 인증키(Encoding)
		sb.append("&sidoName=");
		sb.append(URLEncoder.encode("서울", "UTF-8"));
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
		// 버퍼 특징 : 기반 스트림 없이 단독으로 존재할 수 없다.
		// 버퍼란? : 임시저장공간. I/O 성능을 향상시키기 위해 사용되는 메모리 영역. 
		// 데이터 입출력 시, 버퍼를 사용하면 여러 번의 입출력 작업을 효율적으로 처리할 수 있음.

		/*
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		언제까지 일줄 알고 계속 씀? 반복문 써야지. readLine()은 반환할 데이터가 없으면 null을 반환.*/ 
		// 변수에 값을 넣어주고..
		/* 기초
		while(true) {
			String value = br.readLine();
			if(value != null) {
				System.out.println(value);
			} else {
				break;
			}
		}
		 또는 */
		//심화
		/*
		String responseXml = "";
		while((responseXml = br.readLine()) != null) {
			System.out.println(responseXml);
		}
		*/
		
		// json으로 받으면 한줄로 오기 때문에 ※데이터를 가공해줘야함.
		
		//System.out.println(responseJson);
		/*
		AirVO air = new AirVO();
		air.setKhaiValue("57");
		*/
		// 문자열 형태의 json을 파싱해주는 라이브러리가 있음!
		//라이브러리 혼동주의
		// JsonObject, JsonArray => JSON => 자바 데이터로(GSON라이브러리)
		// + JsonParser
		// JSONObject, JSONArray => 자바 데이터 => JSON으로 (JSON라이브러리)
		
		// 파싱을 하고싶은 문자열 데이터를 가져다가 넣음. getAsJsonObject를 이용하여 반환
		//  한 줄로 이루어진 JSON 데이터일 경우, 이 메서드로 전체 응답을 읽을 수 있다. (system.out.println()으로 찍어보자.)
		String responseJson = br.readLine();
		JsonObject jsonObj = JsonParser.parseString(responseJson).getAsJsonObject();
		// JsonParser는 문자열을 JSON 데이터 구조로 변환하는 데 사용
		// System.out.println(responseJson); // String
		// System.out.println(jsonObj); // JsonObject
		
		JsonObject responseObj = jsonObj.getAsJsonObject("response"); // response라는 프로퍼티로 접근 => {} JsonObject
		// System.out.println(responseObj);
		
		JsonObject bodyObj = responseObj.getAsJsonObject("body"); // body라는 프로퍼티로 접근 => {} JsonObject
		// System.out.println("-------------------");
		// System.out.println(bodyObj); 
		// Java와 JSON은 아무런 연관이 없는데 왜 값이 키 밸류값으로 {}안에 똑같이 출력이 되지? => 라이브러리안에 Map으로 반환하게 설정되어있음.
		
		// byte, short, int, long => 기본이 정수형인 int
		int totalCount = bodyObj.get("totalCount").getAsInt(); // totalCount라는 프로퍼티로 접근 => int
		System.out.println(totalCount);
		
		// byte num1 = 22;
		// byte num2 = 126;
		// System.out.println(num1 + num2); // 자바는 같은 자료형끼리만 연산해야한다. 여기에서는 자동으로 int형으로 변환되었음.(큰 걸 우선시하는 특징)
		// System.out.println((int)num1 + (int)num2);	
		
		// 배열의 형태로 출력되기에 
		JsonArray items = bodyObj.getAsJsonArray("items"); // items라는 프로퍼티로 접근 => [] JsonArray
		System.out.println(items);
		
		//진짜 필요한 것은 배열의 객체형태에 들어있는 속성의 값들이 필요함.
		//하나의 객체형태가 하나의 저장소의 위치 이므로
		
		JsonObject firstItem = items.get(0).getAsJsonObject(); // 첫번째 요소만 뽑아오기
		// System.out.println(firstItem); 
		// 리스트. 몇개의 요소가 들어올지 모르니까.
		
		
		List<AirVO> list = new ArrayList();
		
		for(int i = 0; i < items.size(); i++) {
			
			JsonObject item = items.get(i).getAsJsonObject();
		
			AirVO air = new AirVO(); // 초기화	
			air.setPm10Value(firstItem.get("pm10Value").getAsString());
			air.setStationName(firstItem.get("stationName").getAsString());
			air.setDataTime(firstItem.get("dataTime").getAsString());
			// air.setO3Value(firstItem.get("O3Value").getAsString()); NullPointerException
			// air.setKhaiValue(firstItem.get("KhaiValue").getAsString()); NullPointerException
			
			list.add(air);
		}
		for(AirVO air : list) {
			System.out.println(air);
		}
		//Stream
		
		//자원 반납
		br.close();
		urlConnection.disconnect();
	}
}
