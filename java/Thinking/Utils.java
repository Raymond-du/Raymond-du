package Thinking;

import java.util.Arrays;

/**
 * 自定义一个工具类  实现泛型排序
 * @author 26368
 * @param <T>
 *从小到大排序	
 *
 *在使用该方法时需要  类型T继承自Comparable接口  并且重写compareTo方法
 *
 *也可以容器排序  可以将容器转成数组
 */
public class Utils<T> {
    private Utils() {}
    public static <T extends Comparable>void sort(T t[]){
	boolean sorted=true;
	for(int i=0;i<t.length-1;i++) {
	    for(int j=0;j<t.length-i-1;j++) {		
		if(0<((Comparable<T>)t[j]).compareTo(t[j+1])) {
		    T temp=t[j];
		    t[j]=t[j+1];
		    t[j+1]=temp;
		    sorted=false;
		}
	    }
	    if(sorted) {
		break;
	    }
	}
    }
    public static void main(String[] args) {
	String s[]= {"das","dawq","daw","fsa","dfg"};
	Utils.sort(s);
	System.out.println(Arrays.toString(s));
    }
}
