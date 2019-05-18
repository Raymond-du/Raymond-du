package SORM;

import java.lang.reflect.Method;

import SORM.StringUtils;

/**
 * 封装了反射常用的操作
 * @author Raymond-du
 *
 */
@SuppressWarnings("all")
public class ReflectUtil {
	//利用反射获取需要get的对象
	/**
	 * 调用obj对象对应属性的fieldName的get方法
	 * @param clz 
	 * @param strField
	 * @param obj
	 * @return
	 */
	public static Object invokeGet(String strField,Object obj) {
		try {
			Method method=obj.getClass().getDeclaredMethod("get"+StringUtils.FirstChar2Uppercase(strField), null);
			return method.invoke(obj, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		
	}
	//实现set方法的反射 obj 执行的对象 fieldName属性的名 param 参数
	//这里数据库中的text 装换CLOB错误     BLOB装换也可能发生错误
	public static void invokeSet(Object obj,String fieldName,Object param) {
		try {
			Method method=obj.getClass().getDeclaredMethod("set"+StringUtils.FirstChar2Uppercase(fieldName), param.getClass());
			method.invoke(obj, param);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
