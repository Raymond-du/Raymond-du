package SORM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import SORM.ColumnInfo;
import SORM.DBManager;
import SORM.JDBCUtil;
import SORM.JavaFieldGetSet;
import SORM.StringUtils;
import SORM.TableInfo;
import SORM.TypeConvertor;

/**
 * 封装了生成java文件(源代码)常用操作
 * @author Raymond-du
 *
 */
public class JavaFileUtil {
	//表示空白字符
	public static final String BLACK=" ";
	//表示分隔符(分号)
	public static final String SEMICOLON=";";
	/**
	 * 从数据库获取的字段信息 生成一个源代码
	 * @param ci  数据库的字段信息
	 * @param convertor 将数据库的类型装换为java数据类型
	 * @return  返回一个源代码的类
	 */
	public static JavaFieldGetSet createFieldSourceInfo(ColumnInfo ci,TypeConvertor convertor) {
		String javaType=convertor.datebaseType2JavaType(ci.getdataType());
		JavaFieldGetSet jfgs=new JavaFieldGetSet();
		//public 类型  名称
		jfgs.setFieldSource("\tprivate "+javaType+BLACK+ci.getName()+SEMICOLON);
		//类型 get名称 (){}
		jfgs.setGetFieldSource("\t"+javaType+BLACK+"get"+StringUtils.FirstChar2Uppercase(ci.getName())
				+"(){\n\t\treturn"+BLACK+ci.getName()+SEMICOLON+"\n\t}");
		//void set名称 (类型 名称){}
		jfgs.setSetFieldSource("\tvoid"+BLACK+"set"+StringUtils.FirstChar2Uppercase(ci.getName())+"("+
				javaType+BLACK+ci.getName()+"){"+"\n\t\tthis."+ci.getName()+"="+ci.getName()+SEMICOLON+"\n\t}");		
		return jfgs;
	}
	/**
	 * 根据表的信息(各个属性)生成一个对应的类
	 * @param tableInfo	表信息
	 * @param convertor	类型装换器(将数据库类型装换为java数据类型)
	 * @return 返回生成的一个类对象
	 */
	public static String createJavaSrc(TableInfo tableInfo,TypeConvertor convertor) {
		Map<String, ColumnInfo> columns= tableInfo.getColums();
		List<JavaFieldGetSet> javaFields=new ArrayList<>();
		for(ColumnInfo column:columns.values()) {
			javaFields.add(createFieldSourceInfo(column, convertor));
		}
		StringBuilder javaSrc=new StringBuilder();
		//package包
		javaSrc.append("package "+DBManager.getConfiguration().getPoPackage()+";\n");
		//import 语句 
		//添加类声明语句
		javaSrc.append("public class "+StringUtils.FirstChar2Uppercase(tableInfo.gettName()+"{\n"));
		//添加属性列表
		for(JavaFieldGetSet fieldSrc:javaFields) {
			javaSrc.append(fieldSrc.getFieldSource()+"\n");
		}
		//添加get set方法
		for(JavaFieldGetSet fieldSrc:javaFields) {
			javaSrc.append("\tpublic"+fieldSrc.getGetFieldSource()+"\n");
			javaSrc.append("\tpublic"+fieldSrc.getSetFieldSource()+"\n");
		}
		//结尾的大括号
		javaSrc.append("}");
		return javaSrc.toString();
	}
	
	public static void createJavaFile(TableInfo table,TypeConvertor convertor) {
		String src=createJavaSrc(table, convertor);
		//这里是正则表达式
		String destDir=DBManager.getConfiguration().getSrcPath()+"/"+DBManager.getConfiguration().
				getPoPackage().replaceAll("\\.", "/");
		String destPath=destDir+"/"+StringUtils.FirstChar2Uppercase(table.gettName())+".java";
		File dir=new File(destDir);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		BufferedWriter bw=null;
		try {
			bw=new BufferedWriter(new FileWriter(destPath));
			bw.write(src);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(bw);
		}
	}	
	
}
