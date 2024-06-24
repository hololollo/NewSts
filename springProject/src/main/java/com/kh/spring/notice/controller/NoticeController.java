package com.kh.spring.notice.controller;

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

import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.PageTemplate;
import com.kh.spring.notice.model.service.NoticeService;
import com.kh.spring.notice.model.vo.Notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j

public class NoticeController {
	
private final NoticeService noticeService;
	
	
	// localhost/spring/boardlist
	@GetMapping("noticelist")
	public String forwarding(@RequestParam(value="page", defaultValue="1") int page, Model model) {
		
		// return 전에 요청을 받은 시점에서 DB의 데이터를 가져와서 list로 반환해줌.

		int listCount; // 현재 일반 게시판의 게시글 총 개수 => BOARD테이블로부터 SELECT COUNT(*)활용해서 조회
		int currentPage; // 현재페이지(사용자가 요청한 페이지) => 앞에서 넘길것.
		int pageLimit; // 페이지 하단에 보여질 페이징바의 최대 개수 => 10개로 고정 
		int boardLimit; // 한 페이지에 보여질 게시글의 최대 개수 => 10개로 고정
		
		int maxPage; // 가장 마지막 페이지가 몇 번 페이지인지(총 페이지의 개수)
		int startPage; // 그 화면상 하단에 보여질 페이징바의 시작하는 페이지넘버
		int endPage; // 그 화면상 하단에 보여질 페이징바의 끝나는 페이지넘버
		
		// * listCount : 총 게시글의 수
		listCount = noticeService.noticeCount();
		
		
		// *currentPage : 현재 페이지(사용자가 요청한 페이지)
		currentPage = page;
		log.info("게시글의 총 개수 : {}, 현재 요청 페이지 : {}", listCount, currentPage);
		
		// * pageLimit : 페이징바의 최대개수
		pageLimit = 10;
		
		// * boardLimit : 한 페이지에 보여질 게시글의 최대 개수
		boardLimit = 10;
		
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
		

		startPage = ((currentPage-1) / (pageLimit * pageLimit)) + 1;
		
		// *endPage : 페이지 하단에 보여질 페이징바의 끝 수
		
		
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
								
		
		
		
		
		// 전체 목록조회
		Map<String,Integer> map = new HashMap();
		
		int startValue = (currentPage - 1) * boardLimit + 1;
		int endValue = startValue + boardLimit - 1;
		
		map.put("startValue", startValue);
		map.put("endValue", endValue);
		
		List<Notice> noticeList = noticeService.findAll(map);
		
		//log.info("조회된 게시글의 개수 : {}", noticeList.size());
		//log.info("--------------");
		//log.info("조회된 게시글 목록 : {}", noticeList);
		
		model.addAttribute("list", noticeList);
		model.addAttribute("pageInfo", pageInfo);

		return "notice/list";
	}
	
	//검색기능
	@GetMapping("Nsearch.do")
	public String search(String condition, @RequestParam(value="page", defaultValue = "1") int page, String keyword, Model model) {
		
		// log.info(" 검색 조건 : {}", condition);
		// log.info(" 검색 키워드 : {}", keyword);
		
		// 페이징 처리를 끝낸 후 검색결과를 들고가야함~!
		
		// 검색 경우의 수
		// 조건(condition) : "writer", "title", "content"
		// 사용자가 입력한 키워드(keyword) 뭐가 입력될지 모름. 담아서 간다. 
		
		// 클래스나 배열, 맵 가능.
		Map<String, String> map = new HashMap();
		map.put("condition", condition);
		map.put("keyword", keyword);
		// service로
		
		int searchCount = noticeService.searchCount(map);
		log.info("검색 조건에 부합하는 행의 수 : {}", searchCount);
		int currentPage = page;
		int pageLimit = 5;
		int boardLimit = 5;
		
		//pageTemplate클래스에서 가져와서(get) pageInfo에 담아줌
		PageInfo pageInfo = PageTemplate.getPageInfo(searchCount, currentPage, pageLimit, boardLimit);
		
		// 마이바티스에서 제공해주는 rowBounds라는 클래스를 제공
		
		// offset(몇번 건너뛰고 가져갈 것인지 엑셀에서의 offset을 생각x ex. offset 4 => 50개를 조회하고 앞에 40를 제외하고 나머지를 들고간다.)
		// limit(개수)
		RowBounds rowBounds = new RowBounds((currentPage - 1) * boardLimit, boardLimit);
		
		List<Notice> noticeList = noticeService.findByConditionAndKeyword(map, rowBounds);
		
		model.addAttribute("list", noticeList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("keyword", keyword);
		model.addAttribute("condition", condition);
		
		return "notice/list";
	}
	
	//글등록 넘어가기
	@GetMapping("noticeForm.do")
	public String noticeFormForwarding() {
		return "notice/insertForm";
	}
	//글등록
	@PostMapping("Ninsert.do")
	public String insert(Notice notice,
			HttpSession session,
			Model model) { 
		noticeService.insert(notice);
		return "redirect:/noticelist";
	}
	/*
		if(noticeService.insert(notice) > 0) {
			session.setAttribute("alertMsg", "게시글 작성 성공~!");
			return "redirect:/noticelist"; // 무조건 리다이렉트 해야함. 
		
		}else {
			model.addAttribute("errorMsg", "게시글 작성 실패!");
			return "common/errorPage";
		}
		*/
		
		
		 
}
