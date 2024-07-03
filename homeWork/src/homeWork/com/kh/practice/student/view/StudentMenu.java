package homeWork.com.kh.practice.student.view;

import homeWork.com.kh.practice.student.controller.StudentController;
import homeWork.com.kh.practice.student.model.vo.Student;

public class StudentMenu {
	// 멤버 필드
	public StudentController a1 = new StudentController();
	
	public StudentMenu() {
		System.out.println("=======학생 정보 출력 =======");
		// StudentController에 printStudent()의 반환 값을 통해 학생 정보 출력
		Student[] students = a1.printStudent();
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].inform()); // 배열의 길이만큼 VO의 값을 가져와서 출력될 수 있게
        }
		
		System.out.println("=======학생 성적 출력========");
		// StudentController에 avgScore()를 통해 점수 합계와 평균 출력
		
		System.out.println("학생 점수 합계 : " + a1.sumScore()); // == acgScore()[0]
		System.out.println("학생 점수 평균 : " + a1.avgScore()[1]);
		
		
		System.out.println("========== 성적 결과 출력 ==========");
		// 학생의 점수가 CUT_LINE 미만이면 재시험 대상, 이상이면 통과 출력
		for (int i = 0; i < students.length; i++) {
			
			if (students[i].getScore() < StudentController.CUT_LINE) {	// 만약 a1으로 사용할 경우 controller단에서 정적필드 static을 삭제
				System.out.println(students[i].getName() + "학생은" + "재시험 대상 입니다.");
		
			} else {		
				System.out.println(students[i].getName() + "학생은" + "통과입니다.");
			}
		}
	}
}
