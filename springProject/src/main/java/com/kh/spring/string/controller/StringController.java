package com.kh.spring.string.controller;

public class StringController {
	
	// String 클래스 => 특징? 불변(변하지 않음을 의미).
	
	int num = 1; //(단순한 정수)
	boolean flag = true; //(논리)
	
	/*
	 * 논리 : 실체가 없는 것
	 * 물리 : 실체가 있는 것
	 * 
	 * 배열 : 논리적인 구조와 물리적인 구조가 동일함!
	 */
	
	int[] numArr = {1,2,3,4,5};
	int num1 = numArr[0]; // 인덱스 : 실체가 없으므로 논리적인 구조 
	int num2 = numArr[1];
	int num3 = numArr[2];
	
	// charArray
	//String str;

	
	/*
	 * String 클래스의 객체 생성 방법
	 * 
	 * 1. 대입연산자를 통해서 String리터럴을 대입하는 방법 ★
	 * 2. 생성자를 호출해서 String객체로 만들어주는 방법 X
	 * 
	 * Why? => 
	 * 
	 */
	
	//생성자를 호출해서 String 객체를 생성
	public void constructorString() {
	
		String str1 = new String("Hello");
		String str2 = new String("Hello");
		
		String[] strArr = {};
		
		
		
		//toString()
		System.out.println(str1.toString());
		System.out.println(str2);
		
		// System.out.println(strArr);
		
		// 1. String클래스의 toString의 경우
		// 실제 담겨있는 문자열 리터럴을 반환하게끔 오버라이딩
		
		//equals() => 주소값을 비교하는 것이 아닌 실제 문자열 리터럴값을 비교하도록 오버라이딩.
		System.out.println(str1.equals(str2));
		
		// hashCode() 
		// 16진수는 알아보기 힘드니까 => int형 10진수
		System.out.println(str1.hashCode());
		System.out.println(str2.hashCode());
		System.out.println("Hello".hashCode());
		//주소값을 해싱하는 것이 아니라 실제 담긴 문자열을 기반으로 해시코드값을 만들어서 반환.
		
		// 해시코드에 오버라이딩되어있는 값 비교가 불가능하다. 
		// => 식별 할 수 있는 값? System.indentityHashCode(참조형변수);
		System.out.println(System.identityHashCode(str1));
		System.out.println(System.identityHashCode(str2));
		// => 실제 str1과 str2는 주소값이 다르다.
		System.out.println(str1 == str2);
	}
	
	// 리터럴 대입방식
	public void assignToString() {
		String str1 = "Hello";
		String str2 = "Hello";
		
		//toString()
		System.out.println(str1);
		System.out.println(str2);
		
		//equals()
		System.out.println(str1.equals(str2));
		
		// hashCode()
		System.out.println(str1.hashCode());
		System.out.println(str2.hashCode());
		
		//System.identityHashCode()
		System.out.println(System.identityHashCode(str1));
		System.out.println(System.identityHashCode(str2));
		// => 오버라이딩된 값이 아니다.
		
		// 문자열의 내용을 비교하려면 equals 메서드를 사용해야 합니다: if(A.equals(B))
		// 문자열의 참조(메모리 주소)를 비교하려면 == 연산자를 사용해야 합니다: if(A == B)
		System.out.println(str1 == str2);
		// A == B => ex) A와 B가 같니?
		// A != B
		// A > B
		// A < B
		// .. 비교연산자는 의문형으로 작성 및 해석하기!
		
	}
	
	public void stringPool() {
		
		String str = "Hello";
		System.out.println(System.identityHashCode(str));
		//대입연산자를 이용해서 문자열 리터럴값을 대입시 
		// Heap영역중 StringPool영역에 올라감.
		// String 문자열인 Hello를 str이라는 변수에 넣는다.
		
		//String newStr = "Hello";
		//StringPool특징 : 동일한 내용의 문자열 리터럴이 존재할 수 없음.
		
		str = "Bye";
		System.out.println(System.identityHashCode(str));
		// String이 아니였으면 값이 변화함.
		// String은 주소값이 변화함. (불변의 특징.)
		// ex) Hello가 SpringPool에 aaa라는 주소값을 가지고 있었으면, Bye라는 bbb주소값이 추가되고, stack에서는 기존 aaa대신 bbb만 남게된다. 
		// 그러면 의문점! StringPool에 존재하는 aaa는 어떻게 되는가? => 메모리낭비. 개발자는 이러한 메모리를 반납하는 일까지 해야함.(자바는 가비지컬렉터가 일을 대신해줌.)
		// 정리
		// 즉, 연결이 끊긴 문자열은 가비지콜렉터가 정리해줌.
		// 객체는 불변.
		// 참조변수는 새로운 주소값을 참조.
	}
	
	
}
