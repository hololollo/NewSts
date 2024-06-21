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
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
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
		 *   -> 나눗셈 결과에 소숫점을 붙여서 올림처리 할 경우 maxPage가 됨!!
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
		// 조건 : "writer", "title", "content"
		// 사용자가 입력한 키워드 뭐가 입력될지 모름. 담아서 간다. 
		
		// 클래스나 배열, 맵 가능.
		Map<String, String> map = new HashMap();
		map.put("condition", condition);
		map.put("keyword", keyword);
		// service로
		
		int searchCount = boardService.searchCount(map);
		log.info("검색 조건에 부합하는 행의 수 : {}", searchCount);
		int currentPage = page;
		int pageLimit = 3;
		int boardLimit = 3;
		
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
										.listCount(endPage)
										.currentPage(currentPage)
										.maxPage(maxPage)
										.boardLimit(boardLimit)
										.build();
		
		*/
		PageInfo pageInfo = PageTemplate.getPageInfo(searchCount, currentPage, pageLimit, boardLimit);
		
		// 마이바티스에서 제공해주는 rowBounds 사용
		
		// offset(몇번 건너뛰고 가져갈 것인지 엑셀에서의 offset을 생각x ex. offset 4 => 50개를 조회하고 앞에 40를 제외하고 나머지를 들고간다.)
		// limit(개수)
		RowBounds rowBounds = new RowBounds((currentPage - 1) * boardLimit, boardLimit);
		// Mybatis에서는 페이징 처리를 위해 RowBouns라는 클래스를 제공.
		
		/*
		 * boardLimit이 3일 경우
		 * 
		 * currentPage : 1 -> 1~3 ==> 0
		 * currentPage : 2 -> 4~6 ==> 3개를 건너뛰어야 한다.
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
			// 첨부파일이 존재하면.
			// 1. 업로드 완료
			// 2. Board객체에 originName + changeName에 담아줘야한다.
			
			board.setOriginName(originName);
			board.setChangeName(savePath + changeName);
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
		
		
		
		
		// return "redirect:/boardForm.do";
	}
}
