package SORM;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import SORM.DBconnectionPool;
import SORM.JDBCUtil;
import SORM.ReflectUtil;
import SORM.TableContext;
import SORM.TableInfo;

/**
 * 负责查询(对外提供服务的核心类)
 * @author Raymond-du
 *
 */
@SuppressWarnings("all")
public interface Query {
	/**
	 * 直接执行dml语句(增删改查语句 对数据库的操作语句)
	 * @param sql sql语句
	 * @param params sql语句的参数
	 * @return 执行sql语句影响记录的行数
	 */
	public int executeDML(String sql,Object[] params);
	/**
	 * 将一个对象直接储存到数据库中
	 * @param obj  要存储的对象
	 * @hasMainKey  是否写入主键  不写入则  自动增加主键
	 */
	public void insert(Object obj);
	
	/**
	 * 执行删除操作
	 * @param clazz 数据库表对象
	 * @param keyValue  主键的值
	 */
	public void delete(TableInfo table, Object keyValue);
	/**
	 * 删除对象在数据库中对应的记录(对象所在的类对应到表,对象的主键的值对应到记录)
	 * @param obj
	 */
	public void delete(Object obj);
	/**
	 * 更新对象对应的记录,并且只更新指定的字段的值
	 * @param obj  需要更新的对象
	 * @param fieldNames 更新的属性列表
	 * @return 影响的行数
	 */
	public int update(Object obj,String [] fieldNames);
	/**
	 * 查询返回多行记录,并将记录封装到clazz指定的类的对象
	 * @param sql 查询语句
	 * @param clazz 封装数据的javabean类的Class对象
	 * @param params sql的参数
	 * @return 查询到的结果
	 */
	public List queryRows(String sql,Class clazz,Object[] params);
	/**
	 * 执行查找指定对象
	 * @param sql 查询语句
	 * @param params 参数
	 * @return 查询的结果
	 */
	public Object queryValue(String sql,Object[] params);
	/**
	 * 查询返回一个值(一行一值) ,并将改制返回
	 * @param sql 查询语句
	 * @param params sql的参数
	 * @return 查询的结果
	 */
	public Object queryUniqueRow(String sql,Class clazz,Object[] params);
	/**
	 * 查询一个数值 并将返回
	 * @param sql 查询语句
	 * @param params sql的参数
	 * @return 查询的数值
	 */
	public Number queryNumber(String sql,Object[] params);	
}
@SuppressWarnings("all")
class MysqlQuery implements Query{
	private DBconnectionPool conPool=new DBconnectionPool();
	//这里如果是CLOb和BlOb  就不能使用setOBject
	@Override
	public int executeDML(String sql, Object[] params) {
		Connection con=conPool.getCon();
		PreparedStatement ps=null;
		try {
			ps= con.prepareStatement(sql);
			for(int i=0;i<params.length;i++) {
				if(params[i] instanceof Reader) {
					ps.setClob(i+1, (Reader)params[i]);
					continue;
				}else if(params[i] instanceof InputStream) {
					ps.setBlob(i+1,(InputStream)params[i]);
				}
				ps.setObject(i+1, params[i]);
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}finally {
			conPool.close(con);
			JDBCUtil.close(ps);
		}
	}
	//将一个对象存储到数据库中	object必须是t_user对象
	//把对象中部位null的示行存储到数据库中  
	public void insert(Object obj) {
		//insert into 表名(id,username,) value(?,?,?)
		TableInfo table=TableContext.getPoClassTables().get(obj.getClass());
		//存储 field对象的list
		List<Object> fieldList=new ArrayList<>();
		//DML语句
		StringBuilder sql=new StringBuilder("insert into "+table.gettName()+" (");
		//获取类中的属性列表  这里把控也放入里面
		Field[] fields=obj.getClass().getDeclaredFields();
		for(Field field:fields) {
			String fieldName=field.getName();
			Object o=ReflectUtil.invokeGet(fieldName, obj);
			if(o!=null) {
				sql.append(fieldName+",");
				fieldList.add(o);
			}
		}
		sql.setCharAt(sql.length()-1, ')');
		sql.append(" value (");
		for(int i=0;i<fieldList.size();i++) {
			sql.append("?,");
		}
		sql.setCharAt(sql.length()-1, ')');
		System.out.println(sql.toString());
		executeDML(sql.toString(), fieldList.toArray());
	}
	//生成一个mysql的删除语句  clazz是对应的表TableInfo   的主键
	public void delete(TableInfo table, Object keyValue) {
		/** 这里将主键的名限定为  id
		//删除语句 delete from t_user where id=?
		String sql="delete from "+clazz.getName()+" where id=?";
		executeDML(sql,new Object{id});
		*/
		//在poClassTables里找到clazz的映射
		String sql="delete from "+table.gettName()+" where "+table.getOnlyPriKey().getName()+"=?" ;
		executeDML(sql, new Object[] {keyValue});
	}

	//obj是删除记录的对象
	public void delete(Object obj) {
		//获取tableInfo的信息
		TableInfo table=TableContext.getPoClassTables().get(obj.getClass());
		
		Object keyValue=ReflectUtil.invokeGet(table.getOnlyPriKey().getName(), obj);
		delete(table,keyValue);
	}
	//需要更新的field的name
	public int update(Object obj, String[] fieldNames) {
		//update 表名 set field名=value,field名=value where key=**
		TableInfo table=TableContext.getPoClassTables().get(obj.getClass());
		List<Object>fieldList=new ArrayList<>();
		StringBuilder sql=new StringBuilder("update "+table.gettName()+" set ");
		for(String fieldName:fieldNames) {
			sql.append(fieldName+"=?,");
			fieldList.add(ReflectUtil.invokeGet(fieldName, obj));
		}
		sql.setCharAt(sql.length()-1, ' ');
		//获取主键对应的value
		sql.append("where "+table.getOnlyPriKey().getName()+"="
				+ReflectUtil.invokeGet(table.getOnlyPriKey().getName(),obj));
		System.out.println(sql.toString());
		return executeDML(sql.toString(), fieldList.toArray());
	}
	@Override
	public List queryRows(String sql, Class clazz, Object[] params) {
		Connection con=conPool.getCon();
		List list=null;
		ResultSet rs=null;
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			for(int i=0;i<params.length;i++) {
				if(params[i] instanceof Reader) {
					ps.setClob(i+1, (Reader)params[i]);
					continue;
				}else if(params[i] instanceof InputStream) {
					ps.setBlob(i+1,(InputStream)params[i]);
				}
				ps.setObject(i+1, params[i]);
			}
			rs=ps.executeQuery();
			//这是获取列信息
			ResultSetMetaData metaDate=rs.getMetaData();
			
			while(rs.next()){
				if(list==null) {
					list=new ArrayList<>();
				}
				//创建一个表成员对象
				Object rowObj=clazz.newInstance();
				//列数 (id username pwd 等等)
				for(int i=0;i<metaDate.getColumnCount();i++) {
					String columnName=metaDate.getColumnLabel(i+1);
					//返回的是一个int 对应类型的值
					int type=metaDate.getColumnType(i+1);
					Object columnValue=MysqlTypeConvertor.getMySqlbject(rs, type, i+1);
					//利用反射 添加field
					if(columnValue!=null) {
						ReflectUtil.invokeSet(rowObj, columnName, columnValue);
					}
				}
				list.add(rowObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			conPool.close(con);
			JDBCUtil.close(rs);
		}
		
		return list;
	}

	@Override
	public Object queryUniqueRow(String sql,Class clazz, Object[] params) {
		List list=queryRows(sql, clazz,params);
		return (list!=null&&list.size()>0)?list.get(0):null;
	}

	public Object queryValue(String sql,Object[] params) {
		Connection con=conPool.getCon();
		Object value=null;
		ResultSet rs=null;
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			for(int i=0;i<params.length;i++) {
				if(params[i] instanceof InputStream) {
					ps.setBlob(i+1,(InputStream)params[i]);
					continue;
				}
				ps.setObject(i+1, params[i]);
			}
			rs=ps.executeQuery();
			//这是获取列信息		
			
			while(rs.next()){				
				value=rs.getObject(1);
				return value;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			conPool.close(con);
			JDBCUtil.close(rs);
		}
		
		return value;
	
	}
	@Override
	public Number queryNumber(String sql, Object[] params) {
		return (Number)queryValue(sql, params);
	}
	//关闭所有的connection
	public void closeCon() {
		conPool.closeAll();
	}
	
}
