package Server;

import java.io.IOException;

public class LogServlet extends Servlet{		
	
	@Override
	public void doGet(Respond rep) {
		rep.print("Login interface");
		try {
			rep.pushToBrowser(200);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void doPost(Respond rep) {
		
	}
}
