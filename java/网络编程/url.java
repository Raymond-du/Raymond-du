package Thinking;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 *网络编程  InetAddress和Inet SocketAddress
 *Url
 *
 * @author 26368
 *
 */


public class url {
    public static void main(String[] args) {
	/**
	InetAddress addr=null;
	try {
	    addr = InetAddress.getByName("www.baidu.com");
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}
	System.out.println(addr.getHostAddress());//输出地址
	System.out.println(addr.getHostName());//输出域名*/
	
	//InetSocketAddress socketAddress=new InetSocketAddress("www.baidu.com", 80);//第一个参数是域名或者IP地址,第二个是端口
	//socketAddress.getPort();//获取端口   和InetAddress就差一个端口
	URL url=null;
	InputStream is=null;
	try {
	    url=new URL("https://www.baidu.com/index.html");
	    System.out.println("ip地址"+InetAddress.getByName(url.getHost()).getHostAddress());
	    System.out.println("获取与此协议相关的默认端口"+url.getDefaultPort());
	    System.out.println("getFile"+url.getFile());//端口号后面的内容
	    System.out.println("端口号"+url.getPort());//输出端口号
	    System.out.println("主机名"+url.getHost());
	    System.out.println("锚点"+url.getRef());
	    System.out.println("协议"+url.getProtocol());	
	    System.out.println("参数部分"+url.getQuery());
	    System.out.println("路径"+url.getPath());//端口号后面  参数前面的内容
	    
	    //打开流  写入到baos 流中
	    is = url.openStream();
	    byte flush[]=new byte[1024];
	    int len=0;
	    
	    OutputStream os=new BufferedOutputStream(new FileOutputStream(new File("temp.txt")));
	    while(-1!=(len=is.read(flush))) {
		os.write(flush,0,len);
	    }
	    os.flush();
	    os.close();
	    /**ByteArrayOutputStream baos=new ByteArrayOutputStream();
	    
	    while(-1!=(len=is.read(flush))) {
		baos.write(flush,0,len);
	    }
	    baos.flush();
	    
	    OutputStream os=new BufferedOutputStream(new FileOutputStream(new File("temp.txt")));
	    os.write(baos.toByteArray());
	    os.flush();
	    os.close();
	    baos.close();*/
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}finally {
	    if(is!=null) {
		try {
		    is.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	
	
	
    }
}
