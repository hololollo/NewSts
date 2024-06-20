package com.kh.spring.board.model.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.repository.BoardRepository;
import com.kh.spring.board.model.vo.Board;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final SqlSessionTemplate sqlSession;
	
	@Override
	public int boardCount() {
		return boardRepository.boardCount(sqlSession);
	}

	@Override
	public List<Board> findAll() {
		return boardRepository.findAll(sqlSession);
	}

	@Override
	public int searchCount() {
		return 0;
	}

	@Override
	public List<Board> searchAll() {
		return null;
	}

	@Override
	public int insert(Board board) {
		return 0;
	}

	@Override
	public int increaseCount(int boardNo) {
		return 0;
	}

	@Override
	public Board findById(int BoardNo) {
		return null;
	}

	@Override
	public Board delete(int BoardNo) {
		return null;
	}

	@Override
	public Board update(int BoardNo) {
		return null;
	}

}
