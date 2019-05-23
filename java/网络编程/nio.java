package po;
//这里适用于多链接少数据  单线程即可完成io的手法操作

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test{
	public static void main(String[] args) throws Exception {
		//创建一个selector  用来选择read write accept消息
		Selector selector=Selector.open();
		//初始化tcp连接通道
		ServerSocketChannel listenChannel=ServerSocketChannel.open();
		//绑定端口
		listenChannel.bind(new InetSocketAddress(1214));
		//设置为非阻塞模式  
		listenChannel.configureBlocking(false);
		//把accept消息注册到selector里  注册消息是accept
		listenChannel.register(selector,SelectionKey.OP_ACCEPT);
		while(true) {
			//阻塞直到有消息到来
			selector.select();
			Iterator<SelectionKey> keyIter=selector.selectedKeys().iterator();
			//读入的字节缓冲 
			ByteBuffer buf=ByteBuffer.allocate(64);
			//通过迭代器依次访问select出来的Channel的事件
			while(keyIter.hasNext()) {
				SelectionKey key=keyIter.next();
				//存在连接
				if(key.isAcceptable()) {
					//连接到了客户端的通道  
					SocketChannel channel=((ServerSocketChannel)key.channel()).accept();
					channel.configureBlocking(false);
					//注册消息 read消息
					channel.register(selector, SelectionKey.OP_READ);
					System.out.println("已连接到客户端,等待客户端消息");
				}else if(key.isReadable()) {
				//接收到read消息		
										
					//写入时将buf清空
					buf.clear();
					
					//这里不用循环的读取  如果没有读取结束 会继续收到read消息
					if(((SocketChannel)key.channel()).read(buf)==-1) {
						InetSocketAddress remoteAddr=(InetSocketAddress) ((SocketChannel)key.channel()).getRemoteAddress();
						System.out.println(remoteAddr.getHostName()+":"+remoteAddr.getPort()+"已经断开连接");
						key.channel().close();
						continue;
					}
					//读取字符的时候将使用flip 将position置0
					buf.flip();
					System.out.println(new String(buf.array()));	
					
					//发送收到消息
					buf.clear();
					buf.put("i recived your info".getBytes());
					buf.flip();
					//循环发送消息
					while(buf.hasRemaining()) {
						((SocketChannel)key.channel()).write(buf);
					}
				}
				
				//处理完一个事件  需要手动的清除
				keyIter.remove();
			}			
		}
	}
}
