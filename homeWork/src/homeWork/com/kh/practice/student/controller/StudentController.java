package homeWork.com.kh.practice.student.controller;

import homeWork.com.kh.practice.student.model.vo.Student;

public class StudentController {
	
	public static final int CUT_LINE = 60;
	
	public Student[] sArr = new Student[5];// 인스턴스 변수. (클래스 내의 모든 메서드에서 접근 가능.)
	
	
	public StudentController() { // 5개 객체배열
		sArr[0] = new Student("김길동", "자바", 100);
		sArr[1] = new Student("박길동", "디비", 50);
		sArr[2] = new Student("이길동", "화면", 85);
		sArr[3] = new Student("정길동", "서버", 60);
		sArr[4] = new Student("홍길동", "자바", 20);
	};
	public Student[] printStudent() {
		return sArr;
	}
	public int sumScore() {
		int sum = 0;
		for(int i = 0; i < sArr.length; i++) {
			sum += sArr[i].getScore(); // 점수 가져와서 합치기
		}
		return sum; // 점수를 합한 값
	}
	public double[] avgScore() {
		double avg[] = new double[2]; // 0하고 1
		avg[0] = sumScore(); // sumScore의 리턴 값
		avg[1] = sumScore()/ 5.0; // 총합 / 5 (합의 평균) 또는 sArr.length
		return avg;
	}
	
}
