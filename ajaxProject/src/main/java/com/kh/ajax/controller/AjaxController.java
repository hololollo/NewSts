package com.kh.ajax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {
	// AJAX용 컨트롤러는 @Controller 애노테이션 말고 다른걸 쓴다.
	
	/*
	 * 1. HttpServletResponse 객체로 응답데이터를 응답하기(Stream을 이용한 방식) -> 가장 아날로그한 방식
	 */
	/*
	@GetMapping("ajax1.do")
	public void calSum(String menu, 
						int amount,
						HttpServletResponse response) throws IOException {
		
		//Integer.parseInt(""); => input요소 값이 없으면 빈문자열
		// System.out.println("사용자가 입력한 메뉴 : " + menu);
		// System.out.println("사용자가 입력한 수량 : " + amount);
		
		// 가정) 여기부터는 DB에서 일어나는 일
		int price = 0;
		switch(menu) {
			case "알밥" : price = 10000; 
				break;
			case "돈까스" : price = 12000; 
				break;
			case "서브웨이" : price = 15000; 
				break;
			case "김치찜" : price = 9000; 
				break;
			case "막국수" : price = 8000; 
			}
		price *= amount;
		
		// System.out.println(price);
		
		// 서비스 다녀와서 요청처리가 다 끝났다.
		// 요청한 페이지에 반환할 데이터를 완성해냈다!
		
		// 데이터 형식 지정
		response.setContentType("text/html; charset=UTF-8");
		// 출력
		response.getWriter().print(price); // 인풋 아웃풋스트림
	}
	*/
	
	
	/*
	 * 2. 응답할 데이터를 문자열로 반환
	 * 		=> HttpServletResponse를 사용하지 않는 방법
	 * 			=> String 반환하면 포워딩 => 응답뷰의 경로로 인식을 해서 뷰 리졸버로 전달
	 * 
	 * 따라서 스프링을 사용해서 응답데이터를 반환 할때는
	 * ==> MessageConverter로 이동하게끔 해주어야 함!! => @ResponseBody 애노테이션! 응답할때 body에 담는다
	 * 
	 * produces : 응답할 데이터 형식, 인코딩 방식..
	 * 
	 * 여기까지 했으면 아이디 중복체크 이런건 할 수 있어야 한다.
	 */
	@ResponseBody
	@GetMapping(value="ajax1.do", produces="text/html; charset=UTF-8")
	public String calSum(String menu, int amount) {
		int price = 0;
		switch(menu) {
			case "알밥" : price = 10000; 
				break;
			case "돈까스" : price = 12000; 
				break;
			case "서브웨이" : price = 15000; 
				break;
			case "김치찜" : price = 9000; 
				break;
			case "막국수" : price = 8000; 
				
			}
		price *= amount;
		
		return String.valueOf(price) + "한글"; // int형인 price를 String으로 맞춰줌. 
		// 한글이나 기타 다른문자가 올 경우 인코딩이 깨지는 경우가 있음. 따라서 응답데이터형식과 인코딩방식을 지정해줘야함.
	}
	
}
