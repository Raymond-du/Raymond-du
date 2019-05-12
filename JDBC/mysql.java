package Test02;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Connection;
/**
 * 先将mysql的jar包链接上
 * 
 * Statment的函数 execute是执行sql语句
 * executeQuery运行select语句返回ResultSet结果集
 * executeUpdate运行insert,delete/update操作,返回更新的行数
 * @author Raymond-du
 *
 */

public class Test{
	public static void main(String[] args){
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			//装载mysql的驱动
			Class.forName("com.mysql.jdbc.Driver");
			//DriverManager是sun公司提供的接口   连接数据库     连理连接比较耗时(开发中使用连接池)
			//返回了一个com.mysql.jdbc.JDBC4Connection实现类  接口时java.sql.Connection
			//每个数据库的连接方式不同  mysql(jdbc:mysql://ip地址:端口号/数据库名,账号,密码);
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","697193du");
			System.out.println(con==null?"连接失败":"连接成功");
			
			/**
			//执行sql语句的操作(一般不用  容易发生sql注入漏洞)
			Statement stat= con.createStatement();
			
			//sql插入语句
			//String sql1="insert into t_user(username,pwd,regtime) value ('赵四',123,now())";
			//stat.execute(sql1);
			
			stat.close();*/
			
			/**
			//测试prepareStatment常用  安全 
			String sql="insert into t_user (username,pwd,regtime) value (?,?,now())";//  ?占位符避免sql的注入
			//这个也是执行sql语句的方法
			PreparedStatement pstat=con.prepareStatement(sql); 
			pstat.setString(1,"gaoqi");//参数的索引   从1开始
			pstat.setObject(2,"2344");
			
			pstat.execute();
			pstat.close();
			
			//更新的行数
			int count=pstat.executeUpdate();*/
			String sql="select id,username,pwd,regtime from t_user where username=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, "gaoqi");
			rs=ps.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String username=rs.getString("username");
				String pwd=rs.getString("pwd");
				Time regtime=rs.getTime("regtime");
				System.out.println(id+"\t"+username+"\t"+pwd+"\t"+regtime);
			}
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
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
