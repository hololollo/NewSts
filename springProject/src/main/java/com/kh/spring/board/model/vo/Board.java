package com.kh.spring.board.model.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter // 원래대로라면 X 지금은 dto, vo 같다고 생각하고 진행
@NoArgsConstructor
@ToString

public class Board {
	
	private int boardNo;
	private String boardTitle;
	private String boardWriter;
	private String boardContent;
	private String originName;
	private String changeName;
	private int count;
	private String createDate;
	private String status;
	private List<Reply> replyList;
	
	
	// 내가 쓰는 기술, sql테이블에 따라 모양이 달라짐.
	
}
