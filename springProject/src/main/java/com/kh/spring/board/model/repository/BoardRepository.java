package com.kh.spring.board.model.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BoardRepository {

	public int boardCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("boardMapper.boardCount");
	}
		
}
