package Thinking;

/**
 * 一般 假如缓冲流  BufferedInPutStream  增强效率
 *在读取字符流时遇到编码问题时  使用转换流
 *Reader= InPutStreamReader(new InputStream(""),"字符集")注意这里可以加上缓冲流
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class copyFileDemo {
    
    public static void copyFile(File src,File dest) throws FileNotFoundException {
	InputStream is=null;
	OutputStream os=null;
	
	if(!src.exists()) {//判断源文件是否存在
	    throw new FileNotFoundException("源文件未找到");
	}
	
	try {  	    
	    is=new FileInputStream(src);
	    os=new FileOutputStream(dest);
	    byte[] data=new byte[1024];
	    int len=0;
	    while(-1!=(len=is.read(data))) {//循环读取源文件
		os.write(data, 0, len);
	    };
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}finally {
	    if(null!=src) {
		try {
		    is.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	    if(null!=os) {
		try {
		    os.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }
    
    public static void copyFile(String srcPath,String destPath) throws IOException {
	File src=new File(srcPath);
	File dest=new File(destPath);
	//创建目标文件
	dest.getParentFile().mkdirs();
	dest.createNewFile();
	copyFile(src,dest);
    }
    
    //实现文件夹及文件的全拷贝
    public static void copyDir(File src,File dest) throws FileNotFoundException {
	if(!src.exists()) {
	    throw new FileNotFoundException("未找到源文件"+src.getAbsolutePath());
	}
	if(src.isFile()) {
	    copyFile(src,new File(dest.getPath()+'/'+src.getName()));
	}else {
	    new File(dest.getPath()+'/'+src.getName()).mkdirs();
	    File []fList=src.listFiles();
	    for(File temp:fList) {
		copyDir(temp, new File(dest.getPath()+'/'+src.getName()));
	    }
	}
    }
    
    public static void main(String[] args) {	
	try {
	    copyDir(new File("C:/Users/26368/Videos"), new File("C:\\Users\\26368\\Pictures"));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}	
    }
}

