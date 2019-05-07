package Test;

//根据字符串创建相应的对象
public class ComputerFactory {
	public static Computer getComputerByName(String kind,String brand,int count) {
		Computer c =null;
		Brand b=BrandFactory.getBrandByName(brand);
		if(null!=kind) {
			if(kind.equals("台式机")||kind.equals("DeskTop")) {
				c=new Desktop();
			}else if(kind.equals("笔记本")||kind.equals("Laptop")){
				c=new Laptop();				
			}else {
				System.out.println("未发现该类型的电脑");
			}
		}
		if(null!=c) {
			if(null!=b) {
				c.setBrand(b);
			}
			c.setCount(count);
		}
		return c;
	}
}
class BrandFactory{
	public static Brand getBrandByName(String name) {
		Brand brand=null;
		if(null!=name) {
    		if(name.equals("联想")||name.equals("Lenovo")) {
    			brand=new Lenovo();
    		}else if(name.equals("戴尔")||name.equals("Dell")) {
    			brand=new Dell();
    		}else {
    			brand =new Unknown(name);
    		}
		}
		return brand;
	}
}