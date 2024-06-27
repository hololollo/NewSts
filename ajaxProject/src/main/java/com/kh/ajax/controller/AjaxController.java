package com.kh.ajax.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Menu;

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
	@ResponseBody // 메세지 컨버터로 가서 ~~
	@GetMapping(value="ajax2.do", produces="application/json; charset=UTF-8")
	public String selectMenu(int menuNumber) {
		
		// 요청처리를 잘 했다는 가정하에 데이터 응답
		/*
		 * DB에 존재하는 메뉴 테이블 --> 출력하기 위해 작성
		 * ----------------------------
		 * | 메뉴번호 | 메뉴이름 | 가격 | 재료 |
		 * ----------------------------
		 * |   1   |  순두부 | 9500| 순두부|
		 *  ....
		 * ----------------------------
		 * 출력하기 위해서 : 1, 순두부, 9500, 순두부 라는 4개의 값을 출력해야 함.   
		 * 
		 * 자바와 자바스크립트는 아무런 연관이 없는데 어떻게 던질거야?
		 * 과거에는 xml형식으로 던졌다고 함.(태그방식 등 html과 유사함.)
		 * 
		 * 현재는 JSON형태로 보냄.
		 * Java Script Object Notation => 자바스크립트 객체 표기법(데이터 형식)
		 * 
		 * 2가지로 나눌 수 있음.
		 * [](배열형)
		 * {}(객체형)
		 * 
		 * 오늘 날 웹에서 데이터를 주고받는 거의 대부분은 JSON형태로 이루어짐.
		 * JAVA와 JSON은 연관이 없는데? 파싱.
		 * 
		 */
		
		/*
		 * 
		 * 예시 (자바스크립트 객체)
		 * {
		 *  "menuNumber" : "1",
		 *  "menuName" : "순두부",
		 *  "price" : "9500",
		 *  "material" : "순두부"
		 * }
		 * DB와 VO에서 가져와 이렇게 만들어서 뷰에 넣어줘야 함.
		 */
		
		// 요청 보내서 결과 잘 받았음
		//menuService.findByMenuNumber(menuNumber);
		Menu menu = new Menu(1, "순두부", 9500, "순두부");
		// 자바스크립트가 이것을 해석할 수 있는 형태(위에처럼) 만들어서 보내줘야 함.
		
		
		StringBuilder sb = new StringBuilder();
		/*
		sb.append("{");
		sb.append("menuNumber : " + "'" + menu.getMenuNumber() + "'," );
		sb.append("menuName : " + "'" +menu.getMenuName()+ "'," );
		sb.append("price : " + "'" +menu.getPrice()+ "'," );
		sb.append("material : " + "'" +menu.getMaterial() + "'" );
		sb.append("}");
		return sb.toString();
		//문자열 데이터로 변환
		 */
		
		// => 노가다를 json-simple, gson 등록했던 걸 이용해서
		
		//json라이브러리 내장 클래스(simplejson)
		JSONObject jObj = new JSONObject(); // => key-value형태로 넣음(map을 상속받은 클래스임.)
		jObj.put("menuNumber", menu.getMenuNumber());
		jObj.put("menuName", menu.getMenuName());
		jObj.put("price", menu.getPrice());
		jObj.put("material", menu.getMaterial());
		
		return jObj.toJSONString(); // 문자열 형태로 만들어서 보내줄 수 있다. 
		// 서버에서 문자열이 아닌 데이터타입으로 인식할 수 있게 produces에 text대신 json이라고 명명해줘야 함.
		// 이렇게 만들 수 있다.
	}
	
	//Gson사용방법
	/*
	@ResponseBody
	@GetMapping(value="ajaxe3.do", produces="application/json; charset=UTF-8")
	public String ajax3Method(int menuNumber) {
		Menu menu = new Menu(menuNumber, "순두부찌개", 9500, "순두부");
		//DB에서 SELECT해옴
		
		return new Gson().toJson(menu);
		}
		*/
		// Gson 라이브러리 낮은버전 사용법
		
		
	
	@ResponseBody
	@GetMapping(value="ajaxe3.do", produces="application/json; charset=UTF-8")
	public Menu ajax3Method(int menuNumber) {
		Menu menu = new Menu(menuNumber, "순두부찌개", 9500, "순두부");
		//DB에서 SELECT해옴
		
		return menu;
		//Gson 라이브러리 높은버전에서는 이렇게 사용가능.
	
	}
	
	@ResponseBody
	@GetMapping(value="find.do", produces="application/json; charset=UTF-8")
	public String findAll() {
		
		List<Menu> menus = new ArrayList();
		menus.add(new Menu(1, "김치찌개", 9500, "김치"));
		menus.add(new Menu(2, "순두부찌개", 12000, "순두부"));
		menus.add(new Menu(3, "된장찌개", 8000, "된장"));
		// selectList조회결과가 menus에 담겨있음
		// List<Menu> list = menuService.findAll();
		
		/* 객체 3개를 만듦. 자바스크립트 에서 담을 저장소 : 배열(객체배열)
		 *
		 *
		 *[ 
		 * {
		 * 		menuNumber : 1,
		 * 		menuNAme : "김치찌개",
		 * 		price : 9500,
		 * 		material : "순두부"
		 * },
		 * {
		 * 		menuNumber : 2,
		 * 		menuNAme : "순두부찌개",
		 * 		price : 12000,
		 * 		material : "김치"
		 * },
		 * {
		 * 		menuNumber : 3,
		 * 		menuNAme : "된장찌개",
		 * 		price : 8000,
		 * 		material : "된장"
		 * }
		 *] 
		 *
		 *
		 */
		/*
		JSONObject jObj1 = new JSONObject(); // => key-value형태로 넣음(map을 상속받은 클래스임.)
		jObj1.put("menuNumber", menus.get(0).getMenuNumber());
		jObj1.put("menuName", menus.get(0).getMenuName());
		jObj1.put("price", menus.get(0).getPrice());
		jObj1.put("material", menus.get(0).getMaterial());

		JSONObject jObj2 = new JSONObject(); // => key-value형태로 넣음(map을 상속받은 클래스임.)
		jObj2.put("menuNumber", menus.get(1).getMenuNumber());
		jObj2.put("menuName", menus.get(1).getMenuName());
		jObj2.put("price", menus.get(1).getPrice());
		jObj2.put("material", menus.get(1).getMaterial());
		
		JSONObject jObj3 = new JSONObject(); // => key-value형태로 넣음(map을 상속받은 클래스임.)
		jObj3.put("menuNumber", menus.get(2).getMenuNumber());
		jObj3.put("menuName", menus.get(2).getMenuName());
		jObj3.put("price", menus.get(2).getPrice());
		jObj3.put("material", menus.get(2).getMaterial());
		*/
		
		// 왜 배열 못씀? [] 에다가 담아줄 수 있는
		
		// JSONArray jArr = new JSONArray();
		/*
		jArr.add(jObj1);
		jArr.add(jObj2);
		jArr.add(jObj3);
		*/ 
		// 이렇게 쓰는게 아니라 반복문으로 해야하는데 이것마저 번거롭다.
		/*
		for(int i = 0; i < menus.size(); i++) {
			JSONObject jObj = new JSONObject(); // => key-value형태로 넣음(map을 상속받은 클래스임.)
			jObj.put("menuNumber", menus.get(i).getMenuNumber());
			jObj.put("menuName", menus.get(i).getMenuName());
			jObj.put("price", menus.get(i).getPrice());
			jObj.put("material", menus.get(i).getMaterial());
		
			jArr.add(jObj);
		} 
		*/
		return new Gson().toJson(menus); // 그래서 Gson을 사용
	}
}
