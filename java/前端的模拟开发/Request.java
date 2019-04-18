package Server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Request{	
	
	private InputStream is=null;	
      //空格
    public static final String BLACK=" "; 
    //换行符
    public static final String CRLF="\r\n";
    //原始字符串数据
    private String data;
    //请求方法
    private String method;
    //请求url
    private String URL;
    //请求的协议版本
    private String version;
    //接收到的报文头
    private Map<String,String> msgHead;
    //报文体  (账号及密码)
    private Map<String,String> msgBody;
	
	public Request(Socket s) {
		super();
		try {
			this.is = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		recv();
	}

	//解析数据 
	public void analisis() {
    	//当前解析的位置
    	int index=0;
    	this.method=data.substring(index,index=data.indexOf(BLACK));
    	index++;
    	//处理URL和后面的  数据参数
    	if(this.method.equalsIgnoreCase("post")) {
    	    //post 的报文体在后面	//最后需要判断
    	    this.URL=data.substring(index, index=data.indexOf(BLACK,index));
    	}else if(this.method.equalsIgnoreCase("get")) {
    	    //get 的报文体在url后面
    	    String str=data.substring(index,index=data.indexOf(BLACK,index));
    	    if(str.contains("?")) {//包含问号相当于  存在报文体
    	    	this.URL=str.substring(0,str.indexOf("?"));//获得url地址
    			//设计一个函数  获取报文体
    			parseMSGBody(str.substring(str.indexOf("?")+1));
    	    }else {
    	    	this.URL=str;
    	    }
    	}	
    	//解析  版本
    	this.version=data.substring(index+1,index=data.indexOf(CRLF,index));
    	index++;
    	//解析报文
    	parseMsgHead(data.substring(index));	
	}
	
  //解析报文
    private void parseMsgHead(String str) {
    	int index=0;
    	msgHead=new HashMap<String,String>();
    	while(index<str.length()-2) {  	    
    	    String key=str.substring(index,index=str.indexOf(":",index));
    	    String value=str.substring(index+2,index=str.indexOf(CRLF,index));
    	    msgHead.put(key, value);
    	    index+=2;
    	}
    }
	
    // 解析出报文体
    private void parseMSGBody(String str) {
      	//实例化 map
      	msgBody=new HashMap<String,String>();
      	//解析的位置  可以分割字符串
      	String strArray[]=str.split("&");
      	for(int i=0;i<strArray.length;i++) {
      	    String key=strArray[i].substring(0,strArray[i].indexOf("="));
      	    String value=strArray[i].substring(strArray[i].indexOf("=")+1);
      	    msgBody.put(key,value);
      	}
    }
    
	//接受数据    这里不能循环读  会造成死循环    只要不断开连接  就一直循环
	private void recv() {
	    byte[] flush=new byte[1024*1024];
	    int len=0;
		try {
			len=is.read(flush);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	    data=new String(flush,0,len);
	    analisis();
	}
	
	public String getURL() {
		return URL;
	}
}



/**用来进行解码中文字符串
public String decode(String value,String code) {
	try{    	   
	    return java.net.URLDecoder.decode(value,code);
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}    	
	return null;
}
*/