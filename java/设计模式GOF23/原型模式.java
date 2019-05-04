package test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 原型模式  如果对象需要多次创建或者对象new的过程比较长的情况下clone会加快运行速度
 * 实现对象的clone  对象的clone需要继承Cloneable接口
 * 实现对象的深克隆clone有两种办法1.clone接口  2.序列化于反序列化
 * @author Raymond-du
 *
 */
public class test {
	public static void main(String[] args) throws Exception {
		Student stu01=new Student("01",new Date(2313));
		
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ObjectOutputStream os=new ObjectOutputStream(baos);
		os.writeObject(stu01);
		
		ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream is=new ObjectInputStream(bais);
		
		Student stu02=(Student)is.readObject();
		System.out.println(stu01.date);
		System.out.println(stu02.date);
		
	}
}

class Student implements Cloneable,Serializable{
	public String name;
	public Date date;
	public Student(String name, Date date) {
		this.name = name;
		this.date = date;
	}
	//这种情况只是浅克隆  只是把date的地址传给了新的Student地址
//	@Override
//	protected Object clone() throws CloneNotSupportedException {
//		return super.clone();
//	}		
	//深克隆 会把date对象也复制
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Student temp=(Student)super.clone();
		if(null!=this.date) {			
			temp.date=(Date) this.date.clone();
		}
		return temp;
	}	
}

