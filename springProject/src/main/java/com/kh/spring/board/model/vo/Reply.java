package com.kh.spring.board.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reply {
	private int replyNo;
	private String replyContent;
	private String replyWriter;
	private int refBoardNo;
	private String createDate;
	private String status; // 혹여나 나중에 댓글 수정이나 삭제를 만들 수도 있으니까.
}
