package Thinking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * 节点流   改流函数不是读写文件的  用来对数据的处理   结束后数据不会消失
 * 适合小数据的处理
 * ByteArrayOutputStream  ByteArrayOutputStream
 * read(byte[],int off,int len)+close()
 * write(byte[],int off,int len)+toByteArray
 * 想要该类中的新建方法不能使用多态
 * @author 26368
 *
 */
public class byteArrayStreamDemo {
    public static byte[] getByteFromFile(String srcPath) throws IOException {
	byte[] flush=null;//这是返回值
	ByteArrayOutputStream bos=new ByteArrayOutputStream();
	File src=new File(srcPath);
	InputStream is=new BufferedInputStream(new FileInputStream(src));
	byte[] data=new byte[1024];
	int len=0;
	while(-1!=(len=is.read(data))){
	    bos.write(data, 0, len);
	}
	bos.flush();//将数据刷新到bos中
	flush=bos.toByteArray();// 数组大小随  读取的大小变化
	is.close();
	bos.close();	
	return flush;
    }
    
    //将字节数组写入到文件中
    public static void toFileFromByteArray(byte[] src,String destPath) throws IOException {
	File dest=new File(destPath);
	InputStream is=new BufferedInputStream(new ByteArrayInputStream(src));
	OutputStream os=new BufferedOutputStream(new FileOutputStream(dest,false));
	byte[] flush =new byte[1024];
	int len=0;
	while(-1!=(len=is.read(flush))) {
	    os.write(flush, 0, len);
	}
	os.flush();
	os.close();
	is.close();	
    }
    
    public static void main(String[] args) {
	String s="woainisda";
	byte[] b=s.getBytes();
	try {
	    toFileFromByteArray(b, "C:\\Users\\26368\\Documents\\Visual Studio 2013\\源码\\c++算法代码.txt");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
}
