package Server;
import java.io.IOException;

//返回注册
public class RegServlet extends Servlet{


	@Override
	public void doGet(Respond rep) {		
		try {
			rep.print("registered interface");
			rep.pushToBrowser(200);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void doPost(Respond rep) {		
	}

}
