package Test02;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;

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
			con=JDBCUtil.getConnection();
			System.out.println(con==null?"连接失败":"连接成功");
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
			JDBCUtil.close(is,con,rs,ps);
					
		}
	}	
}
