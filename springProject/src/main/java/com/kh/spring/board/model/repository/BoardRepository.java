package com.kh.spring.board.model.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;

@Repository
public class BoardRepository {

	public int boardCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("boardMapper.boardCount");
	}

	public List<Board> findAll(SqlSessionTemplate sqlSession, Map<String, Integer> map) {
		return sqlSession.selectList("boardMapper.findAll", map);
		//RowBounds객체를 넘겨야 할 경우
		// sqlSession.selectList("boardMapper.findAll", null, rowBounds);
		// selectList()의 오버로딩된 형태중 매개변수가 3개인 메소드로 ★반드시 무조건 이걸로★ 호출해야함.
		// 두번째 인자값으로 전달할 값이 없다면 null값을 넘기면 된다.!!
	}
	
	public int searchCount(SqlSessionTemplate sqlSession, Map<String, String> map) {
		return sqlSession.selectOne("boardMapper.searchCount", map);
	}

	public List<Board> findByConditionAndKeyword(SqlSessionTemplate sqlSession, 
													Map<String, String> map,
													RowBounds rowBounds) {
		return sqlSession.selectList("boardMapper.findByConditionAndKeyword", map, rowBounds);
	}

	public int insert(SqlSessionTemplate sqlSession, Board board) {
		return sqlSession.insert("boardMapper.insert", board);
	}

	public int increaseCount(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.update("boardMapper.increaseCount", boardNo);
	}

	public Board findById(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.selectOne("boardMapper.findById", boardNo); // 하나(selectOne)의 게시글 정보만 가져옴.
	}

	public int delete(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.update("boardMapper.delete", boardNo);
	}

	public int update(SqlSessionTemplate sqlSession, Board board) {
		return sqlSession.update("boardMapper.update", board);
	}
		
}
