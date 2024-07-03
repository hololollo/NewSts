package homeWork.com.kh.practice.student.model.vo;

public class Student {
	
    String name;  //이름
    String subject; //과목
	int score; // 점수
	
	//생성자
	public Student() {};
	public Student(String name, String subject, int score) {
		super();
		this.name = name;
		this.subject = subject;
		this.score = score;
	}
	
	//Getter, Setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	// == @Override toString
	public String inform() { 
		return "이름 : " + name + "/" + "과목 : " + subject + "/" + "성적 : " + score;
	}
	
		
	
		

}
