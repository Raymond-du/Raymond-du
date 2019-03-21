package Thinking;
/**
 * 利用学生信息进行分拣思想
 * @author 26368
 *
 */

public class Student {
    private String name;
    private String no;//班级
    private int Score;//成绩
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNo() {
        return no;
    }
    public void setNo(String no) {
        this.no = no;
    }
    public int getScore() {
        return Score;
    }
    public void setScore(int score) {
        Score = score;
    }
    public Student(String name, String no, int score) {
	super();
	this.name = name;
	this.no = no;
	Score = score;
    }
    
}
