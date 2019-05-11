package Test02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Test{
	public static void main(String[] args) throws InterruptedException {
		/**
		List<String> list=new ArrayList<String>();
		for(int i=0;i<10;i++) {
			new Thread(()->{
				synchronized (list){
					list.add(" ");
				}
			}).start();			
		}
		//如果不加子线程还没有结束 主线程就退出了
		Thread.sleep(100);
		System.out.println(list.size());*/
		//使用并发容器  并发容器再java.util.concurrent包下
		List<String> list=new CopyOnWriteArrayList<>();
		for(int i=0;i<10;i++) {
			new Thread(()->{
				synchronized (list){
					list.add(" ");
				}
			}).start();			
		}
		Thread.sleep(100);
		System.out.println(list.size());
	}
}
