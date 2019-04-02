package Thinking;

/**
 * 生产消费者模式
 * 避免  线程的死锁
 * @author 26368
 *
 */

//表示影片   需要表演者 演和消费者看
public class Movie{
    
    public  String name ;//电影名字
    private boolean flag=true;//true表示生产者可以操作  false表示 消费者操作
    
    public synchronized void play(String name) {
	if(false==flag) {
	    try {//如果flag是false  则进入线程等待
		this.wait();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	try {
	    Thread.sleep(200);//模拟进行生产时间
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	this.name=name;
	this.notify();
	flag=false;
    }
    
    public synchronized void watch() {
	if(flag) {
	    try {//如果flag是true则进入线程等待
		this.wait();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	try {
	    Thread.sleep(200);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	this.notify();
	flag=true;
    }
    
    public static void main(String[] args) {
	Movie m=new Movie();
	player p=new player(m);
	Watcher w=new Watcher(m);
	Thread t=new Thread(p);
	Thread t1=new Thread(w);
	t.start();
	t1.start();
    }
}
//表示制作者
class player implements Runnable{

    Movie m;    
    public player(Movie m) {
	super();
	this.m = m;
    }

    @Override
    public void run() {
	for(int i=0;i<20;i++) {
	    m.play("电影"+i);
	    System.out.println("生产者生产"+m.name);
	}
    }    
}
//消费者
class Watcher implements Runnable{

    Movie m;
    public Watcher(Movie m) {
	this.m=m;
    }
    @Override
    public void run() {
	for(int i=0;i<20;i++) {
	    m.watch();
	    System.out.println("消费者观看了"+m.name);
	}
    }
    
}
