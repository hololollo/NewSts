package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
//@RequestMapping("/member/")
public class MemberController {
	// final : 객체의 불변성을 보장할 수 있음.
	private final MemberService memberService;
	private final BCryptPasswordEncoder bcryptPasswordEncoder; // spring-security.xml 빈등록
	private final JavaMailSender sender;
	
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
	 *  만약, 넘어온 값이 비어있는 형태라면 defaultValue속성으로 기본값을 지정할 수 있음.
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
		// 1.오브젝트 타입의 obj에 2.멤버 객체를 생성해서 3.★주소값★을 대입
		
		
		//자바에서는 대입연산자를 기준으로 왼쪽과 오른쪽의 자료형이 같아야 함.
		//자바에서는 동일한 타입의 값끼리만 연산이 가능함 => 연산의 결과도 동일한 타입이어야 함. !!
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
		log.info("평문 : {}", member.getUserPwd());
		String encPwd = bcryptPasswordEncoder.encode(member.getUserPwd());
		log.info("암호문 : {}", encPwd );
		
		//matches()
		// member => userId필드 : 사용자가 입력한 아이디값
		//		  => userPwd필드 : 사용자가 입력한 비밀번호 값(평문)
		
		//MEMBER테이블에 사용자가 입력한 userId값이 존재하고 STATUS컬럼의 값이 'Y'와 일치한다면
		
		//loginUser : 조회된 ResultSet의 컬럼의 값이 필드에 차곡차곡담긴 Member의 객체의 주소값!
		//			=> userPwd필드 : DB에 기록된 암호화된 비밀번호(암호문)
		
		
		//BCriptPasswordEncoder객체 matches()
		// matches(평문, 암호문)
		
		// 암호문에 포함되어있는 Salt값을 판단해서 평문에 SAlt값을 더해서 암호화를 반복하여 두 값이 같은지 비교!
		// 일치하면 true / 그렇지않으면 false
		
