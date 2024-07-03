package com.kh.spring.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.notice.model.vo.Notice;

@Mapper // 마이바티스가 기존 repository에서 하는 작업들을 대신 해줌.(sqlSession을 알아서 넘겨줌.) => Mybatis 3버전
public interface NoticeMapper {
	List<Notice> findAll();
	Notice findById(int noticeNo);
	int save(Notice notice);
	int update(Notice notice);
	int delete(int noticeNo);
}
