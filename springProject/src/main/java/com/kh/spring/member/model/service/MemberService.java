package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {
	
	int returnNum();
	
	// 기획!
	
	// 로그인(SELECT)
	Member login(Member member);
	
	// 회원가입(INSERT)
	int insert(Member member);
		
	// 회원 정보 수정(UPDATE)
	int update(Member member);
	
	// 회원 탈퇴(DELETE)
	int delete(String userId);

	
	
	// 아이디 중복체크(SELECT) , 게시판까지 끝내고 다시 돌아와서 ajax로 구현 // NNNNY / NNNNN
	int idCheck(String checkId);
	// 메일인증
	
	int pwdCheck(String checkPwd);
}
