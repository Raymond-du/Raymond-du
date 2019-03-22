package Thinking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 这个处理流实现对int,double等基本类型的储存
 * 写入文件  保留了类型和数据
 * 注意 写入顺序和  写出顺序 必须相同  否者数据可能出错
 * @author 26368
 *
 */
public class dataStreamDemo{
    public static void read(String srcPath) throws IOException {
	File src=new File(srcPath);
	DataInputStream dis=new DataInputStream(new BufferedInputStream(new FileInputStream(src)));
	double x=0;//读数据
	x=dis.readDouble();
	dis.close();
    }
    
    public static void write(String destPath) throws IOException {
	File dest=new File(destPath);
	DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));
	dos.writeDouble(1.2);//写入double   writeXxx 写入不同类型的数据
	dos.writeUTF("wo");//写入字符串
	dos.flush();
	dos.close();
    }
    
    public static void main(String[] args) {
	
    }
}