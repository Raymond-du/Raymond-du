package po;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * executor 是一个接口   executors是一个工厂创建各种类型的线程池
 * wait 可以将执行权交给下一个任务
 * 使用ExecutorService  可以依次获得有序的结果  但是不能及时获得已完成的任务
 * 使用ExecutorCompletionService 包装了ExecutorService  实现了及时获取已完成的任务 
 * 
 * Executors.newCachedThreadPool大小不受限,当线程释放时,可以重用该线程
 * Executors.newSingleThreadExecutor创建一个单线程,任务会依次进行
 * Executors.newScheduledThreadPool创建一个定长的线程池,支持定时及周期任务执执行
 * @author raymond-du
 *
 */
public class Test {
	public static void executor(Task[] task) {
		//获取cpu的核心数
		int CPUCoreCount=Runtime.getRuntime().availableProcessors();
		//创建对应核心数的线程池
		ExecutorService executor=Executors.newFixedThreadPool(CPUCoreCount);
		//创建多个任务  需继承自Callable的接口
		//Task task=new Task();
		//Task task1=new Task();
		//将任务提交到线程池中  返回的结果到future中     
		List<Future<Integer>> futureList=new ArrayList<>();
		for(Task temp:task) {
			futureList.add(executor.submit(temp));
		}
		//返回结果
		try {
    		for(Future<Integer> temp:futureList) {
    			System.out.println(temp.get());
    		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		//创建线程池
		ExecutorService executor=Executors.newFixedThreadPool(2);
		//使用ExecutorCompletionService包装    实现可以及时获取已完成的任务
		ExecutorCompletionService<Integer> comExecutor=new ExecutorCompletionService<>(executor);
		//创建任务
		Task task1=new Task(20);
		Task task2=new Task(5);
		Task task3=new Task(3);
		
		comExecutor.submit(task1);
		comExecutor.submit(task2);
		comExecutor.submit(task3);
		try {
    		for(int i=0;i<3;i++) {
    			//comExecutor.take()获取已完成的任务结果
    			System.out.println(comExecutor.take().get());
    		}
		}catch(Exception e) {
			
		}    		
	}
}
class Task implements Callable<Integer>{
	private int count=0;
	public Task(int count){
		this.count=count;
	}
	@Override
	public Integer call() throws Exception {
		while(true) {
			if(count<0) {
				break;
			}
			System.out.println(Thread.currentThread().getName()+"执行"+count--);
			Thread.sleep(300);
		}
		return count;
	}	
}

