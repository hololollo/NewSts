package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.repository.BoardRepository;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final SqlSessionTemplate sqlSession;
	private final BoardRepository boardRepository;

	@Override
	public int boardCount() {
		return boardRepository.boardCount(sqlSession);
	}

	@Override
	public List<Board> findAll(Map<String,Integer> map) {
		return boardRepository.findAll(sqlSession, map);
	}

	@Override
	public int searchCount(Map<String, String>map) {
		return boardRepository.searchCount(sqlSession, map);
	}
	// 숫자 세오기.

	@Override
	public List<Board> findByConditionAndKeyword(Map<String, String> map, RowBounds rowBounds) {
		return boardRepository.findByConditionAndKeyword(sqlSession, map, rowBounds);
	}

	@Override
	public int insert(Board board) {
		return boardRepository.insert(sqlSession, board);
	}

	@Override
	public int increaseCount(int boardNo) {
		return boardRepository.increaseCount(sqlSession, boardNo);
	}
	// 조회수증가가 성공한다면 상세보기를 할 수 있게
	@Override
	public Board findById(int boardNo) {
		return boardRepository.findById(sqlSession, boardNo);
	}

	@Override
	public int delete(int boardNo) {
		return boardRepository.delete(sqlSession, boardNo);
	}

	@Override
	public int update(Board board) {
		return boardRepository.update(sqlSession, board);
	}

	@Override
	public List<Board> selectImages() {
		return boardRepository.selectImages(sqlSession);
	}

	@Override
	public List<Reply> selectReply(int boardNo) {
		return boardRepository.selectReply(sqlSession, boardNo);
	}

	@Override
	public int insertReply(Reply reply) {
		return boardRepository.insertReply(sqlSession, reply);
	}

	@Override
	public Board boardAndReply(int boardNo) {
		return boardRepository.boardAndReply(sqlSession, boardNo);
	}

	@Override
	public List<Board> findTopFiveBoard() {
		return boardRepository.findTopFiveBoard(sqlSession);
	}
	
}
