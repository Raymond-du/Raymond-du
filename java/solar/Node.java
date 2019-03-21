package solar;

public class Node {
    private Node next;
    private Node pre;
    public Node(Object obj) {
	super();
	this.obj = obj;
    }
    private Object obj;
    public Node getNext() {
        return next;
    }
    public void setNext(Node next) {
        this.next = next;
    }
    public Node getPre() {
        return pre;
    }
    public void setPre(Node pre) {
        this.pre = pre;
    }
    public Object getObj() {
        return obj;
    }
    public void setObj(Object obj) {
        this.obj = obj;
    }
    public boolean hasNext() {
	return next!=null ? true:false;
    }
    public boolean hasPre() {
	return pre!=null? true:false;
    }    
}
