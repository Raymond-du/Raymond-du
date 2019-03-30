package Thinking;
/**
 * 类似于io设计模式
 * 使用静态代理实现多线程
 * 好处:避免单继承的局限性  
 * 	便于共享资源
 * @author 26368
 *
 */
	
public class Web12306 implements Runnable{
    private int ticket=50;
    @Override
    public void run() {
	while(ticket>0) {
	    System.out.println(Thread.currentThread().getName()+"买了"+ticket--);
	}
    }
    
    public static void main(String[] args) {//由thread作为代理创建不同对象的线程
	Runnable web=new Web12306();
	Thread t1=new Thread(web,"黄牛1");
	Thread t2=new Thread(web,"黄牛2");
	Thread t3=new Thread(web,"黄牛3");
	Thread t4=new Thread(web,"黄牛4");
	t1.start();
	t2.start();
	t3.start();
	t4.start();
    }
}
