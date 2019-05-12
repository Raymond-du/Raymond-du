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

import javafx.beans.binding.BooleanBinding;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;

//实现sql大文本的数据保存读取和写入 CLOB  字符串写入  BLOB  二进制文本写入
//sql添加text文本  可以写入6万多个字符  
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
		InputStream is=null;
		try {
			//装载mysql的驱动
			Class.forName("com.mysql.jdbc.Driver");
			//DriverManager是sun公司提供的接口   连接数据库     连理连接比较耗时(开发中使用连接池)
			//返回了一个com.mysql.jdbc.JDBC4Connection实现类  接口时java.sql.Connection
			//每个数据库的连接方式不同  mysql(jdbc:mysql://ip地址:端口号/数据库名,账号,密码);
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","697193du");
			System.out.println(con==null?"连接失败":"连接成功");
			/**
			String sql="insert into t_user (username,pwd,regtime,file) value ('dushow','32',now(),?)";
			ps=con.prepareStatement(sql);
			ps.setBlob(1, new BufferedInputStream(new FileInputStream(
					"C:\\Users\\26368\\Desktop\\android studio/android.txt")));
			ps.executeUpdate();*/
			String sql="select * from t_user where id=20008";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				Blob b=rs.getBlob(6);
				is=b.getBinaryStream();
				byte[] buf=new byte[1024];
				int temp=0;
				while(-1!=(temp=is.read(buf))) {
					System.out.println(new String(buf,0,temp));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
