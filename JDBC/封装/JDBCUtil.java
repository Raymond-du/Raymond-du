package Test02;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

//分装jdbc

public class JDBCUtil{
	enum Command{
		INSERT,
		DELETE,
		SELECT,
		UPDATE;
	}
	static Properties pros=null;
	static {
		//新建文件解析器
		pros=new Properties();
		try {
			//加载文件
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static Connection getConnection() {
		try {
			//读取资源文件
			
			//先加载mysql的jar包
			Class.forName(pros.getProperty("mysqlDriver"));
			//连接mysql的数据库 从db.properties配置文件中获取信息
			return DriverManager.getConnection(pros.getProperty("mysqlUrl"),
					pros.getProperty("mysqlUser"),pros.getProperty("mysqlPwd"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//关闭 sql 相关的对象 
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
