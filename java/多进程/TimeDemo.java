package Thinking;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 练习任务调度
 * @author 26368
 *
 */
public class TimeDemo{
    public static void main(String[] args) {
	
	Timer t=new Timer();
	t.schedule(new TimerTask() {
	    int i=0;
	    @Override
	    public void run() {
		System.out.println("执行次数"+(++i));
	    }
	}, new Date(System.currentTimeMillis()+2000), 1000);  //开始执行任务的时间  和执行任务的间隔
	
	try {
	    Thread.sleep(5000);
	    t.cancel();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}