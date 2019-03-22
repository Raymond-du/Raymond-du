
package Thinking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class objectStream{
    public static void main(String[] args) {
	//利用打印流  写入文件中
	/**PrintStream ps=null;
	try {
	    ps=new PrintStream(new BufferedOutputStream(
		    new FileOutputStream(new File("C:\\Users\\26368\\Videos\\Captures\\wo.txt"))));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	ps.println("woaini");
	ps.close();
	*/
	
	//这里用打印流  读取文件中的数据
	/**InputStream is=null;
	try {
	    is=new BufferedInputStream(new FileInputStream(new File("C:\\Users\\26368\\Videos\\Captures\\wo.txt")));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	Scanner s=new Scanner(is);
	System.out.println(s.nextLine());
	System.out.println(s.nextLine());
	s.close();
	*/
	
	//将控制台获得字符  写道文件中
	/**Scanner s=new Scanner(System.in);
	PrintStream ps=null;
	try {
	    ps=new PrintStream(new BufferedOutputStream(new FileOutputStream(new File("C:/Users/26368/Videos/Captures/wo.txt"))));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}	
	ps.print(s.nextLine());
	ps.close();
	s.close();
	*/
	
	//利用默认函数修改输入的位置
	//PrintStream(OutputStream,bool)第二个参数  是否自动刷新到文件
	/**try {
	    System.setOut(new PrintStream(new BufferedOutputStream(new 
	    	FileOutputStream(new File("C:/Users/26368/Videos/Captures/wo.txt"))),true));//
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	System.out.print("woshi ");*/
	//改回控制台
	//System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out))));
	
	//模仿Scanner 获取控制台的输入数据
	BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(FileDescriptor.in)));
	String buf=null;
	try {
	    buf=br.readLine();
	} catch (IOException e) {
	    e.printStackTrace();
	}finally {
	    if(br!=null) {
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	System.out.println(buf);
    }
}
