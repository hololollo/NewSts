package com.kh.spring.notice.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.notice.model.repository.NoticeRepository;
import com.kh.spring.notice.model.vo.Notice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{
	private final SqlSessionTemplate sqlSession;
	private final NoticeRepository noticeRepository;
	
	@Override
	public int noticeCount() {
		return noticeRepository.noticeCount(sqlSession);
	}

	@Override
	public List<Notice> findAll(Map<String, Integer> map) {
		return noticeRepository.findAll(sqlSession, map);
	}

	@Override
	public int searchCount(Map<String, String> map) {
		return noticeRepository.searchCount(sqlSession, map);
	}

	@Override
	public List<Notice> findByConditionAndKeyword(Map<String, String> map, RowBounds rowBounds) {
		return noticeRepository.findByConditionAndKeyword(sqlSession, map, rowBounds);
	}

	
	@Override
	public int insert(Notice notice) {
		return noticeRepository.insert(sqlSession, notice);
	}

	//상세보기
	@Override
	public Notice noticeFindById(int noticeNo) {
		return noticeRepository.noticeFindById(sqlSession, noticeNo);
	}
	//게시글 삭제
	@Override
	public int noticeDeleteById(int noticeNo) {
		return noticeRepository.noticeDeleteById(sqlSession, noticeNo);
	}

	@Override
	public int noticeUpdate(Notice notice) {
		return noticeRepository.noticeUpdate(sqlSession, notice);
	}
	
	
	
	
}
