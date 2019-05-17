package SORM;
/**
 * 管理配置信息(方便更改配置文件  作为一个中介类)
 * @author Raymond-du
 *
 */


public class Configuration {
	//驱动类
	private String driver;
	//jdbc的url
	private String url;
	//数据库的用户名
	private String user;
	//数据库密码
	private String pwd;
	//正在使用的数据库
	private String usingDB;
	//项目的源码路径
	private String srcPath;
	//扫描生成java类的包(po Persistance Object持久化对象)
	private String poPackage;
	public Configuration() {}
	
	
	public Configuration(String driver, String url, String user, String pwd, String usingDB, String srcPath,
			String poPackage) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.usingDB = usingDB;
		this.srcPath = srcPath;
		this.poPackage = poPackage;
	}


	public String getDriver() {
		return driver;
	}
	public String getUrl() {
		return url;
	}
	public String getUser() {
		return user;
	}
	public String getPwd() {
		return pwd;
	}
	public String getUsingDB() {
		return usingDB;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public String getPoPackage() {
		return poPackage;
	}
	
}
