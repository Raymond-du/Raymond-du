package Test02;

/**
 * 指令重排  (发生后面的代码限执行前面的代码后执行)
 * 多线程会发生指令重排的效果与 理想相反
 * @author 26368
 *
 */
public class Test{
	private static int a=0;
	private static boolean flag=false;
	public static void main(String[] args) throws Exception{
		Thread1 t1=new Thread1();
		Thread2 t2=new Thread2();
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}
	
	static class Thread1 extends Thread{
		public void run() {
			a=1;
			flag=true;
		}
	}
	static class Thread2 extends Thread{
		public void run() {
			//先看线程一 如果flag==true  则a=1了  则a==0不会发生
			if(flag) { 
				System.out.println("flag=="+flag);
			}
			if(a==0) {
				System.out.println("a=="+a);
			}
		}
	}
}
