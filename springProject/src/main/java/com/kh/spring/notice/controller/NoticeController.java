package com.kh.spring.notice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.notice.model.service.NoticeService;
import com.kh.spring.notice.model.vo.Message;
import com.kh.spring.notice.model.vo.Notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
	private final NoticeService noticeService;
	
	@GetMapping
	public ResponseEntity<Message> findAll() { // 응답하는 객체
		
		List<Notice> noticeList = noticeService.findAll();
		if(noticeList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
								 .body(Message.builder()
										 .msg("조회결과가 존재하지 않아요 ㅠㅠ")
										 .build());
		}
		
		Message responseMsg = Message.builder().data(noticeList).msg("조회 요청 성공~!").build();
		
		// log.info("조회된 공지사항 목록 : {}", noticesList);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMsg);
				
	}
	
	
	/*
	 * 전체조회(Get)
	 * 상세조회(Get)
	 * 작성(Post)
	 * 수정(Put)
	 * 삭제(Delte)
	 */
	
	@GetMapping("/{id}")
	public ResponseEntity<Message> findById(@PathVariable int id) {
		
		Notice notice = noticeService.findById(id);
		//조회결과가 없으면 null값이 반환
		if(notice == null) {
			return ResponseEntity.status(HttpStatus.OK)
					             .body(Message.builder()
								 .msg("조회결과가 존재하지 않습니다.")
								 .build());
		}
		Message responseMsg = Message.builder()
							 .msg("조회요청에 성공했습니다.")
							 .data(notice)
							 .build();
		return ResponseEntity.status(HttpStatus.OK).body(responseMsg);
	}
	
	@PostMapping
	   public ResponseEntity<Message> save(Notice notice) {
	      
	      log.info("이게 노티스..? {}", notice);
	      
	      
	      
	      if("".equals(notice.getNoticeTitle()) || "".equals(notice.getNoticeContent()) || "".equals(notice.getNoticeWriter())) {
	         return ResponseEntity.status(HttpStatus.OK).body(Message.builder()
	                                                 .msg("서비스 요청 실패")
	                                                 .data("필수 파라미터가 누락되었습니다.")
	                                                 .build());
	      }
	      int result = noticeService.save(notice);
	      
	      if(result == 0) {
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message.builder()
	                                                          .msg("추가실패..ㅠ")
	                                                          .build());
	      }
	      
	      Message responseMsg = Message.builder().data("공지사항 추가 성공~~!")
	                                     .msg("서비스 요청 성공~~!")
	                                      .build();
	      
	      return ResponseEntity.status(HttpStatus.OK).body(responseMsg);
	   }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Message> deleteById(@PathVariable int id) {
		int result = noticeService.delete(id);
		// 두명이서 보다가 한명이 이미 삭제를 누른상태에서 나머지한명은 아직 새로고침이 되지 않은상태에서 다시한번 삭제를 누르게되면 이미 없는것을 삭제하는거니까 0이 나올수도 있다.
		if(result == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(Message.builder()
					.msg("게시글이 존재하지 않음.").build());
			
		}
		Message responseMsg = Message.builder().data("삭제성공!").msg("서비스처리에 성공했어요~!").build();
		return ResponseEntity.status(HttpStatus.OK).body(responseMsg);
		//공공데이터 하면서..
	}
	// 공지사항 수정
	// get방식은 requestParam으로 받는다.(http의 헤더영역)
	// put은 바디영역이기 때문에 requestBody로
	
	@PutMapping
	public ResponseEntity<Message> update(@RequestBody Notice notice) {
		// log.info("노티스 잘 넘어가나? {}", notice);
		int result = noticeService.update(notice);
		
		if(result == 0) {
			// 응답객체의 서버응답상태가 괜찮으면 메세지를 표시하는데, 메세지는 공지사항 변경실패.
			return ResponseEntity.status(HttpStatus.OK).body(Message.builder().msg("공지사항 변경 실패").build());
		}
		
		Message responseMsg = Message.builder().data(result).msg("공지사항 변경 성공~!").build();
		
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMsg);
	}
}
