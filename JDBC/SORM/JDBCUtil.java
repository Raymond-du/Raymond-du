package SORM;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 封装了JDBC查询常用的操作
 * @author Raymond-du
 *
 */

public class JDBCUtil{
	//私有构造器
	private JDBCUtil() {}
	//关闭 流对象
	public static void close(Object... objs) {
		try {
			for(Object obj:objs) {
				if(obj!=null) {
            		if(obj instanceof Connection) {
            			((Connection)obj).close();
            		}else if(obj instanceof Statement) {
            			((Statement)obj).close();
            		}else if(obj instanceof InputStream) {
            			((InputStream)obj).close();
            		}else if(obj instanceof OutputStream) {
            			((OutputStream)obj).close();
            		}else if(obj instanceof ResultSet) {
            			((ResultSet)obj).close();
            		}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
