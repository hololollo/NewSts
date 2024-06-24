package com.kh.spring.notice.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter // 원래대로라면 X 지금은 dto, vo 같다고 생각하고 진행
@NoArgsConstructor
@ToString
public class Notice {
	private int noticeNo;
	private String noticeTitle;
	private String noticeWriter;
	private String noticeContent;
	private String createDate;
	
	//board와 차이점 : 조회수(count), 파일업로드용 오리진네임 없음
}
