package Server;

import java.util.HashMap;
import java.util.Map;

public class WebContent {
	//对应名的servlet
	private Map<String,String> mapServlet=null;
	//将相同的意义的名指向一个名  例如 LOG->Login/Log->Login
	private Map<String,String> mapName=null;
	//测试时  构造里面添加map数据   ,,以后可以解析xml及进行添加
	public WebContent() {
		mapServlet=new HashMap<String,String>();
		mapName=new HashMap<String,String>();
		mapServlet.put("Login","LogServlet");
		mapServlet.put("Reg", "RegServlet");
		mapName.put("/Log", "Login");
		mapName.put("/Login","Login");
		mapName.put("/Reg","Reg");		
	}
	
	public String getCLz(String url) {
		return mapServlet.get(mapName.get(url));
	}
	
	//利用反射获取class
	public Servlet getServlet(String url) {
		String className=getCLz(url);
		if(null!=className) {
    		try {
    			Class<?> clz=Class.forName("Server."+className);
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
