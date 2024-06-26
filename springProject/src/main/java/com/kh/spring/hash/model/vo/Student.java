package com.kh.spring.hash.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Data를 쓰면 setter도 포함되기때문에 Data는 권장사항이 아니다.
// constructor를 쓰면 반드시 builder가 짝꿍으로 와야한다....
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
	public String name;
	public int age;
	public int score;
	
	// hashCode()
	@Override
	public int hashCode() {
		
		// new Student("홍길동", 15, 100);
		// 홍길동 15100
		
		return (name + age + score).hashCode();
	}
	
	// equals()
	@Override
	public boolean equals(Object obj) {
		Student other = (Student)obj;
		// 내가 가진 name필드와
		// 매개변수로 전달받은 Student객체의 name필드값을 비교!
		
		//이름, 나이, 점수
		// 셋 중 하나라도 다르면 false값 반환
		if(!other.name.equals(name)|| other.age != this.age || other.score != this.score) {
			return false;
		}
		
		
		return true;
	}
}
