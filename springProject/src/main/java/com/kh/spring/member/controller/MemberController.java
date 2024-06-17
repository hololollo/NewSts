package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//선택지가 두개. 컨트롤러 vs 래스트컨트롤러
//제어하는 코드가 들어가야한다.
// 1. 데이터가공, 2. 응답
@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
	// final : 객체의 불변성을 보장할 수 있음.
	private final MemberService memberService;
	
	/*
	@RequestMapping("login.do") // RequestMapping타입의 애노테이션을 붙임으로서 HandlerMapping 등록
	public void login() {
		log.info("로그인 요청을 보냈니?");
	}
	*/
	/*
	 * spring에서 Handler가 요청 시 전달값(Parameter)을 받는 방법
	 * 
	 * 1. HttpServletRequest을 이용해서 전달받기(기존의 JSP/Servlet방식)
	 * 
	 * 해당 핸들러의 매개변수로 HttpServletRequest타입을 작성해두면
	 * DispatcherServlet이 해당 메서드를 호출할 때 request객체를 전달해서 매개변수로 주입해줌!
	 * 
	 */
	
	/*
	@RequestMapping("login.do")
	public String login(HttpServletRequest request) {
		
		
		
		// c패밀리 언어(c, c++, c#, java,  js, kotlin...)
		// get, set으로 값을 넣어주고 불러오는.
		
		// request.getParameter(인자값)
		String userId = request.getParameter("id"); // key값 == input요소의 name값
		// 클래스 안에 정해진 함수 => 메서드(클래스가 할 수 있는 동작을 정의.)
		// ex. new aaa(객체, 메서드); => 객체(특정)가 동작하는 함수
		String userPwd = request.getParameter("pwd");
		
		log.info("회원이 입력한 아이디 값 : {} ", userId);
		log.info("회원이 입력한 아이디 값 : {} ", userPwd);
		return "main";
	}
	*/
	
	/*
	 * 2. @RequestParam 애노테이션을 이용하는 방법
	 *  request.getParameter("키값")로 밸류를 뽑아오는 역할 대신해주는 애노테이션
	 *  
	 *  value/name속성의 값으로 jsp에서 작성했던 name속성값을 적으면 알아서 해당 매개변수로 주입을 해줌.
	 *  만ㅇㄱ, 넘어온 값이 비어있는 형태라면 defaultValue속성으로 기본값을 지정할 수 있음.
	 *  
	 */
	/*
	@RequestMapping("login.do")
	public String login(@RequestParam(value="id", defaultValue="aaa") String userId,
						@RequestParam(value="pwd") String userPwd) {
		log.info("회원이 입력한 아이디 값 : {}", userId);
		log.info("회원이 입력한 비밀번호 값 : {}", userPwd);
		return "main";
	}
	*/
	/*
	 * 3. RequestParam애노테이션을 생략하는 방법
	 * 
	 * 단, 매개변수 식별자를 jsp의 name속성값(요청 시 전달하는 값의 키값)과 동일하게 작성해주어야만 자동으로 값이 주입
	 * 단점이라고 한다면 2. 에 사용했던 defaultValue속성을 사용할 수 없음
	 * 
	 */
	
	/*
	@RequestMapping("login.do")
	public String login(String id, String pwd) {
		
		log.info("회원이 입력한 아이디 값 : {}", id);
		log.info("회원이 입력한 비밀번호 값 : {}", pwd);
		
		//컨트롤러가 해야 할 일! -> 제어
		// 1.데이터 가공 -> DTO
		Member member = new Member();
		member.setUserId(id);
		member.setUserPwd(pwd);
		//읽는방법?
		
		// 1.5 서비스 호출
		// memberService.login(id, pwd);
		
		// 2. 응답화면 지정
		
		return "main";
	}
	*/
	/*
	 * 4. 커맨드 객체 방식
	 * 
	 * *** 반드시 name속성값과 담고자하는 필드명이 동일해야함! + setter가 꼭 있어야함! + 기본생성자가 꼭 있어야함!!
	 * 
	 * 해당 매서드의 매개변수로
	 * 요청 시 전달값을 담고자 하는 클래스의 타입을 지정한 뒤
	 * 요청 시 전달값의 키값(jsp의 name속성값)을 클래스의 담고자하는 필드명과 동일하게 작성
	 * 
	 * 스프링 컨테이너가 해당 객체를 기본생성자로 생성한 후 내부적으로 setter메서드를 찾아서 요청 시 전달값을 해당 필드에 담아줌(setter injection)
	 */
	
	@RequestMapping("login.do")
	public String login(Member member) {
		log.info("가공된 맴버객체 : {}", member);
		// 여기까지가 가공 끝! 
		// 이후 비즈니스로직 가공
		
		Member loginMember = memberService.login(member);
		
		// 1. 자바에서는 대입연산자를 기준으로 왼쪽과 오른쪽의 자료형이 같아야 함.
		// 2. 왼쪽(1순위) 오른쪽(2순위) 대입연산자(3순위)
		
		// 대입연산자 기준 왼쪽은 공간, 오른쪽은 연산
		
		// Object obj = new Member();
		// 1.오브젝트 타입의 obj에 2.멤버 객체를 생성해서 3.주소값을 대입
		
		
		//자바에서는 대입연산자를 기준으로 왼쪽과 오른쪽의 자료형이 같아야 함.
		//자바에서는 동일한 타입의 값끼리만 연산이 가능함 => 연산의 결과도 동일한 타입이어야 함.
		// ex. system.out.println(1 + 1.1 ); => 2.1 why? double타입이 더 큼.(자동형변환)
		
		
		log.info("멤버다 멤버 : {}", loginMember);
		return "main";
	}
	
	/*
	 * localhost/spring ==> ??
	 * localhost/spring/member => 회원관련
		
		@GetMapping(get)
		@PostMapping(insert)
		@DeleteMapping(delete)
		@PutMApping(update)
	 */
	/*
	// REST방식의 URL만들기
	// localhost/spring/member/12
	@GetMapping("/member/{id}")
	public void restTest(@PathVariable String id) {
		log.info("앞단에서 넘긴값 : {}", id);
	}
	*/
	
	/*
	 * 요청 처리 후 응답데이터를 담고 응답페이지로 포워딩 또는 리다이렉트 하는 방법
	 * 
	 * 1. 스프링에서 제공하는 Model 객체를 사용하는 방법
	 * forwarding할 응답 뷰로 전달하고자 하는 데이터를 key-value형태로 담을 수 있는 영역.
	 * Model객체는 requestScope
	 */
	/*
	@PostMapping("login.do")
	public String login(Member member, Model model, HttpSession session) {
		
		Member loginUser = memberService.login(member);
		
		if(loginUser == null) { // 로그인 실패 => 에러문구 requestScope에 담아서 에러페이지로 포워딩
			model.addAttribute("errorMsg", "로그인에 실패했어요 ㅠㅠ");
			// 포워딩
			
			//ex. WEB-INF/views/common/errorPage.jsp
			// -prefix : /WEB-INF/views/ 생략 가능
			// -suffix: .jsp 생략가능
			return "common/errorPage";
		} else { // 로그인 성공! => loginUser를 sessionScope에 담고 응답화면
			session.setAttribute("loginUser", loginUser);
			
			// redirect 방식 ~
			return "redirect:/";
		}
		// WEB-INF/views/main.jsp
		// return "main";
	}
	*/
	
	
	/*
	 * 2. Spring에서 제공하는 ModelAndView타입을 사용하는 방법
	 * Model은 데이터를 key-value세트로 담을 수 있는 공간이라고 한다면
	 * View는 응답 뷰에 대한 정보를 담을 수 있는 공간.
	 * 
	 * Model객체와 View 가 결합된 형태의 객체. View는 단독으로 사용이 불가하기때문에 ModelAndView로 사용된다.
	 */
	@PostMapping("login.do")
	public ModelAndView login(Member member, ModelAndView mv, HttpSession session) {
		Member loginUser = memberService.login(member);
		
		if(loginUser != null) {
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("errorMsg", "로그인 실패 ㅠㅜ..").setViewName("common/errorPage");
		}
		return mv;
	}
}
