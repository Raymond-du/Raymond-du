package Test02;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟迭代器
 * @author Raymond-du
 *
 */

public class Test{
	public static void main(String[] args) {
		MyList list=new MyList();
		list.add("dsad");
		list.add("21d");
		list.add("d323d");
		list.add("dsqeqd");
		MyIterator<String> it=list.getIterator();
		while(it.hasNext()) {
			String str=it.next();
			System.out.println(str);
		}
	}
}
//迭代器接口
interface MyIterator<E>{
	boolean hasNext();
	E next();	
}
//创建迭代目标
class MyList{
	private List<String> list=new ArrayList<String>();
	public void add(String str) {
		list.add(str);
	}
	public MyIterator<String> getIterator(){
		MyIterator<String> iterator=new MyListIterator();
		return iterator;
	}
	private class MyListIterator implements MyIterator<String>{

		private int cursor=0;
		@Override
		public boolean hasNext() {
			if(cursor<list.size()) {
				return true;
			}
			return false;
		}

		@Override
		public String next() {
			if(cursor<list.size()) {
				return list.get(cursor++);
			}
			return null;
		}		
	}
}
