package Test;

import java.util.Date;

//电脑类型  这里使用聚合模式
public abstract class Computer {
	protected Brand brand;
	protected int count=0;
	
	public void setCount(int count) {
		this.count=count;
	}
	public void setBrand(Brand brand) {
		this.brand=brand;
	}
	
	public String getBrand() {
		if(brand==null) {
			return "";
		}
		return brand.getName();
	}
	public int getCount() {
		return count;
	}
	
	public void sale() {
		count--;
		System.out.println(new Date()+":出售了"+getBrand()+getKind());
	}
	
	
	@Override
	public String toString() {
		return getBrand()+getKind()+"剩余"+getCount()+"台";
	}
	
	public abstract String getKind() ;
}

class Desktop extends Computer{
	public Desktop() {
	}

	@Override
	public String getKind() {
		return "台式机电脑";
	}

}
class Laptop extends Computer{
	public Laptop() {
	}

	@Override
	public String getKind() {
		return "笔记本";
	}
}