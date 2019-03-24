package Thinking;
/**
 * RandomAccessFile的练习  面向对象的思想
 * @author 26368
 *
 */

import java.util.List;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class SplitFile {
    
    //文件对象
    private File file=null;
    //分割的块数
    private long size=-1;
    //分割大小
    private long blockSize=-1;
    //分割的名称 
    private List<String> blockName=null;
    //文件大小
    private long fileLength;
    
    //默认分割块大小;
    public SplitFile(String filePath) {
	this(new File(filePath),1024);
    }
    public SplitFile(File f) {
	this(f,1024);	
    }
    public SplitFile(String filePath,long blockSize) {
	this(new File(filePath),blockSize);
    }
    public SplitFile(File f,long blockSize)  {
	blockName=new ArrayList<String>();
	this.file=f;
	this.blockSize=blockSize;
	init();	
    }
    
    //初始化成员
    private void init()  {
	//判断文件是否合格
	if(!file.exists()||file.isDirectory()) {
	    try {
		throw new IOException("文件未找到");
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}	
	//判断分割大小是否合法
	if(blockSize<=0) {
	    throw new IllegalArgumentException("传入分割大小不合法");
	}
	//计算文件大小
	fileLength=file.length();
	if(blockSize>fileLength) {
	    blockSize=fileLength;
	    size=1;
	}
	//计算分割的块数  注意这里需要乘以1.0  不加的话 可能与实际不同
	size=(long) Math.ceil(fileLength*1.0/blockSize);
	//给分割文件设置文件名;
	initFileName();
    }
    //给分割文件设置文件名;
    private void initFileName() {
	String fileName=file.getName();
	int index=fileName.indexOf('.');
	for(int i=0;i<size;i++) {
	    //源文件名+part +i+文件类型
	    blockName.add(fileName.substring(0, index)+"Part"+i+fileName.substring(index));
	}
    }
    //分割文件
    public void split(String dest) {
	//切割开始位置
	long beginPos=0;
	//切割的大小
	long actualBlockSize=blockSize;
	//创建目标文件夹
	(new File(dest)).mkdirs();
	for(int i=0;i<size;i++) {
	    //切割  最后一块数据
	    if(size-1==i) {
		actualBlockSize=fileLength-beginPos;
	    }
	    saveSplitFile(new File(dest+'/'+blockName.get(i)), beginPos, actualBlockSize);
	    beginPos+=actualBlockSize;
	}
    }
    //保存分割后的文件
    private void saveSplitFile(File dest,long beginPos,long length) {
	//源文件读取流
	RandomAccessFile raf=null;
	//输出文件写入流
	OutputStream os=null;
	byte[] flush=new byte[1024];
	try {
	    dest.createNewFile();
	    os=new BufferedOutputStream(new FileOutputStream(dest));
	    //读取源文件的数据
	    raf=new RandomAccessFile(file, "r");
	    //定位开始读取的地方
	    raf.seek(beginPos);
	    while(true) {
		if(1024<length) {
		    raf.readFully(flush);
		    length-=1024;
		    os.write(flush, 0, 1024);
		    continue;
		}
		raf.readFully(flush,0,(int)length);
		os.write(flush,0,(int)length);
		break;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}finally{
	    if(null!=raf) {
		try {
		    raf.close();
		    os.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	
	
    };
    
    
    public static void main(String[] args) {
	SplitFile sf=new SplitFile("C:\\Users\\26368\\Desktop\\java笔记.txt",2048);
	sf.split("C:\\Users\\26368\\Videos\\1");
    }
}








