package com.kh.api.kakao.model.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.kh.api.kakao.model.SocialMember;

@Service
public class KakaoService {

	public String getToken(String code) throws IOException, ParseException {
		
		String tokenUrl = "https://kauth.kakao.com/oauth/token"; // 받아올 토큰 (POST)
		// 자바 프로그래밍 언어에서 토큰을 받아오기 위한 url, 커넥션
		URL url = new URL(tokenUrl);
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		
		// 전송방식 정해주기
		urlConnection.setRequestMethod("POST"); 
		urlConnection.setDoOutput(true); // (post로 보낼때는 기본이 false인 ture값을 넣어줘야 한다.)
		
		// 출력용 outputstream. 성능향상을 위해 bufferedWriter를 쓴다. 보조스트림이기 때문에 기반스트림이 있어야 한다. 따라서 OutputStreamWriter로 보조스트림을 한번 더 사용.
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
		// 자바에서의 스트림의 출력(특징) : 1. 바이트단위 2. 기반스트림, 보조스트림 나눠져있다. 3. 단반향이고 선입선출 4. 입력출력할때 사용한다. 
		
		StringBuilder sb = new StringBuilder();
		sb.append("client_id=924b1cc2f4e15791c7a9a87ee486d739");
		sb.append("&grant_type=authorization_code");
		sb.append("&redirect_uri=http://localhost/api/oauth");
		sb.append("&code=");
		sb.append(code);
		
		//출력
		bw.write(sb.toString()); // bufferedWriter출력은 write메서드로 출력
		bw.flush(); // 응답코드가 어떻게 돌아올지(response code) 확인가능. 출력 버퍼에 있는 데이터를 강제로 내보내는 역할
		// 응답코드확인
		// System.out.println(urlConnection.getResponseCode()); true나오면 
		
		//입력받아오기 *7월1일*
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	
		String line = "";
		String responseData = "";
		
		while((line = br.readLine()) != null) {
			responseData += line;
		}
		System.out.println(responseData);
		// 엑세스 토큰이 반환됨.(문자열)
		
		// 문자열로 왔으니 JSON형태로 가공(JSON Object로 파싱)해줘야 함.
		
		JSONParser parser = new JSONParser();
		JSONObject element = (JSONObject)parser.parse(responseData);
		
		String accessToken = element.get("access_token").toString();
		
		//자원반납
		br.close();
		bw.close();
		
		//컨트롤러로 리턴
		return accessToken;
	}
	
	// 로그아웃 하려면 엑세스토큰을 POST로 보내줘야 한다.
	public void logout(String accessToken) {
		
		String logoutUrl = "https://kapi.kakao.com/v1/user/logout";
		URL url;
		HttpURLConnection conn;
		
		try {
			url = new URL(logoutUrl);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer" + accessToken); // 요청 보낼값
			
			// 결과 확인용
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String responseData = "";
			String line = "";
			while((line = br.readLine()) != null) {
				responseData = line;
			}
			System.out.println(responseData);
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//사용자 정보 가져오기.
	public SocialMember getUserInfo(String accessToken) {
		
		//요청보내기
		String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
		SocialMember sm = null;
		
		
		try {
			URL url = new URL(userInfoUrl);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Authorization", "Bearer" + accessToken);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String responseData = br.readLine(); // JSON객체는 한줄로 표시되니까.
			
			// System.out.println(responseData);
			
			JSONObject responseObj = (JSONObject)new JSONParser().parse(responseData);
			// System.out.println(responseData)으로 뽑아낸 JSON값 중 properties
			JSONObject propObj = (JSONObject)responseObj.get("properties");
			
			// long id = (long)responseObj.get("id");
			
			sm = new SocialMember(); // 객체 초기화
			sm.setId(responseObj.get("id").toString());
			sm.setNickName(propObj.get("nickname").toString());
			sm.setThumbnailImg(propObj.get("thumbnail_image").toString());
			
			 System.out.println(sm);
			/*
			result obj = mapper.selectById(sm);
			
			if(obj != null) {
				return obj; // 있으면 로그인
			} else {
				mapper.save(obj); // 없으면 DB에 저장
			}
			*/
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return sm;
	}
	
	/*
	 * public void login() {
	 * 		mapper.login(member);
	 */
	
	
}
