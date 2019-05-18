package SORM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import SORM.Configuration;

/**
 * 根据配置信息维持对象的管理(增加连接池的功能)
 * @author Raymond-du
 *
 */
		
public class DBManager {
	//配置文件信息
	private static Configuration conf;
	static {//静态代码
		//加载资源文件
		Properties pros=new Properties();
		try {
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		conf=new Configuration(pros.getProperty("driver"), pros.getProperty("url"), pros.getProperty("user"),
				pros.getProperty("pwd"), pros.getProperty("usingDB"), pros.getProperty("srcPath"), 
				pros.getProperty("poPackage"));
	}
	//获取连接  目前直接建立连接后期添加连接池的处理,提高效率
	public static Connection getConnection() {
		try {
			Class.forName(conf.getDriver());
			return DriverManager.getConnection(conf.getUrl(),conf.getUser(),conf.getPwd());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	public static Configuration getConfiguration() {
		return conf;
	}
}
