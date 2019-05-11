package Test02;

/**
 * ThreadLocal  线程的独有的内存空间
 *  有主要的三个函数 set get initalValue
 * 设置内存空间的内容
 * get获取内存的内容
 * initalValue 初始化
 * 
 * 主线程  其他线程的构造函数   currentThread是main线程
 * 哪里调用就属于哪里
 * 
 * ThreadLocal 的子类InheritableThreadLocal继承上下文的数据
 * @author 26368
 *
 */
public class Test{
	//ThreadLocal 是每个线程的独有的地方  多个线程之间不会相互影响
	private static ThreadLocal<Integer> threadLocal=ThreadLocal.withInitial(()-> 20);
	public static void main(String[] args){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(threadLocal.get()>0) {
					System.out.println(Thread.currentThread().getName()+"pop"+threadLocal.get());
					threadLocal.set(threadLocal.get()-1);
				}
			}
		}).start();
		while(threadLocal.get()>0) {
			System.out.println(Thread.currentThread().getName()+"pop"+threadLocal.get());
			threadLocal.set(threadLocal.get()-1);
		}
	}
	
}
