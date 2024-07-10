package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.PageTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	
	
	private final BoardService boardService;
	
	
	// localhost/spring/boardlist
	@GetMapping("boardlist")
	public String forwarding(@RequestParam(value="page", defaultValue="1") int page, Model model) {
		
		// return 전에 요청을 받은 시점에서 DB의 데이터를 가져와서 list로 반환해줌.
		
		// -- 페이징 처리 -- 2가지 방법. 둘의 성능이 다르다.
		// RowBounds 안쓴거
		// RowBounds 쓴거
		
		// 필요한 변수들(7)
		// 최대한 풀어서 작성.
		int listCount; // 현재 일반 게시판의 게시글 총 개수 => BOARD테이블로부터 SELECT COUNT(*)활용해서 조회
		int currentPage; // 현재페이지(사용자가 요청한 페이지) => 앞에서 넘길것.
		int pageLimit; // 페이지 하단에 보여질 페이징바의 최대 개수 => 10개로 고정 
		int boardLimit; // 한 페이지에 보여질 게시글의 최대 개수 => 10개로 고정
		
		int maxPage; // 가장 마지막 페이지가 몇 번 페이지인지(총 페이지의 개수)
		int startPage; // 그 화면상 하단에 보여질 페이징바의 시작하는 페이지넘버
		int endPage; // 그 화면상 하단에 보여질 페이징바의 끝나는 페이지넘버
		
		// * listCount : 총 게시글의 수
		listCount = boardService.boardCount();
		
		
		// *currentPage : 현재 페이지(사용자가 요청한 페이지)
		currentPage = page;
		log.info("게시글의 총 개수 : {}, 현재 요청 페이지 : {}", listCount, currentPage);
		
		// * pageLimit : 페이징바의 최대개수
		pageLimit = 10;
		
		// * boardLimit : 한 페이지에 보여질 게시글의 최대 개수
		boardLimit = 10;
		
		// * maxPage : 가장 마지막 페이지가 몇 번 페이지인지(총 페이지 개수)
		/*
		 * listCount, boardLimit에 영향을 받음.
		 * 
		 * -공식 구하기
		 *  단, boardLimit이 10이라는 가정.
		 *  
		 *  총 개수(listCount)    게시글 개수(boardLimit)      maxPage(마지막페이지)
		 *  100         /        10                  ==    10.0 -> 10페이지
		 *  106         /        10                  ==    10.6 -> 11페이지
		 *  111         /        10                  ==    11.1 -> 12페이지
		 * 
		 *   -> 나눗셈 결과에 소숫점을 붙여서 올림(.ceil)처리 할 경우 maxPage가 됨!!
		 *   
		 *   코드를 작성하는 순서를 풀어보자면
		 *   1. listCount를 double로 변환
		 *   2. listCount / boardLimit
		 *   3. Math.ceil() => 결과를 올림처리
		 *   4. 결가값을 int로 형변환
		 */
		
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
		
		
		// Math math = new Math(); 
		
		
		// * startPage : 페이지 하단에 보여질 페이징바의 시작 수
		/*
		 * 
		 * 
		 * currentPage, pageLimit에 영향을 받음
		 * 
		 * - 공식
		 *     단, pageLimit이 10이라고 가정
		 *     
		 * - startPage : 1, 11, 212, 31 ,41 => n * 10 + 1
		 * 
		 * 
		 * 만약 pageLimit이 5다!
		 *  - startPage : 1, 6, 11, 16, 21 => n * 5 + 1
		 *  
		 *  즉, startPAge = n * pageLimit + 1
		 *  
		 *  currentPage    startPage
		 *  	1				1
		 *  	5				1
		 *  	9				1
		 *  	10				1
		 *  	13				11
		 *  	15				11	
		 *  	21				21
		 *  
		 *  => 1 ~ 10 : n * 10 + 1 ==> n == 0
		 *  => 11 ~ 20 : n * 10 + 1 ==> n == 1
		 *  => 21 ~ 30 : n * 10 + 1 ==> n == 2
		 *  
		 *  -----
		 *  
		 *  1 ~ 10 / 10 => 0 ~ 1
		 *  11 ~ 20 / 10 => 1 ~ 2
		 *  21 ~ 30 / 10 => 2 ~ 3
		 *  
		 *  
		 *  1 ~ 9 / 10 => 0
		 *  10 ~ 19 / 10 => 1
		 *  20 ~ 29 / 10 => 2
		 *  
		 *  n = (currentPage -1) / pageLimit;
		 *  n = 현재페이지(1) / 10 => 0;
		 */
		
		startPage = ((currentPage-1) / (pageLimit * pageLimit)) + 1;
		
		// *endPage : 페이지 하단에 보여질 페이징바의 끝 수
		
		/*
		 * startPage, pageLimit에 영향을 받음(단, maxPage도 마지막 페이징바에 대해 영향을 끼침ex. 10개씩인데 5밖엔 안남은 상황 등)
		 * 
		 * -공식
		 * 단, pageLimit이 10이라고 가정
		 * 
		 * startPage : 1 => endPage : 10
		 * startPage : 11 => endPage : 20
		 * startPage : 21 => endPage : 30
		 * 
		 * => endPage = startPage + pageLimit - 1;
		 *
		 */
		
		endPage = startPage + pageLimit - 1;
		// startPage가 1이라서 endPage가 10이 들어갔는데 maxPage가 2인경우
		// endPage를 maxPage값을 변경 
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		
		// 7개의 객체를 계속 들고다닐수가 없으니까 
		// 클래스 / int[] / list / Map 중 택 1하여 값을 담고 넘긴다.
		
		// PageInfo pageInfo = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		// 클래스에 넣는다 -> 필드 선언 순서를 모르면 와장창.
		
		
		// Builder 애너테이션(매개변수가 사용될때 따라오는 애너테이션)을 쓰면
		PageInfo pageInfo = PageInfo.builder()
									.listCount(listCount)
									.currentPage(currentPage)
									.pageLimit(pageLimit)
									.boardLimit(boardLimit)
									.maxPage(maxPage)
									.startPage(startPage)
									.endPage(endPage)
									.build(); // 각각의 필드에 객체를 담아서 빌드 (순서에 상관이 없다는 특징이 있다.)
		
		/*
		 * boardLimit 이 10이라는 가정하에
		 * currentPage == 1 => 시작값은 1, 끝값은 10
		 * currentPage == 2 => 시작값은 11, 끝값은 20
		 * currentPage == 3 => 시작값은 21, 끝값은 30
		 * 
		 * 시작값 = (currentPage -1) * boardLimit + 1;
		 * 끝 값 = 시작값 + boardLimit - 1; 
		 * 
		 */
								
		
		Map<String,Integer> map = new HashMap();
		
		int startValue = (currentPage - 1) * boardLimit + 1;
		int endValue = startValue + boardLimit - 1;
		
		map.put("startValue", startValue);
		map.put("endValue", endValue);
		
		List<Board> boardList = boardService.findAll(map);
		
		log.info("조회된 게시글의 개수 : {}", boardList.size());
		log.info("--------------");
		log.info("조회된 게시글 목록 : {}", boardList);
		
		model.addAttribute("list", boardList);
		model.addAttribute("pageInfo", pageInfo);
		
		
		return "board/list";
	}
	
	@GetMapping("search.do")
	public String search(String condition, @RequestParam(value="page", defaultValue = "1") int page, String keyword, Model model) {
		
		log.info(" 검색 조건 : {}", condition);
		log.info(" 검색 키워드 : {}", keyword);
		
		// 사용자가 선택한 조건과 입력한 키워드를 가지고
		// 페이징 처리를 끝낸 후 검색결과를 들고가야함~!
		
		// 검색 경우의 수
		// 조건(condition) : "writer", "title", "content"
		// 사용자가 입력한 키워드(keyword) 뭐가 입력될지 모름. 담아서 간다. 
		
		// 클래스나 배열, 맵 가능.
		Map<String, String> map = new HashMap();
		map.put("condition", condition);
		map.put("keyword", keyword);
		// service로
		
		int searchCount = boardService.searchCount(map);
		log.info("검색 조건에 부합하는 행의 수 : {}", searchCount);
		int currentPage = page;
		int pageLimit = 5;
		int boardLimit = 5;
		
		/* PageTemplate클래스에 담아두었음.
		int maxPage = (int)Math.ceil((double) searchCount / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		PageInfo pi = PageInfo.builder().pageLimit(pageLimit)
										.startPage(startPage)
										.endPage(endPage)
										.listCount(listCount)
										.currentPage(currentPage)
										.maxPage(maxPage)
										.boardLimit(boardLimit)
										.build();
		
		*/
		//pageTemplate클래스에서 가져와서(get) pageInfo에 담아줌
		PageInfo pageInfo = PageTemplate.getPageInfo(searchCount, currentPage, pageLimit, boardLimit);
		
		// 마이바티스에서 제공해주는 rowBounds라는 클래스를 제공
		
		// offset(몇번 건너뛰고 가져갈 것인지 엑셀에서의 offset을 생각x ex. offset 4 => 50개를 조회하고 앞에 40를 제외하고 나머지를 들고간다.)
		// limit(개수)
		RowBounds rowBounds = new RowBounds((currentPage - 1) * boardLimit, boardLimit);
		/*
		 * boardLimit이 3일 경우
		 * 
		 * currentPage : 1 -> 1~3 ==> offset은 0
		 * currentPage : 2 -> 4~6 ==> offset은 3(개를 건너뛰어야 한다.)
		 * =>
		 * (currentPage() - 1) * boardLimit()
		 * 
		 */
		
		List<Board> boardList = boardService.findByConditionAndKeyword(map, rowBounds);
		
		model.addAttribute("list", boardList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("keyword", keyword);
		model.addAttribute("condition", condition);
		
		return "board/list";
	}
	
	@GetMapping("boardForm.do")
	public String boardFormForwarding() {
		return "board/insertForm";
	}
	@PostMapping("insert.do")
	public String insert(Board board,
			HttpSession session,
			Model model,
			MultipartFile upfile) { // 여러개 첨부시 MultipartFile[] 배열로 선언하면 한번에 들어옴
					
		// log.info("게시글정보 : {}", board);
		// log.info("파일의 정보 : {}", upfile);
		
		// 첨부파일 존재o / 존재 x
		// Multipart객체는 무조건 생성된다!!
		// => fileName필드에 원본명이 존재하는가 없는가 체크해야함!!
		
		// 전달된 파일이 존재할 경우 => 파일 업로드!!
		
		if(!upfile.getOriginalFilename().equals("")) {
			// 파일명이 같으면 안된다. => 덮어씌워짐.
			// 파일명을 바꿔야 함. ex. kh_년월일시분초_랜덤한값.확장자
			/*
			String originName = upfile.getOriginalFilename();
			
			String ext = originName.substring(originName.lastIndexOf("."));
			// "abc.ddd.txt" => 뒤에 . 기준
			
			int num = (int)(Math.random() * 100) + 1; // 값의 범위를 곱한다. 그런뒤에 시작값을 더해준다.
			// Math.random() : 0.0 ~ 0.9999999....
			
			// 시간메서드
			// log.info("currentTime : {}", new Date());
			
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/"); // /가 없으면 파일이 들어가지 않는다.
			// 새로운 파일 명
			String changeName = "KH_" + currentTime + "_" + num + ext;
			
			try {
				upfile.transferTo(new File(savePath + changeName)); // 파일경로 + 파일이름
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
			saveFile(upfile, session); // 460행 메서드로 따로 빼놓음
			// 첨부파일이 존재하면.
			// 1. 업로드 완료
			// 2. Board객체에 originName + changeName에 담아줘야한다.
			
			board.setOriginName(upfile.getOriginalFilename());
			board.setChangeName(saveFile(upfile, session)); // 480행쪽에 메서드를 만들어 놨음.
		}
		
		//첨부파일이 존재하지 않을 경우 board : 제목 / 내용 / 작성자
		// 첨부파일이 존재할 경우 board : 제목 / 내용 / 작성자 / 원봉명 / 변경된 경로와 이름
		if(boardService.insert(board) > 0) {
			session.setAttribute("alertMsg", "게시글 작성 성공~!");
			return "redirect:/boardlist"; // 무조건 리다이렉트 해야함. 
		
		}else {
			model.addAttribute("errorMsg", "게시글 작성 실패!");
			return "common/errorPage";
		}
		
	}
	
	// localhost/spring/board-detail?boardNo(key)=???(value)
	@GetMapping("board-detail")
	public ModelAndView findById(int boardNo, ModelAndView mv) {
		// public ModelAndView findByBoardNo(HttpServletRequest request, @RequestParam(value="boardNo") int boardNo) {
		// 리퀘스트서블릿 - 디스패처서블릿에 담아서 보내줌. 리퀘스트파람이란? HTTP 요청 파라미터를 컨트롤러 메서드의 파라미터에 바인딩하는 데 사용됩니다. 주로 GET 요청의 쿼리 스트링이나 POST 요청의 폼 데이터에서 값을 추출할 때 사용됩니다.
		// int abc = Integer.parseInt("123"); // '파싱한다'라고 표현함. 형변환과는 다른개념임. 기본형->참조형(o). 참조형->기본형(x)
		
		// 1. 데이터 가공 --> 매개변수(파라미터) 한개라서 할거 없음
		// 2. 서비스호출
		if(boardService.increaseCount(boardNo) > 0) {
		mv.addObject("board", boardService.findById(boardNo))
		//3. 응답화면 지정
		.setViewName("board/boardDetail");	
		
		} else {
			mv.addObject("errorMsg", "게시글 상세조회에 실패했습니다.").setViewName("common/errorPage");
			
		}
		//get방식이기때문에 DML(CRUD)이 성공할 수도 있고 실패할 수도 있음. 카운트가 증가되면 상제조회가 되도록.
		
		
		// 실패여부 확인
		
		// 3. 응답화면 지정
		
		return mv;
	}
	
	/*
	 * deleteById : Client(게시글 작성자)에게 정수형의 boardNo(BOARD테이블의 pk)를 전달받아서 BOARD테이블의 존재하는 STATUS컬럼의 값을 'N'으로 갱신.
	 * 
	 * @param boardNo : 각 행을 식별하기 위한 PK
	 * @param filePath : 요청 처리 성공 시 첨부파일을 제거하기 위해 파일이 저장되어 있는 경로 및 파일명
	 * 
	 * @return : 반환된 View의 논리적인 경로
	 *
	 * 생각해보니 게시글에 파일이 달릴 수 있네?
	 * 
	 * 근데 처음에 생각안하고 작업했다가 다시 view단 가서 파일정보를 끌어왔습니다.
	 * 
	 * 해봤더니 뭐시기 Exception이 생겼다. 그래서 찾아보니까 어떤거란다.
	 * 
	 * 요롷케 조롷케 해결했다.
	 */
	
	@PostMapping("boardDelete.do")
	public String deleteById(int boardNo, String filePath, HttpSession session, Model model) {
		if(boardService.delete(boardNo) > 0) {
			if(filePath != null && !"".equals(filePath)) { // 파일이 저장되어 있는 경로 및 파일명이 빈문자열이 아닌 
		// 만약 요청하는 view에서 filePath 오타가 발생했다면, null값이 발생하여 nullpointerException이 발생할 수도 있으니 주어를 변경해서 막아준다.
			new File(session.getServletContext().getRealPath(filePath)).delete();
			}
			session.setAttribute("alertMsg", "게시글 삭제 성공");
			return "redirect:boardlist";
		}else {
			model.addAttribute("errorMsg", "게시글 삭제 실패");
			return "common/errorPage";
		}

	}
	
	// 수정하기
	@PostMapping("boardUpdateForm.do")
	public ModelAndView updateForm(ModelAndView mv, int boardNo) {
		mv.addObject("board", boardService.findById(boardNo)).setViewName("board/boardUpdate");
		// boardNo을 가져오는 findById 를 재활용
		return mv;
	}
	
	@PostMapping("board-update.do")
	public String update(Board board, HttpSession session, MultipartFile reUpFile) {
		// DB가서 BOARD테이블 UPDATE
		
		//Board board
		
		/*
		 * -> boardTitle, boardContent
		 * + reUpfile
		 * 
		 * 1. 기존 첨부파일X, 새로운 첨부파일x => 그렇구나~ 더 할게 없음.
		 * 2. 기존 첨부파일O, 새로운 첨부파일x => ORIGIN : 기존 첨부파일 이름, CHANGE : 기존 첨부파일 경로 (기존 파일이 날라갈 수 있음.)
		 * 3. 기존 첨부파일X, 새로운 첨부파일O => ORIGIN : 새로운 첨부파일 이름, CHANGE : 새로운 첨부파일 경로
		 * 4. 기존 첨부파일O, 새로운 첨부파일O => ORIGIN : 새로운 첨부파일 이름, CHANGE : 새로운 첨부파일 경로 
		 * 
		 */
		// 새로운 첨부파일이 존재하는가? 를 체크해야한다.
		if(!reUpFile.getOriginalFilename().equals("")) { // 빈문자열과 같지 않으면 (새로운 첨부파일이 있다.)
			board.setOriginName(reUpFile.getOriginalFilename()); // board에 담는다.
			board.setChangeName(saveFile(reUpFile, session));
		}
		// 담은 값을 board까지 전달~
		if(boardService.update(board)>0) {
			session.setAttribute("alertMsg", "수정완료");
			return "redirect:board-detail?boardNo=" + board.getBoardNo();
		}else {
			session.setAttribute("errorMsg", "정보수정 실패");
			return "common/errorPage";
		}
	}
	public String saveFile(MultipartFile upfile, HttpSession session) {
		
		String originName = upfile.getOriginalFilename();
		
		String ext = originName.substring(originName.lastIndexOf("."));
		// "abc.ddd.txt" => 뒤에 . 기준
		
		int num = (int)(Math.random() * 100) + 1; // 값의 범위를 곱한다. 그런뒤에 시작값을 더해준다.
		// Math.random() : 0.0 ~ 0.9999999....
		
		// 시간메서드
		// log.info("currentTime : {}", new Date());
		
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/"); // /가 없으면 파일이 들어가지 않는다.
		// 새로운 파일 명
		String changeName = "KH_" + currentTime + "_" + num + ext;
		
		try {
			upfile.transferTo(new File(savePath + changeName)); // 파일경로 + 파일이름
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "resources/uploadFiles/" + changeName;
	}
	
	@GetMapping("image-board")
	public String images(Model model) {
		
		// List<Board> images = boardService.selectImages();
		model.addAttribute("board", boardService.selectImages());
		return "board/imageList";
	}
	
	// select면 get
	@ResponseBody
	@GetMapping(value="reply", produces="application/json; charset=UTF-8")
	public String selectReply(int boardNo) {
		return new Gson().toJson(boardService.selectReply(boardNo));	
	}
	// insert면 post
	@ResponseBody
	@PostMapping("reply")
	public String saveReply(Reply reply) {
		return boardService.insertReply(reply) > 0 ? "success" : "fail";
	}
	@ResponseBody
	@GetMapping("board-reply")
	public Board boardAndReply(int boardNo) {
		
		return boardService.boardAndReply(boardNo);
	}
	@GetMapping("var")
	public String varForward() {
		
		return "common/variable";
	}
}
