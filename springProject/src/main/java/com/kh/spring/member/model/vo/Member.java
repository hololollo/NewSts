package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * @NoArgsConstructor
 * @AllArgsConstructor
 * @Builder
 * @Getter
 * @Setter -> dto는 맞는데 vo는 아니다.
 * @ToString
 * 
 * @Data -> 사용X
 * 
 * Lombok사용시 주의사항! 
 * 멤버변수 필드명을 지울 때 
 * - 시작글자가 외자인 필드명은 X
 * ex. productName --> 올바른 표기법. 롬복 사용시 getProductName() 
 * ex. pName --> 병신짓. 롬복 사용시 getPName() 카멜케이스표기가 안된다.
 * ex. EL표기법을 이용할 때 내부적으로 getter사용방법!
 * ${Pname} == .getpName() => 즉, 매치가 안되기때문에 주의해야한다. 근데 이렇게 배웠었다..
 * 
 * *** 따라서 필드명 작성 시 최소 소문자 두 글자 이상으로 시작해야 한다. 
 * - 
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Member {
	
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
}
