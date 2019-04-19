package Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class WebContent {
	//对应名的servlet
	private Map<String,String> mapServlet=null;
	//将相同的意义的名指向一个名  例如 LOG->Login/Log->Login
	private Map<String,String> mapName=null;
	//测试时  构造里面添加map数据   ,,以后可以解析xml及进行添加
	public WebContent() {
		mapServlet=new HashMap<String,String>();
		mapName=new HashMap<String,String>();
		
		//获取解析工厂
		SAXParserFactory factory=SAXParserFactory.newInstance();
		//获得解析器
		try {
			SAXParser parser=factory.newSAXParser();
			WebHandler handler=new WebHandler();
			InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("test/1.xml");
			parser.parse(is,handler);
			is.close();
			//将webhandler中的数据存到webcontext
			for(Entity e:handler.getEntityList()) {
				mapServlet.put(e.getName(),e.getClz());
			}
			for(Mapping m:handler.getMappingList()) {
				for(String s:m.getPattern()) {
					mapName.put(s, m.getName());
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getCLz(String url) {
		return mapServlet.get(mapName.get(url));
	}
	
	//利用反射获取class
	public Servlet getServlet(String url) {
		String className=getCLz(url);
		if(null!=className) {
    		try {
    			System.out.println(className);
    			Class<?> clz=Class.forName(className);
    			return (Servlet)clz.newInstance();
    		} catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
