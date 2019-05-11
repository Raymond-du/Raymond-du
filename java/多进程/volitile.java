package Test02;

//volitile(易变的)用于保证数据的同步  也是可见性的

public class Test{
	//private static int num=0;
	//如果将num修饰为volitile则可以避免
	private static volatile int num=0;//强制更新数据
	public static void main(String[] args) throws Exception {
		new Thread(()->{
			//这里将不会退出   因为cpu没有时间去将源数据(num)更新到缓冲中
			while(num==0) {//这里里面不要写东西  让cpu特别忙
				
			}
		}).start();
		Thread.sleep(1000);
		num=1;
	}
}
