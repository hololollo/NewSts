package com.kh.spring.member.model.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;


// Repository : 저장소
// 영속석 작업 : DB CRUD 작업! => 즉, sql문 실행하고 결과 받아오는 것만!! 다른 작업이 들어가면 안된다.
// 즉, 하나의 메서드가 하나의 SQL문을 실행하도록 해야함.

@Repository
public class MemberRepository {
	
	public Member login(SqlSessionTemplate sqlSession, Member member) {
		return sqlSession.selectOne("memberMapper.login", member);
		
	}

	public int insert(SqlSessionTemplate sqlSession, Member member) {
		return sqlSession.insert("memberMapper.insert", member);
	}

	public int update(SqlSessionTemplate sqlSession, Member member) {
		return sqlSession.update("memberMapper.update", member);
	}
	
	public int delete(SqlSessionTemplate sqlSession, String userId) {
		return sqlSession.update("memberMapper.delete", userId);
	}

	public int idCheck(SqlSessionTemplate sqlSession, String checkId) {
		return sqlSession.selectOne("memberMapper.idCheck", checkId);
	}

	public int pwdCheck(SqlSessionTemplate sqlSession, String checkPwd) {
		return sqlSession.selectOne("memberMapper.pwdCheck", checkPwd);
	}
	
}
