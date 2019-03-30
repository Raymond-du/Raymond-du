package Thinking;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 使用callable创建线程 的call函数和runnable的run对比
 * call有返回值,call可以扔出异常
 * @author 26368
 *  龟兔赛跑的模拟练习
 */
public class test {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
	//创建线程  参数是创建的线程数  Executor-->执行者
	ExecutorService ser=Executors.newFixedThreadPool(2);
	race rabbit=new race("兔子",200);
	race tortoise=new race("乌龟",400);
	//获取运行后的返回值  利用future的get获取返回值
	Future<Integer> result1=ser.submit(rabbit);
	Future<Integer> result2=ser.submit(tortoise);
	
	Thread.sleep(2000);
	rabbit.setFlag(false);
	tortoise.setFlag(false);
	
	System.out.println("兔子跑了"+result1.get()+"乌龟跑了"+result2.get());
	//关闭线程  不会自动退出
	ser.shutdownNow();
    }
}

//这里泛型是指线程运行后的返回值
class race implements Callable<Integer>{
    private String name;
    private long time ;//每步的时间
    private int step=0;
    private boolean flag=true;//标志是否可以跑
       
    public race(String name, long time) {
	super();
	this.name = name;
	this.time = time;
    }

    @Override
    public Integer call() throws Exception {
	while(flag) {
	    Thread.sleep(time);
	    step++;
	}
	return step;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }
    
}
