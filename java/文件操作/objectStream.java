package Thinking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 将对象数据保存到文件中
 * @author 26368
 *
 */


public class objectStream{
    public static void write(String destPath,Student stu) throws IOException {
	File dest=new File(destPath);
	ObjectOutputStream oos=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));
	oos.writeObject(stu);
	oos.flush();
	oos.close();
    }
    
    public static Student read(String srcPath) throws IOException, ClassNotFoundException {
	File src=new File(srcPath);
	
	ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(new FileInputStream(src)));
	Object stu=ois.readObject();
	ois.close();
	System.out.println(((Student)stu).toString());
	if(stu instanceof Student) {
	    return (Student)stu;
	}else
	    return null;
    }
    
    public static void main(String[] args) {
	/**Student stu=new Student (001,"du",10,"meiyou");
	try {
	    write("C:\\Users\\26368\\Videos\\Captures\\wo.txt",stu);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}*/
	Student stu=new Student(001,"jj",10,"mwiyou");
	try {
	    write("C:\\Users\\26368\\Videos\\Captures\\wo.txt", stu);
	    read("C:\\Users\\26368\\Videos\\Captures\\wo.txt");
	}catch (IOException e) {
	    e.printStackTrace();
	}catch(ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }
}