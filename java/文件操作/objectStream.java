/**
使用该类的需要注意    写入文件的object是list  则读取的时候也得读到list中
如果一个一个将对象写入文件中  不能只读出一个对象
*/

package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Inflect {
	public static void main(String[] args) {
		/**Student stu1=new Student("张三",001,105,"山西晋中");
		Student stu2=new Student("张四",002,144,"山西晋中太原");
		Student stu3=new Student("张五",003,104,"山西晋中大苏打");
		Student stu4=new Student("张六",004,105,"山西晋的撒旦中");
		List<Student> stuList=new ArrayList<Student>();
		stuList.add(stu1);
		stuList.add(stu2);
		stuList.add(stu3);
		stuList.add(stu4);
		ObjectOutputStream oos=null;
		try {
			oos=new ObjectOutputStream(new FileOutputStream(new File("a.txt")));
			oos.writeObject(stuList);
			oos.flush();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(null!=oos) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/
		
		List<Student> stuList;
		ObjectInputStream ois=null;
		try {
			ois=new ObjectInputStream(new FileInputStream(new File("a.txt")));
			stuList=(List<Student>) ois.readObject();
			System.out.println(stuList.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			if(null!=ois) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

@SuppressWarnings("serial")
class Student implements java.io.Serializable{
	String name;
	int id;
	int classRoom;
	String addr;
	public Student(){
		
	}
	public Student(String name, int id, int classRoom, String addr) {
		super();
		this.name = name;
		this.id = id;
		this.classRoom = classRoom;
		this.addr = addr;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", id=" + id + ", classRoom=" + classRoom + ", addr=" + addr + "]";
	}
	
}
