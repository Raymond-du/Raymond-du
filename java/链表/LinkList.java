package solar;

import java.util.Iterator;

public class LinkList implements java.lang.Iterable{
    private Node first;//链表的头
    private Node last;//链表的尾
    private int size;//链表长度
    
    public LinkList() {	
    }
    
    public LinkList(Object obj) {
	add(obj);
    }
    
    public int size() {
	return size;
    }
    
    public void add(Object obj) {//追加到链表的尾部
	Node n=new Node(obj);
	if(first==null) {
	    first=n;
	    last=n;	    
	}else {
	    last.setNext(n);//将最后一个node的指向新的node;
	    n.setPre(last);//将新的node的pre指向last;
	    last=n;	    
	}
	size++;
    }
    
    public void addFirst(Object obj) {//添加对象到头部
	Node n=new Node(obj);
	if(first==null) {
	    first=n;
	    last=n;
	}else {
	    n.setNext(first);//将新的node的下一个指向第一个;
	    first.setPre(n);//将第一个node的上一个指向新的node;
	    first=n;
	}
	size++;
    }
    
    private void rangeCheck(int index) {
	if(index<0) {
	    try {
		throw new Exception("输入下标不正确");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	if(index>=size) {
	    throw new IndexOutOfBoundsException("你输入的下表已经越界");
	}
    }
    
    public Object get(int index) {
	Node temp=getNode(index);
	return temp.getObj();
    }
    
    public void addLast(Object obj) {
	this.add(obj);
    }
    
    public Object remove() {//移除头节点
	if(size>0) {
	    Object temp=first;
	    first.getNext().setPre(null);
	    first=first.getNext();
	    return temp;
	}
	return null;	
    }
    
    private Node getNode(int index) {//获取指定节点
	rangeCheck(index);
	Node temp=first;
	while(true) {
	    if(index-->0) {
		temp=temp.getNext();
	    }else {
		break;
	    }
	}
	return temp;
    }
    
    public Object remove(int index) {//移除指定对象
	rangeCheck(index);
	Node temp=getNode(index);
	if(size==1) {//如果只有一个节点的情况下
	    first=null;
	    last=null;
	    size--;
	    return temp.getObj();
	}
	
	if(temp.hasPre()) {//存在前节点
	    temp.getPre().setNext(temp.getNext());	    
	}else {//不存在前节点
	    first=temp.getNext();
	}
	if(temp.hasNext()) {
	    temp.getNext().setPre(temp.getPre());
	}else {
	    last=temp.getPre();
	}
	size--;
	return temp.getObj();
    }
       
    @Override
    public Iterator iterator() {

	return new Iterator() {

	    private int cur=0;//当前对象
	    private int lastRet=-1;//刚刚遍历完的对象
	    @Override
	    public boolean hasNext() {
		return cur!=size;
	    }

	    @Override
	    public Object next() {
		
		if(cur>=size) {
		    try {
			throw new Exception();
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
		Object temp=get(cur);
		lastRet=cur++;
		return temp;		
	    }
	    
	    public void remove() {
		if(lastRet==-1) {
		    try {
			throw new Exception("该数据已被删除");
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
		LinkList.this.remove(lastRet);
		lastRet=-1;
	    }
	};
    }
    public static void main(String[] args) {
	LinkList l=new LinkList();
	l.add(1);
	l.add(4);
	l.add(7);
	l.add(2);
	for(Object t:l) {
	   System.out.println(t);
	}
	

    }
}






























