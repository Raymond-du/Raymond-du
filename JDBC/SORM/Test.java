package SORM;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test{
	public static void main(String[] args) throws InterruptedException {
		Data d=new Data();
		new Thread(new MyRun(d)).start();
		new Thread(new MyRun(d)).start();
		
		Thread.sleep(10000);
		d.setTicket();
	}
}

class MyRun implements Runnable{
	private Data d=null;
	public MyRun(Data d) {
		this.d=d;
	}
	@Override
	public void run() {
		while(true) {
		System.out.println(Thread.currentThread().getName()+d.getTicket());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}}
	}
	
}

class Data{
	public int ticket=10;
	public synchronized int getTicket() {
		if(ticket<1) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return ticket--;
	}
	public synchronized void setTicket() {
		ticket+=10;
		notifyAll();
	}	
}