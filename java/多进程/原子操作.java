package Test02;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程原子操作  在 java.util.concurrent(同时)
 * 测试 Atomic(原子)Integer
 * 
 * 原子模式的原理 :
 * 原子模式没有synchronied那样的线程同步  
 * CAS 操作需要输入两个数值，一个旧值（期望操作前的值）和一个新值，在操作期间先比较下旧值
 * 有没有发生变化，如果没有发生变化，才交换成新值，发生了变化则不交换。
 * 
 * 模仿 抢购
 * @author 26368
 *
 */

public class Test{
	private static AtomicInteger ai=new AtomicInteger(5);
	public static void main(String[] args){
		for(int i=0;i<10;i++) {
    		new Thread(()->{
    			if(ai.get()>0) {
    				System.out.println(Thread.currentThread().getName()+"抢到了"+ai.decrementAndGet());
    			}
    		}).start() ;
		}
	}	
}
