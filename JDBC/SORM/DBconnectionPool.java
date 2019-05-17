package SORM;

import java.sql.Connection;
import java.util.List;

/**
 * 连接池  提高效率
 * @author 26368
 *
 */
public class DBconnectionPool {
	//连接池list
	private List<Connection> conList=null;
	
	public static final int MAX_CON=10;
	public DBconnectionPool() {
		while(conList.size()<MAX_CON) {
			conList.add(DBManager.getConnection());
		}
	}
	
	public synchronized Connection getCon() {
		if(conList.size()<1) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return conList.remove(conList.size()-1);
	}
	
	public synchronized void close(Connection con) {
		if(MAX_CON>conList.size()) {
			conList.add(con);
			notify();
			return ;
		}
		JDBCUtil.close(con);
	}
	
	public void closeAll() {
		for(Connection con:conList) {
			JDBCUtil.close(con);
		}
	}
}
