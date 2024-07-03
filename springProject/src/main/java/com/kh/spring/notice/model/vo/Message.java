package com.kh.spring.notice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Message {
	
	private String message;
	private Object data; // 여기저기서 쓸 수 있는 거
}
