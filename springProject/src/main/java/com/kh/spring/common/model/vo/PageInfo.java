package com.kh.spring.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor // 기본생성자보단 모든 것이 다 있어야 하므로.
@Builder
@ToString
public class PageInfo {
	//Controller에서 페이징 처리 필드를 담을 클래스
	int listCount; // 현재 일반 게시판의 게시글 총 개수 => BOARD테이블로부터 SELECT COUNT(*)활용해서 조회
	int currentPage; // 현재페이지(사용자가 요청한 페이지) => 앞에서 넘길것.
	int pageLimit; // 페이지 하단에 보여질 페이징바의 최대 개수 => 10개로 고정 
	int boardLimit; // 한 페이지에 보여질 게시글의 최대 개수 => 10개로 고정
	
	int maxPage; // 가장 마지막 페이지가 몇 번 페이지인지(총 페이지의 개수)
	int startPage; // 그 화면상 하단에 보여질 페이징바의 시작하는 페이지넘버
	int endPage; // 그 화면상 하단에 보여질 페이징바의 끝나는 페이지넘버
	
	
}
