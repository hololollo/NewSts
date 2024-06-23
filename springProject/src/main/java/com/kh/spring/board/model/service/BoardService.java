package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.vo.Board;

public interface BoardService {
	//어떤 기능을 구현할 건지.
	
	// 게시글 전체 조회 + 페이징 처리
	// 페이징 처리 : 현재 Board테이블의 총 행의 개수를 알아야 함.
	int boardCount();
	
	//그룹함수 5총사
	// SUM()
	// AVERAGE()
	// MIN()
	// MAX()
	// COUNT()
	
	// 게시글 목록 조회
	List<Board> findAll(Map<String,Integer> map);
	
	// 게시글 검색 기능(게시물 개수)
	int searchCount(Map<String, String> map);
	
	// 검색 목록 조회
	List<Board> findByConditionAndKeyword(Map<String, String> map, RowBounds rowBounds);
	
	// 게시글 작성
	int insert(Board board);
	
	//게시글 상세보기(조회수 증가)
	int increaseCount(int boardNo);
	
	// 상세조회
	Board findById(int BoardNo);
	
	
	// 게시글 삭제하기
	int delete(int BoardNo);
	
	
	// 게시글 수정하기
	int update(Board board);
	
	
	// ---- 댓글 관련 ---- (AJAX배우고)
	
	// 1. AJAX를 활용한 댓글 목록 조회 기능을 먼저 만들어보고 -> 2. Mybatis기술을 이용한 댓글 조회도 해볼거임.
	
	// 댓글 작성하기
	
	// ---- Top-N ----
}
