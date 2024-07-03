package com.kh.spring.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// 이 컨트롤러는 /boards로 시작하는 요청이 들어오면 처리를 해줄 컨트롤러
@Slf4j
@RestController // 자동 ResponseBody 역할과 controller역할
@RequiredArgsConstructor
@RequestMapping(value="boards", produces="application/json; charset=UTF-8")
public class BoardsController {
		
	/*
	 * url Mapping값은 notice로 통일
	 * => Rest방식.
	 * c: insert => @PostMapping
	 * r: select => @GetMappng
	 * u: update => @PutMapping
	 * d: delete => @DeleteMapping
	 * 
	 */
	private final BoardService boardService; 
	
	@GetMapping
	private List<Board> findTopFiveBoard() {
		return boardService.findTopFiveBoard();
	}
	@GetMapping("/{boardNo}") // 번호를 가져올 때 @PathVariable
	public Board findByBoardNo(@PathVariable int boardNo) {
		//log.info("넘어온 PK : {}", boardNo);
		return boardService.findById(boardNo);
	}
	
}
