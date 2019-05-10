package Test02;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//使用正则表达式(regular Expression)
/**
 * \n(代表换行符)  \t(制表符  tab)  \\(代表'\')  (\+特使字符)表示特使字符本身
 * 
 * 标准字符集合
 * \大写的字母有着与小写字母相反的含义
 * \d任意一个数字   \D 大写字母D有取反的含义  (任意一个不是数字的字符)
 * \w任意一个字母或数字或下划线,也就是A~B a~z 0~9 ,_中的任意一个
 * \s 包括空格 制表符 换行符等空白的字符 的其他任意一个
 * .  小数点可以匹配任意一个字符(换行符除外)   [\s\S]表示可以匹配任意字符
 * 
 * 自定义字符集合 (看成一个集合 他们时或的关系)
 * []方括号 能够匹配方括号中的任意一个字符  
 * 例如 [abc43]  可以匹配a b c 4 3这几个字符
 * [^abc]  可以匹配出a b c外的任意一个字符
 * [f-k]  可以匹配f~k之间的任意字符
 * [^f-k0-3]可以匹配除f~k和0~3外的任意一个字符
 * 
 * 正则表达式的特殊符号,被包含到中括号中,则失去了特殊意义,除了^ -
 * 想要使用特殊意义的特殊字符需要在特殊字符前加'\'  小数点除外
 * 
 * 量词(修饰匹配次数的特殊符号)
 * {n}表达式重复n次  \d{6}表示匹配数字重复6次 ()小括号表示一组
 * {n,m}表示匹配重复最少n次  最多匹配m次    (贪婪模式)先匹配最多的 再匹配最少的 
 * {n,m}? (非贪婪模式)先匹配最少的 再匹配最多的
 * ? 表示{0,1}  0-1次
 * + 表示最少一次
 * * 表示最少0次   匹配任意次
 * 
 * 字符边界
 * ^ 定位到字符串的开头匹配的是位置不是字符
 * $ 定位到字符串的结束的位置
 * \b 定位字符的边界   \b放到匹配字符的前面 表示匹配字符前面是\W的
 * 	\b放到匹配字符的后面 表示匹配字符后面是\W的
 * 
 * 选择符和分组
 * | 表示或的关系  a|b表示匹配a或b字符
 * () (1)再被修饰匹配次数的时候,括号中的表达式可以作为整体被修饰
 * (2)取匹配结果的时候,括号中的表达式匹配到的内容可以被单独的到
 * (3)每一对括号会分配一个编号,使用()的捕获根据左括号的顺序从1开始自动编号,捕获元素编号为
 * 	零的第一个捕获是由整个正则表达式模式匹配的文本;
 * ([a-z]{2})\1  这里先把([a-z]{2})分组 编号为1   \1表示匹配组1 以左括号为准  例如gigi
 * (?: ([a-z]{2})) 不会保存括号内的信息  只是用来匹配的
 * 
 * 预搜索(零宽断言)  对位置的匹配
 * (?= exp)exp是表达式  定位到字符串(和exp匹配)的最前字符的位置
 * ;例如 : eating swiming going  [a-z]+(?= img) 匹配的结果是eat swim go
 *  (?<= exp)exp是表达式  定位到字符串(和exp匹配)的最后字符的位置                       
 *  ;例如 : eating swiming going  [a-z]+(?<= img) 匹配的结果是eating swiming going 
 * (?! exp)定位到字符串(和exp不匹配)的最前面字符串的位置
 * (?<! exp)定位到字符串(和exp不匹配)的最后面字符串的位置
 * 
 * 固话的匹配 \b(0\d{2,3}-\d{7,8})\b 区号是3-4位 号码是7-8位 \b不能有数字字母_
 * 手机号的匹配 \b(1[35789][0-9]{9})\b  
 * 邮箱地址的匹配\b[0-9a-z]+@[a-z]+\.com(\.cn){0,1}\b
 * 
 * 匹配中文字符 [\u4e00-\u9fa5]
 * @author Raymond-du
 * 
 */
		
public class Test{
	public static void main(String[] args) {
		//表达式对象
		Pattern pattern =Pattern.compile("\\b(0\\d{2,3}-\\d{7,8})\\b");
		//执行匹配操作的引擎
		Matcher matcher= pattern.matcher("0354-5864483 010-5772666 12345-67882133");
		//判断整个字符串是否匹配
		//boolean result=matcher.matches();
		//依次查找符合表达式的字符串
		while(matcher.find()) {
			//这个不能单独使用需要调用find后 才可以获取
			String str=matcher.group();
			System.out.println(str);
		}		
		
		Scanner sc=new Scanner(System.in);
		while(true) {
			String str=sc.nextLine();
			if(str.equals("")) {
				break;
			}
			matcher.reset(str);
			System.out.println("您输入的字符串"+str+"匹配结果"+matcher.matches());		
		}
		sc.close();
	}
}
