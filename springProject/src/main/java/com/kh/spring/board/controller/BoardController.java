package com.kh.spring.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.common.model.vo.PageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping("boardlist")
	public String forwarding(@RequestParam(value="page", defaultValue="1") int page) {
		
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
									
		return "board/list";
	}
}
