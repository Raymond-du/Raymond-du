package Test02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 享元模式的练习 
 * 享元模式的开发中的应用场景:
 * 享元模式由于共享特性.可以在池中操作,如 线程池.数据库连接池
 * 
 * String的类的设计也是享元模式  jvm中保存存在字符串池 final可以保证字符串不被修改
 * @author 26368
 *
 */

public class Test{
	public static void main(String[] args) {
		Chass c1=GoFlyWeight.getChass("黑色", "圆");
		Chass c2=GoFlyWeight.getChass("黑色", "圆");
		Chass c3=GoFlyWeight.getChass("白色", "圆");
		Position p=new GoPosition(10,10);
		c3.printPosition(p);
		GoFlyWeight.printAll();
	}
}

//棋子位置的接口
interface Position{
	int getX() ;
	int getY();
	void setX(int x);
	void setY(int y);
}

class GoPosition implements Position{

	private int x,y;
	public GoPosition(int x,int y) {
		this.x=x;
		this.y=y;
	}
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(int x) {
		this.x=x;
	}

	@Override
	public void setY(int y) {
		this.y=y;
	}
	
}

//以棋子为例 
abstract class Chass{
	protected Chass() {}
	//打印棋子颜色
	abstract void printColor();
	//打印棋子的形状
	abstract void printShape();
	//打印棋子位置   不共享的属性
	abstract void printPosition(Position p);
}
//围棋实例
class Go extends Chass{
	private String color;
	private String shape;
	public Go(String color,String shape) {
		this.color=color;
		this.shape=color;
	}
	
	@Override
	public void printColor() {
		System.out.println("棋子的颜色是"+color);
	}

	@Override
	public void printShape() {
		System.out.println("棋子的形状是"+shape);		
	}

	@Override
	public void printPosition(Position p) {
		System.out.println("棋子的位置X:"+p.getX()+" Y:"+p.getY());
	}	
}

//享元工厂
class GoFlyWeight{
	private static Set<Chass> set=new HashSet<>();
	private GoFlyWeight() {}
	public static Chass getChass(String color,String shape) {
		Chass chass=new Go(color,shape);
		if(!set.contains(chass)) {
			set.add(chass);
		}
		return chass;
	}
	public static void printAll(){
		for(Chass c:set) {
			c.printColor();
			c.printShape();
		}
	}
}

