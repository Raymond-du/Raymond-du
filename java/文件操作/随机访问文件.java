package test;

import java.io.File;
import java.io.RandomAccessFile;
/**
 * 测试随机访问文件,支持修改等长度的数据,不支持插入及删除
 * @author 26368
 *
 */
public class test{
	public static void main(String[] args) throws Exception {
		File file=new File("C:\\Users\\26368\\Documents\\IOCP.txt");
		RandomAccessFile raf=new RandomAccessFile(file, "rw");
		byte[] strBytes=(new String("woshinibaba")).getBytes();
		raf.seek(30);
		raf.write(strBytes,0, strBytes.length);
		raf.close();
	}
	
}
