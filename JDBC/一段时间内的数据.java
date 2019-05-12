package Test02;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;

//去除指定时间段的内容
public class Test{
	public static long DateFormat2Long(String dateFormat) {
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			return format.parse(dateFormat).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
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
			String sql="select * from t_user where regtime>? and regtime<?";
			ps=con.prepareStatement(sql);
			ps.setTimestamp(1, new Timestamp(DateFormat2Long("2019-05-11 21:00:00")));
			ps.setTimestamp(2,new Timestamp(DateFormat2Long("2019-05-12 3:00:00")));
			
			rs=ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"--"+rs.getString(2)+"--"+rs.getString(3)+"--"+
						rs.getTimestamp(4));
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
