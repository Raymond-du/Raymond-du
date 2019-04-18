package Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
//先构建响应头
public class Respond {
	public static String CRLF="\r\n";
	public static String BLANK=" ";	

	//正文
	private StringBuilder content;
	private BufferedWriter bw=null;
	//协议头（状态行与请求头 回车）信息
	private StringBuilder headInfo;
	private int len; //正文的字节数
	
	public Respond(OutputStream os) {
		super();
		content=new StringBuilder();
		headInfo=new StringBuilder();
		this.bw =new BufferedWriter(new OutputStreamWriter(os)) ;
	}
	
	//动态添加内容
	public	Respond print(String info) {
		content.append(info);
		len+=info.getBytes().length;
		return this;
	}

	//推送响应信息
		public void pushToBrowser(int code) throws IOException {
			if(null ==headInfo) {
				code = 505;
			}
			createHeadInfo(code);
			bw.append(headInfo);
			bw.append(content);
			bw.flush();
		}
	//构建响应头
	private void createHeadInfo(int code) {
		//1、响应行: HTTP/1.1 200 OK
		headInfo.append("HTTP/1.1").append(BLANK);
		headInfo.append(code).append(BLANK);
		switch(code) {
			case 200:
				headInfo.append("OK").append(CRLF);
				break;
			case 404:
				headInfo.append("NOT FOUND").append(CRLF);
				break;	
			case 505:
				headInfo.append("SERVER ERROR").append(CRLF);
				break;	
		}
		//2、响应头(最后一行存在空行):
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Server:").append("shsxt Server/0.0.1;charset=GBK").append(CRLF);
		headInfo.append("Content-type:text/html").append(CRLF);
		headInfo.append("Content-length:").append(len).append(CRLF);
		headInfo.append(CRLF);		
	}
}
