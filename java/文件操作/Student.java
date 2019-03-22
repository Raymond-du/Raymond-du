
package Thinking;
//这里需要使用Serializable的接口   该接口是空接口  表明该类可以序列化
public class Student implements java.io.Serializable{
    private int id;
    private String name;
    private int age;
    private transient String descrip;
    public Student(int id, String name, int age, String descrip) {
	super();
	this.id = id;
	this.name = name;
	this.age = age;
	this.descrip = descrip;
    }
    public Student() {
	super();
    }
    @Override
    public String toString() {
	return "Student [id=" + id + ", name=" + name + ", age=" + age + ", descrip=" + descrip + "]";
    }
}
