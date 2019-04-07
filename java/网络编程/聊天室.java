服务端

package Thinking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.ListIterator;
/**
 * 服务socket需要一个进程不断的接收
 * 接收的socket需要重新开辟两个线程进行通道的读和写
 * @author 26368
 *
 */

public class Chatroom implements Runnable{
    private LinkedList<Socket> client=null;
    ServerSocket server=null;
    public Chatroom(int port) throws IOException {
	server=new ServerSocket(port);
	client=new LinkedList<Socket>();
    }
    //该线程进行接收数据
    private class Recv implements Runnable{
	private Socket s=null;
	public Recv(Socket s) {
	    this.s=s;
	}
	@Override
	public void run() {
	    try {
		byte []flush=new byte[1024];
		int len=0;
		InputStream is =s.getInputStream();
		while(-1!=(len=is.read(flush))) {
		    //调用转发函数  发送到聊天室中每个人;  包括自己
		    forward(flush, len);
		}
		client.remove(s);
	    }catch (SocketException e){
		System.out.println(s.getPort()+"已经断开连接");
	    }catch (IOException e) {
		e.printStackTrace();
	    }finally {
		if(!s.isClosed()) {
		    try {
			s.close();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
	    }
	}	
    }
    //转发函数
    private void forward(byte[] flush,int len) {
	OutputStream os=null;
	ListIterator<Socket> it=client.listIterator();
	while(it.hasNext()) {
	    Socket s=it.next();
	    if(s.isClosed()) {
		continue;
	    }
	    try {
		os=s.getOutputStream();
		os.write(flush,0,len);
	    } catch (IOException e) {
		e.printStackTrace();
	    }	    
	}
    }

    
    //用于接受客户端的Socket
    @Override
    public void run() {
	while(true) {
	    try {
		Socket s=server.accept();
		//创建线程用于接受客户端的数据
		new Thread(new Recv(s)).start();
		client.add(s);
		
	    } catch (IOException e) {
		e.printStackTrace();
	    }    
	}
    }
    public static void main(String[] args) {
	try {
	    new Thread(new Chatroom(1214)).start();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}



客户端
package Thinking;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//Client  应该有进行读写的进程
public class Client {
    private String name;
    private Socket client;   
    //再构造函数中直接进行连接
    public Client(String name, String host,int port) throws UnknownHostException, IOException {
	super();
	this.name = name;
	client=new Socket(host, port);	
    }
    //启动收发线程
    public void start() {
	if(null==client) {
	    return ;
	}
	Thread recv=new Thread(new Recv());
	Thread write=new Thread(new Write());
	recv.start();
	write.start();
    }
    //用于发送消息的线程
    private class Write implements Runnable{
	OutputStream os=null;
	//用于获得控制台的输入
	Scanner sc=new Scanner(System.in);
	@Override
	public void run() {
	    try {
		os=client.getOutputStream();
		while(true) {
    		    if(!client.isConnected()) {
    			break;
    		    }
    		    os.write((name+':'+sc.nextLine()).getBytes());	
    		    os.flush();
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}	
    }
    
    //用于接受消息的线程
    private class Recv implements Runnable{
	InputStream is=null;
	byte[] flush=new byte[1024];
	int len=0;
	@Override
	public void run() {
	    try {
		is=client.getInputStream();
		//读取消息到缓冲流中
		while(-1!=(len=is.read(flush))) {
		    //展示到控制台中;
		    System.out.println(new String (flush,0,len));
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }finally {		
		try {
		    client.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}	
	    }
	}	
    }
    
    public static void main(String[] args) {
	Client client=null;
	try {
	    client = new Client("张三", "127.0.0.1", 1214);
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	client.start();
    }
}
