package Thinking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 注意这里的思想是吧  学生分别添加到各自的班级中
 * 使用map将键(班级编号)值(班级类)配对;
 * @author 26368
 *
 */

public class text {
    public static void main(String[] args) {
	List<Student>stuList=new ArrayList<Student>();
	exam(stuList);
	Map<String, ClassRoom> rooms=new HashMap<String, ClassRoom>();
	statistic(rooms, stuList);
	print(rooms);
    }
    
    public static void print(Map<String, ClassRoom> rooms) {
	Set<Entry<String, ClassRoom>> s=rooms.entrySet();
	Iterator<Entry<String, ClassRoom>> it=s.iterator();
	while(it.hasNext()) {
	    Entry<String, ClassRoom> entry= it.next();
	    System.out.println("班级编号"+entry.getKey()+"总分"+entry.getValue().getTotalScore()+"学生人数"+entry.getValue().getStuList().size());
	}
    }
    
    public static void statistic(Map<String, ClassRoom> rooms,List<Student> stuList) {
	for(Student s:stuList) {//注意这里的分拣思想
	
	    ClassRoom room=null;	    
	    if(null==(room=rooms.get(s.getNo()))) {
		room=new ClassRoom(s.getNo());
		rooms.put(s.getNo(), room);
	    }
	    room.getStuList().add(s);
	    room.setTotalScore(room.getTotalScore()+s.getScore());
	}
    }
    
    public static void exam(List<Student> stuList) {//添加学生成员
	stuList.add(new Student("a","001",78));
	stuList.add(new Student("b","002",60));
	stuList.add(new Student("c","002",36));
	stuList.add(new Student("d","001",68));
	stuList.add(new Student("e","003",84));
	stuList.add(new Student("f","003",94));
    }
}
