package Thinking;

import java.util.ArrayList;
import java.util.List;

public class ClassRoom {
    private String no;//°à¼¶±àºÅ
    private List<Student> stuList;
    private int totalScore;
    public ClassRoom() {
	stuList=new ArrayList<Student>();
    }
    public ClassRoom(String no) {
	this();
	this.no = no;
    }
    public String getNo() {
        return no;
    }
    public void setNo(String no) {
        this.no = no;
    }
    public List<Student> getStuList() {
        return stuList;
    }
    public int getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    
}
