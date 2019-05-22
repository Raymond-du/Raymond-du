package po;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 测试 abstractQueuedSynchronizer的唯一实现类
 * ReentrantLock 默认时非公平锁,  公平锁  按照发出请求的顺序获得锁   非公平锁线程的请求可以插队
 * 可重入锁是  一个线程获取两次锁是不会发生死锁
 * @author 26368
 *
 */
public class Test{
	public static int a=20;
/*	public static void main(String[] args) {
		Lock lock=new ReentrantLock(false);
		
		for(int i=0;i<5;i++) {
			new Thread(new Runnable() {				
				@Override
				public void run() {
					//线程等待获取lock的锁  等待 占用的线程释放锁
					while(a>0){				
						lock.lock();
						try{
							System.out.println(Thread.currentThread().getName()+a--);
						}finnaly{
							lock.unlock();
						}						
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
					
				}
			}).start();
		}
	}*/
	public static void main(String[] args) throws InterruptedException {
		ReadWriteLock lock=new ReentrantReadWriteLock();
		for(int i=0;i<5;i++) {
			new Thread(new Runnable() {
				public void run() {
					//读取的锁可以确保所内的值读取到不发生改变   但是里面的值可能已经发生改变
					lock.readLock().lock();
					System.out.println(Thread.currentThread().getName()+a);
					a--;
					lock.readLock().unlock();
					System.out.println(a);
					//使用写入锁可以保证a的值同步的减小   
					/*lock.writeLock().lock();
					a--;
					lock.writeLock().unlock();*/				
					
				}
			}).start();
		}
	
		Thread.sleep(100);
		
		System.out.println(a);
	}
}	
