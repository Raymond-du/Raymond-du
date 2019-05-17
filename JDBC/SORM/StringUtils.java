package SORM;


/**
 * 封装字符串的处理操作s
 * @author Raymond-du
 *
 */
public class StringUtils {
	//私有构造器
	private StringUtils() {}
	//将第一个字符大写
	public static String FirstChar2Uppercase(String str) {
		return str.substring(0, 1).toUpperCase()+str.substring(1);
	}
}
