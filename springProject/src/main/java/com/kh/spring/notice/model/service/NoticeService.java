package com.kh.spring.notice.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.notice.model.vo.Notice;

public interface NoticeService {
	//어떤 기능을 구현할 건지.
	
	// 게시글 전체 조회 + 페이징 처리
	// 페이징 처리 : 현재 Notice테이블의 총 행의 개수를 알아야 함.
	int noticeCount();
	
	//그룹함수 5총사
	// SUM()
	// AVERAGE()
	// MIN()
	// MAX()
	// COUNT()
	
	// 게시글 목록 조회
	List<Notice> findAll(Map<String,Integer> map);
	
	// 게시글 검색 기능(게시물 개수)
	int searchCount(Map<String, String> map);
	
	// 검색 목록 조회
	List<Notice> findByConditionAndKeyword(Map<String, String> map, RowBounds rowBounds);
	
	// 게시글 작성
	int insert(Notice notice);
	
	// 상세조회
	Notice noticeFindById(int noticeNo);
	// 게시글 삭제
	int noticeDeleteById(int noticeNo);
	// 게시글 수정
	int noticeUpdate(Notice notice);
}
