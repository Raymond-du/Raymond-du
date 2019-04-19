package Server;

import java.io.IOException;
import java.net.Socket;

/**
 * 调度
 * @author 26368
 *
 */
public class Dispatcher implements Runnable{
	private WebContent content=null;
	private Request req=null;
	private Respond rep=null;	
	
	public Dispatcher(Socket client,WebContent content) throws IOException {
		super();
		this.content=content;
		this.req=new Request(client);
		this.rep=new Respond(client.getOutputStream());
	}

	@Override
	public void run() {
		//如果是url为空时 直接发送index.html
		try {
    		if(null==req.getURL()||req.getURL().equals("\\")) {
    			rep.print("welcome");
    			rep.pushToBrowser(200);
    		}
    		Servlet servlet=content.getServlet(req.getURL());
    		if(null!=servlet) {
    			servlet.server(rep);
    		}else {
    			rep.print("not found interface");
    			rep.pushToBrowser(400);
    			System.out.println("url flaut");
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
