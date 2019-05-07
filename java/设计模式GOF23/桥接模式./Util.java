package Test;

import java.io.InputStream;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;


public class Util {
	public static void printAllComputer() {

		Warehouse ware=Warehouse.getInstance();
		Set<Computer>set=ware.getSet();
		for(Computer c:set) {
			System.out.println(c.toString());
		}
	}
	//打印指定品牌的电脑
	public static void printComputersByBrand(String brand) {
		Warehouse ware=Warehouse.getInstance();
		Set<Computer>set=ware.getSet();
		
		for(Computer c:set) {
			if(c.getBrand().equals(brand)) {
				System.out.println(c.toString());
			}
		}
	}
	//打印指定类型的电脑
	public static void printComputersByKind(String kind) {
		Warehouse ware=Warehouse.getInstance();
		Set<Computer>set=ware.getSet();
		
		for(Computer c:set) {
			if(c.getKind().equals(kind)) {
				System.out.println(c.toString());
			}
		}
	}
	
	//查找指定的电脑
	public static Computer getComputer(String kind,String brand) {
		Warehouse ware=Warehouse.getInstance();
		Set<Computer>set=ware.getSet();
		
		for(Computer c:set) {
			if(c.getKind().equals(kind)&&c.getBrand().equals(brand)) {
				return c;
			}
		}
		return null;
	}
	//售卖电脑
	public static boolean saleComputer(String kind,String brand) {
		Warehouse ware=Warehouse.getInstance();
		Set<Computer>set=ware.getSet();
		
		for(Computer c:set) {
			if(c.getKind().equals(kind)&&c.getBrand().equals(brand)) {
				c.sale();
				return true;
			}
		}
		System.out.println("未找到该电脑");
		return false;
	}
	
	//进货增加货物
	public static boolean Purchase(String kind,String brand,int count) {
		Computer c=getComputer(kind,brand);
		if(null!=c) {
			c.setCount(c.getCount()+count);
		}
		
		Warehouse ware=Warehouse.getInstance();
		Set<Computer>set=ware.getSet();
		
		c=ComputerFactory.getComputerByName(kind, brand,count);
		if(c==null) {
			return false;
		}
		set.add(c);
		return true;
	}
	//更新数据到xml文件中   (未做好,不正确)
	public static void updataWarehouse() {
		InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("data.xml");
		//dom对象解析工厂
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		//获取xml解析器 
		DocumentBuilder dBuilder=null;
		try {
			dBuilder=factory.newDocumentBuilder();
			//解析成一个dom对象(xml文档)
			Document dom=dBuilder.parse(is);
			//将当前节点和他的后代规范化,删除空白文档
			dom.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
