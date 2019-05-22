package po;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
/**
 * asp框架的详解
 * 底层实现了线程的阻塞和唤醒
 * AbstractQuenedSynchronizer是一个抽象类,维护了一个int类型的state属性和一个非阻塞,先进先出的线程等待队列
 * 当线程想要获取lock时先判断是否存在进程已经获得了锁,如果存在则线程加入到等待队列中
 * @author raymond-du
 *
 */
public class Test{
	public static int a=20;
	public static void main(String[] args) {
		SimpleLock lock=new SimpleLock();
		
		for(int i=0;i<5;i++) {
			new Thread(new Runnable() {				
				@Override
				public void run() {
					//线程等待获取lock的锁  等待 占用的线程释放锁
					lock.lock();
					while(a>0) {				
						System.out.println(a--);
						try {
							Thread.sleep(100);
							lock.unlock();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}
}	
//和c++的关键代码段同步锁  差不多
class SimpleLock extends AbstractQueuedSynchronizer{

	private static final long serialVersionUID = 1L;
	public SimpleLock() {
		
	}
	//尝试独占模式
	@Override
	protected boolean tryAcquire(int arg) {
		if(compareAndSetState(0, 1)) {
			setExclusiveOwnerThread(Thread.currentThread());
			return true;
		}
		return false;
	}
	//返回true表示任何线程都可以获取  表示已经释放
	@Override
	protected boolean tryRelease(int arg) {
		setExclusiveOwnerThread(null);
		setState(0);
		return true;
	}
	public void lock() {
		//以独占模式获取,忽略中断
		acquire(1);
	}	
	public boolean tryLock() {
		return tryAcquire(1);
	}
	public void unlock() {
		//一专属模式释放
		release(1);
	}
	//如果保持的独占模式返回true已经被独占  false表示可以被获取
	public boolean isLocked() {
		return isHeldExclusively();
	}
}
