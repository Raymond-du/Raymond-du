package test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 适配器模式(adapter)
 * 典型事例  Writer(OutputStreamWriter)将字节流转换成字符流
 * 目标接口(Write)  需要适配的类(OutputStream) 适配器类(OutputStreamWriter)
 * 两种方式实现  1.继承的方式 2.组合的方式
 * 
 * 测试date和Calendar的适配
 * @author Raymond-du
 *
 */
public class test{
	public static void main(String[] args) {
		/**
		Calendar calendar=new GregorianCalendar(1999,0,31);
		Date date=new CalendarDate(calendar);
		System.out.println(date);
		*/
		Calendar calendar=new GregorianCalendar(1999,0,31);
		CalendarToDate cTod=new CalendarToDate(calendar);
		System.out.println(cTod.getDate());
	}
}

//第一种方式继承方式   这里的缺点是java只能单继承 
class CalendarDate extends Date{
	public CalendarDate(Calendar calendar) {
		super(calendar.getTime().getTime());
	}
}
//第二种方式 组合方式
class CalendarToDate{
	private Date date=null;
	public CalendarToDate(Calendar calendar){
		this.date=calendar.getTime();
	}
	public Date getDate() {
		return this.date;
	}
}
