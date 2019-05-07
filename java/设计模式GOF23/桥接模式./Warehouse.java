package Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


//获取仓库数据

public class Warehouse{
	private static final Warehouse warehouse=new Warehouse();
	//电脑种类对应仓库剩余量
	private Set<Computer> set=new HashSet<Computer>();
	
	public Set<Computer> getSet() {
		return set;
	}
	public static Warehouse getInstance() {
		return warehouse;
	}
	//这里设置成静态内部类会比较好  (可以访问外部类类成员)
	private class WarehouseHandler extends DefaultHandler{
		//电脑类型和电脑数量
		private final int KIND=0,BRAND=1,COUNT=2;
		
		private String kind;
		private String brand;
		private Integer count;
		private int type=-1;
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			String temp=new String(ch,start,length);
			switch(type) {
			case KIND:
				kind=temp;
				break;
			case BRAND:
				brand=temp;
				break;
			case COUNT:
				count=Integer.parseInt(temp);
				break;
			default:
				return ;
			}
		}
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if(qName!=null) {
				if(qName.equals("Kind")) {
					type=KIND;
				}else if(qName.equals("Brand")){
					type=BRAND;
				}else if(qName.equals("Count")){
					type=COUNT;
				}
				
			}
		}
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if(null!=qName) {
				if(qName.equals("combination")) {
					Computer c=ComputerFactory.getComputerByName(kind, brand, count.intValue());
					if(c!=null) {
						set.add(c);
					}	
				}
			}
			type=-1;
		}		
	}
	
	private Warehouse(){
		//防止反射破坏静态变量
		if(warehouse==null) {
			//指定解析文件
			InputStream is=Thread.currentThread().getContextClassLoader().
					getResourceAsStream("data.xml");
    		//获取解析工厂
        	SAXParserFactory factory=SAXParserFactory.newInstance();
        	//获取解析器
        	SAXParser parser;
			try {
				parser = factory.newSAXParser();
				parser.parse(is, new WarehouseHandler());
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(is!=null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
        	
    	}		
	}
	
}