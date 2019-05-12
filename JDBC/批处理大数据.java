package Test02;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

//当数据量大的时候 不能用prepareStatment预编译的空间有限  大量数据提交时容易发生异常
//批处理大数据
public class Test{
	public static void main(String[] args){
		Connection con=null;
		Statement stat=null;
		ResultSet rs=null;
		try {
			//装载mysql的驱动
			Class.forName("com.mysql.jdbc.Driver");
			//DriverManager是sun公司提供的接口   连接数据库     连理连接比较耗时(开发中使用连接池)
			//返回了一个com.mysql.jdbc.JDBC4Connection实现类  接口时java.sql.Connection
			//每个数据库的连接方式不同  mysql(jdbc:mysql://ip地址:端口号/数据库名,账号,密码);
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","697193du");
			System.out.println(con==null?"连接失败":"连接成功");
			
			con.setAutoCommit(false);//设置为手动提交
			stat=con.createStatement();
			long start=System.currentTimeMillis();
			//插入20000条数据
			for(int i=0;i<20000;i++) {
				stat.addBatch("insert into t_user (username,pwd,regtime) "
						+ "value ('du"+i+"',123456,now())");//批处理
			}
			stat.executeBatch();
			con.commit();
			long end=System.currentTimeMillis();
			System.out.println(end-start);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//这里三个分开写  否则其中一个发生异常  影响其他的关闭
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stat!=null) {
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