		// if(bcryptPasswordEncoder.matches(member.getUserPwd(), loginUser.getUserPwd()) && loginUser != null) {
		// => nullpointerException 발생되는 코드. 
		if(loginUser != null && bcryptPasswordEncoder.matches(member.getUserPwd(), loginUser.getUserPwd())) {
			session.setAttribute("loginUser", loginUser); // null이 아니고 ~~이어야만 아래코드를 실행. 하나라도 틀리면 실행X.
			session.setAttribute("alertMsg", "로그인 성공!");
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("errorMsg", "로그인 실패 ㅠㅜ..").setViewName("common/errorPage");
		}
		return mv;
	}
	
	//logout.do를 받아줄 핸들러가 필요.
	@GetMapping("logout.do")
	public String logout(HttpSession session) {
		
		// sessionScope에 존재하는
		// loginUser 제거
		session.removeAttribute("loginUser");
		// 다른방법. session.invalidate(); -> 세션 초기화. 만약 로그인 말고 다른것도 담겨있으면? 싹다 날라감.
		
		
		return "redirect:/";
	}
	
	
	
	@GetMapping("enroll.do")
	public String enrollForm() {
	
		// WEB-INF/views/member/enrollForm.jsp prefix(WEB-INF)
		// WEB-INF/views/                 .jsp suffix((.jsp)
		
		
		return "member/enrollForm"; 
	}
	
	@PostMapping("join.do") // 입력폼 사용으로 post
	public String join(Member member, Model model, HttpSession session) {
		
		// log.info("회원가입 객체 : {}", member);
		// 1. 한글 깨짐! => web.xml에 스프링이 제공하는 인코딩 필터 등록!
		// 2. 비밀번호가 사용자가 입력한 있는 그대로의 평문(plain text -? 비밀번호는 꼭 암호화 해야함.)
		log.info("평문 : {}", member.getUserPwd());
		String encPwd = bcryptPasswordEncoder.encode(member.getUserPwd());
		log.info("암호문 : {}", encPwd );

		member.setUserPwd(encPwd);
		//Insert할 데이터가 필드에 담긴 Member객체의 userPwd필드에 평문이 아닌 암호문을 담아서 DB로 보내기.
		
		// 2. 응답화면 지정
		String viewName = "";
		if(memberService.insert(member) > 0) { // 성공 => 메인
			session.setAttribute("alertMsg", "회원가입 성공!");
			//return "redirect:/";
			viewName = "redirect:/";
		} else { // 실패 => 에러메세지 담아서 에러페이지로 포워딩
			session.setAttribute("alertMsg", "회원가입 실패!");
			viewName="common/errorPage";
		}
		return viewName;
	}
	
	//responseEntity<T>?
	
	@GetMapping("mypage.do")
	public String myPage() {
		return "member/myPage";
	}
	
	@PostMapping("update.do")
	public String update(Member member, HttpSession session, Model model) {
		
		log.info("수정요청 멤버 : {}", member);
		
		//업데이트 성공이면 1, 아니면 0
		if(memberService.update(member) > 0) {
			
			// 1. 포워딩 => 중복되는요소가 있는경우 만약 jsp이름을 바꾼다면? 저것도 고치고 이거도 고치고 할 일이 두배(유지보수성↑).
			// return "member/myPage";
			
			// DB로부터 수정된 회원정보를 다시 조회해서 sessionScope에 loginUser라는 키값으로 덮어씌워주어야 함.
			session.setAttribute("loginUser", memberService.login(member));
			// alert을 띄워 줄 문자열 데이터(회원정보 수정 성공했어요~!)를 어디다 담아주기
			session.setAttribute("alertMsg", "회원 정보 수정 성공!");
			// 2. 리다이렉트 => 만들어 놓은것을 다시불러와서 그대로 쓰는것이기 때문에 (로그인시점에서의 session값과 변경된 sql 테이블 값이 일치하지 않게되고,) 결론적으로 갱신되지 않음!
			return "redirect:mypage.do";
		} else {
			model.addAttribute("errorMsg", "정보 수정에 실패했습니다.");
			return "common/errorPage";
		}		

	}
	// 기획할 때 누가 어떤 매핑값을 쓸지, 메서드이름 등등.. 겹치지 않게
	
	//RequestMapiing("/member/")
	//RequestMapiing("/board/")
	// => spring/member/update.do
	// => spring/board/update.do
	
	@PostMapping("delete.do")
	public String delete(Member member, HttpSession session, Model model) {
		
		// 아이디/비밀번호 값을 잘 맞게 썻는지 확인.
		// 매개변수 Member => userPwd : 사용자가 입력한 비밀번호 평문.
		// session의 loginUser 키값으로 저장되어있는 Member객체의 userPwd필드 : DB에 기록된 암호화된 비밀번호
		
		String plainPwd = member.getUserId();
		String encPwd = ((Member)session.getAttribute("loginUser")).getUserPwd();
		// 1절 Member의 주소 : session.getAttribute("loginUser")
		// 2절 : .getUserPwd(); Object타입에 getUserPwd()라는 메서드는 없음. 
		// => 1절이 Member로 형변환이 되어야 2절인 .getUserPwd();를 받을 수 있음.
		
		
		// 비밀번호가 사용자가 입력한 평문을 이용해서 만든 암호문일 경우
		if(bcryptPasswordEncoder.matches(plainPwd, encPwd)) {
			
			if(memberService.delete(member.getUserId()) > 0) {
				session.setAttribute("alertMsg", "탈퇴 성공");
				session.removeAttribute("loginUser");
				return "redirect:/";
			} else {
				model.addAttribute("errorMsg","회원탈퇴에 실패했습니다.");
				return "common/errorPage";
			}
			// memberService.delete(member.getUserId());
		} else {
			session.setAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
			return "null";
		}
		
	}
	@ResponseBody
	@GetMapping("idCheck.do")
	public String checkId(String checkId) {
		// log.info(checkId);
		
		//int result = memberService.idCheck(checkId); // SQL문 어떻게 쓸껀지 미리 정해놔야 함.
		// 1(NNNNY)이면 중복이 존재하고 0(NNNNN)이면 중복이 존재하지 않는다.
		
		/*
		if(result > 0) { // 이미 존재하는 아이디
			return "NNNNN";
		}else {
			return "NNNNY";
		} =>
		*/
		//return result > 0 ? "NNNNN" : "NNNNY"; 
		// =>
		return memberService.idCheck(checkId) > 0 ? "NNNNN" : "NNNNY";
	}
	@ResponseBody
	@GetMapping("pwdCheck.do")
	public String checkPwd(String checkPwd) {
		// log.info(checkId);
		
		int checkResultPwd = memberService.pwdCheck(checkPwd); 
		
		if(checkResultPwd > 0) { // 일치하는 값
			return "비밀번호가 일치합니다.";
		}else {
			return "비밀번호가 일치하지 않습니다.";
		} 
	}
	
	@PostMapping("auth-mail")
	public void authMailService(String email, HttpServletRequest request) {
		String remoteAddr = request.getRemoteAddr();
	}
	
}
