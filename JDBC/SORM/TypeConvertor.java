package SORM;

import java.sql.ResultSet;
import java.sql.Types;

/**
 * 负责java类型的数据类型和数据路类型的互相转换
 * @author Raymond-du
 *
 */
public interface TypeConvertor {
	/**
	 * 将数据库数据类型转化成java的数据类型
	 * @param columnType 数据库字段的数据类型
	 * @return java 的数据类型
	 */
	public String datebaseType2JavaType(String columnType);
	/**
	 * 将java数据类型转化成数据库的数据类型
	 * @param javaDateType java的数据类型
	 * @return 数据库的数据类型
	 */
	public String javaType2DatebaseType(String javaDateType);
}

class MysqlTypeConvertor implements TypeConvertor{

	//这里的text 返回LongVarChar  BLob返回LONGVARBINARY
	public static Object getMySqlbject(ResultSet rs,int type,int index) {
		try {
    		switch(type) {
    		case Types.ARRAY:
    			return rs.getArray(index);
    		case Types.LONGVARCHAR:
    			return rs.getAsciiStream(index);
    		case Types.NUMERIC:
    			return rs.getBigDecimal(index);
    		case Types.LONGVARBINARY:
    			return rs.getBinaryStream(index);
    		case Types.SMALLINT:
    			return rs.getShort(index);
    		case Types.BIGINT:
    			return rs.getLong(index);
    		case Types.BOOLEAN:
    			return rs.getBoolean(index);
    		case Types.DATE:
    			return rs.getDate(index);
    		case Types.DOUBLE:
    			return rs.getDouble(index);
    		case Types.FLOAT:
    			return rs.getFloat(index);
    		case Types.INTEGER:
    			return rs.getInt(index);
    		case Types.TIME:
    			return rs.getTime(index);
    		case Types.TIMESTAMP:
    			return rs.getTimestamp(index);
    		case Types.TINYINT:
    			return rs.getByte(index);
    		case Types.NULL:
    			return null;
    		case Types.VARCHAR:
    			return rs.getString(index);
    		default:
    			return rs.getObject(index);
    		}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public String datebaseType2JavaType(String columnType) {
		if("varchar".equalsIgnoreCase(columnType)||"char".equalsIgnoreCase(columnType)) {
			return "String";
		}else if("int".equalsIgnoreCase(columnType)||
				"tinyint".equalsIgnoreCase(columnType)||
				"smallint".equalsIgnoreCase(columnType)||
				"integer".equalsIgnoreCase(columnType)) {
			return "Integer";
		}else if("bigint".equalsIgnoreCase(columnType)){
			return "long";
		}else if("double".equalsIgnoreCase(columnType)){
			return "Double";
		}else if("float".equalsIgnoreCase(columnType)){
			return "Float";
		}else if("blob".equalsIgnoreCase(columnType)){
			return "java.io.ByteArrayInputStream";
		}else if("time".equalsIgnoreCase(columnType)){
			return "java.sql.Time";
		}else if("timestamp".equalsIgnoreCase(columnType)){
			return "java.sql.Timestamp";
		}else if("date".equalsIgnoreCase(columnType)){
			return "java.sql.Date";
		}else if("clob".equalsIgnoreCase(columnType)) {
			return "java.io.ByteArrayInputStream";
		}else if("text".equalsIgnoreCase(columnType)) {
			return "java.io.ByteArrayInputStream";
		}
		return "Object";
	}

	@Override
	public String javaType2DatebaseType(String javaDateType) {
		if("String".equalsIgnoreCase(javaDateType)) {
			return "varchar";
		}else if("Integer".equalsIgnoreCase(javaDateType)) {
			return "int";
		}else if("long".equalsIgnoreCase(javaDateType)){
			return "bigint";
		}else if("Double".equalsIgnoreCase(javaDateType)){
			return "double";
		}else if("Float".equalsIgnoreCase(javaDateType)){
			return "float";
		}else if("java.io.Reader".equalsIgnoreCase(javaDateType)){
			return "text";
		}else if("java.io.InputStream".equalsIgnoreCase(javaDateType)){
			return "blob";
		}else if("java.sql.Time".equalsIgnoreCase(javaDateType)){
			return "time";
		}else if("java.sql.Timestamp".equalsIgnoreCase(javaDateType)){
			return "timestamp";
		}else if("java.sql.Date".equalsIgnoreCase(javaDateType)){
			return "date";
		}	
		return null;
	}
	
}