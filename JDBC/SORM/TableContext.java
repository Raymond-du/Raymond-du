package SORM;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 负责获取管理数据库所有表结构和类结构的关系,并可以根据表结构生成类结构
 * 获取所有的表(可能存在多个表) 添加到tables中
 * @author Raymond-du
 *
 */
public class TableContext {
	//表名为key,表信息为value
	private static Map<String,TableInfo> tables=new HashMap<>();
	//将po的class对象和表信息对象对应关联起来,便于重用
	private static Map<Class, TableInfo> poClassTableMap=new HashMap<>();
	
	private TableContext(){}
	
	static {
		try {
			//初始化表信息
			Connection con=DBManager.getConnection();
			//关于数据库的综合信息  可以获取数据库的详细信息(数据库的相关属性)
			DatabaseMetaData dbmd=con.getMetaData();
			/**
			 * 得到指定参数的表信息  1.目录名称,一般为null 2.数据库名(用户名) 3.表名称 4.表的类型
			 * % 多字符的通配符 _单个字符的通配符 
			 */
			ResultSet tableRs=dbmd.getTables(null, "%", "%", new String[] {"TABLE"});
			while(tableRs.next()) {				
				String tableName=tableRs.getString("TABLE_NAME");
				TableInfo ti=new TableInfo(tableName,new HashMap<String,ColumnInfo>(),
						new ArrayList<ColumnInfo>());
				tables.put(tableName, ti);
				
				//获取表中的所有Fields的名称
				ResultSet set=dbmd.getColumns(null, "%", tableName, "%");
				while(set.next()) {
					//表信息 的field 的名称和 对应的类型
					ColumnInfo ci=new ColumnInfo(set.getString("COLUMN_NAME"),
							set.getString("TYPE_NAME"),0);
					ti.getColums().put(set.getString("COLUMN_NAME"), ci);
				}
				//查询表中的主键
				ResultSet set2=dbmd.getPrimaryKeys(null, "%", tableName);
				while(set2.next()) {
					//上面已经把所有的field添加到了Tables中 只需要在tables中对应主键 并将其属性改为1
					ColumnInfo ci2=ti.getColums().get(set2.getString("COLUMN_NAME"));
					ci2.setKeyType(1);
					ti.getPriKey().add(ci2);
				}
				//去唯一主键 方便使用.如果是联合主键 则为空     这里先测试唯一主键
				if(ti.getPriKey().size()>0) {
					ti.setOnlyPriKey(ti.getPriKey().get(0));
				}
				updateJavaFile();
				//加载po类的class对象便于重用,提高效率
				loadPoTables();
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//获取string和TableInfo的map
	public static Map<String, TableInfo> getTableInfo(){
		return tables;
	}
	//获取Class和TableInfo的MAp
	public static Map<Class,TableInfo> getPoClassTables(){
		return poClassTableMap;
	}
	
	//跟新文件
	public static void updateJavaFile() {
		Map<String,TableInfo> tables=getTableInfo();
		for(TableInfo table:tables.values()) {
			JavaFileUtil.createJavaFile(table, new MysqlTypeConvertor());
		}
	}
	//加载po包的类 class和TableInfo对应起来  存储到poClassTableMap
	public static void loadPoTables() {
		for(TableInfo table:tables.values()) {
			try {
				Class clz=Class.forName(DBManager.getConfiguration().getPoPackage()+"."
						+StringUtils.FirstChar2Uppercase(table.gettName()));
				poClassTableMap.put(clz, table);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *根据表结构,跟新指定po包的po类结构
	 *根据配置文件中的,:srcPath=C:/Users/26368/eclipse-workspace/FIrst_Software/src
	public static void updataPO() {
		Map<String,TableInfo> tables=TableContext.getTableInfo();
		for(TableInfo t:tables.values()) {
			JavaSRCUtil.createJavaPOFlie(t,new MySqlTypeConvertor());
		}
	}*/
	
	
}
