package com.kh.spring.hash.model.run;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kh.spring.hash.model.vo.Student;

public class Run {
	public static void main(String[] args) {
		
		//HashSet : (set)중복허용 X, 순서유지X, 값만 저장
		// Value값만 저장. index가 존재하지 않는다. => 중복허용X, 순서보장x(SET의 특징)
		
		//문자열만 담을 수 있는 HashSet
		// <> : 제네릭(개발자의 편의를 위해 존재.) 아래는 스트링 제네릭이라고 부름.
		
		List<String> list = new ArrayList<>(); 
		List l = new ArrayList();
		
		/*
		 * 제네릭 : 개발자의 편의를 위해서 존재.
		 * 쓰는 이유
		 * 1. 실수방지용! 혹여나 의도하지 않는 타입의 값이 저장소에 들어가지 않도록 하기 위함.
		 * 2. 개발의 편의성을 위해서 ! -> 강제형변환을 할 필요가 없음. 
		 *  
		 *  
		 */
		
		l.add("Hello");
		
		// charAt(0);
		System.out.println(((String)l.get(0)).charAt(0)); 
		// => 리스트가 모든 타입을 받으려면은 오브젝트 클래스를 사용해야함. 그런데 오브젝트클래스는 부모타입을 상속받는것인데, 오브젝트클래스에는 charAt이라는 메서드는 없음.
		// 따라서 String으로 형변환이 필요.
		// 또는 제네릭을 사용하여 미연에 방지.
		
		list.add("Hello");
		System.out.println(list.get(0).charAt(0));
		
		
		HashSet<String> set = new HashSet();
		// set : 참조자료 변수
		// System.out.println(set.toString());
		
		
		// 요소 추가
		set.add("안녕하세요");
		set.add("안녕히 가세요");
		set.add("반갑습니다");
		set.add("안녕하세요");
		set.add(new String("안녕하세요"));
		
		System.out.println(set);
		//중복저장X, 저장 순서 보장x. => SET의 특징.
		
		//그럼 HashSet의 특징은 뭔데? => 중복저장O (일반set은 중복x)
		// 요소가 새롭게 추가 될때마다 equals()와 hashCode()로 비교 후
		// 둘 다 결과가 true일 경우 동일 객체 판단.
		
		//equals() : 현재 객체의 주소값을 비교해서 결과를 반환 : boolean
		//hashcode() : 현재 객체의 주소값을 해싱 알고리즘을 돌려서 10진수로 반환 : int형!!!
		
		
		
		// student 객체만 담을 수 있는 HashSet 생성
		Set<Student> students = new HashSet();
		
		students.add(new Student("ddd", 10, 50));
		students.add(new Student("ggg", 15, 100));
		students.add(new Student("hhh", 60, 80));
		
		System.out.println(students);
		// 주소값? nono. 1차적으론 student클래스의 toString()값이 없으니 
		// 부모클래스인 object클래스의 toString()값이 나온 거.
		
		
		
		// 
	}
}
