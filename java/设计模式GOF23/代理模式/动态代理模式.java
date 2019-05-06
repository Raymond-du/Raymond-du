package Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;



/**
 * 测试动态代理模式  (类似于hook原理  拦截函数并修改函数)
 * @author Raymond-du
 *
 */
public class Test {
	public static void main(String[] args) throws Exception {
		Star LiuStar=new LiuStar();
		InvocationHandler proxy=new StarHandler(LiuStar);
		//获取代理对象  第二个参数是实现代理类的所有接口类
		Star star=(Star) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
				new Class[]{Star.class}, proxy);
		star.sing();
	}
}

//用该类可以动态的管理改类 可以实现对源代码的修改
class StarHandler implements InvocationHandler{
	Star star=null;
	public StarHandler(Star realStar) {
		this.star=realStar;
	}
	
	//第一个参数是执行的对象 第二个参数是对象的方法  第三个参数是对象的参数数组\
	//用方法名进行指定对象的拦截  
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(method.getName().equals("sing")) {
			System.out.println("&&&&&&&");
			star.sing();
		}
		//如果函数有返回值也可以直接在这里返回
		return null;
	}
	
}

