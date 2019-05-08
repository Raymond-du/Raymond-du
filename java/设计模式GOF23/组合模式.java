package Test02;

import java.util.ArrayList;
import java.util.List;

/**组合模式的练习
 * 在很多的架构中都采取这种模式(杀毒软件,清理垃圾等)
 * 
 * 组合模式使用的场景
 * 把部分和整体的关系用树状结构来表示,从而使客户端可以使用
 * 统一的方式处理部分对象和整体对象.
 * 组合模式的核心:
 * 抽象构建(component)角色:定义课叶子和容器构件的共同点
 * 叶子(leaf)构件角色:无子结点
 * 容器(composite)构建角色:有容器特征,可以包含子节点
*/
public class Test {
	public static void main(String[] args) {

	}
}
//抽象角色定义共同点
interface ClearGarbage{
	void clearGarbage();
}
//清理图片
class ClearPhoto implements ClearGarbage{
	private String name;
	
	public ClearPhoto(String name) {
		this.name = name;
	}

	@Override
	public void clearGarbage() {
		System.out.println("图片文件-->"+name+"已被清理");
	}	
}
//清理视频
class ClearVedio implements ClearGarbage{
	private String name;
	
	public ClearVedio(String name) {
		this.name = name;
	}
	
	@Override
	public void clearGarbage() {
		System.out.println("视频文件-->"+name+"已被清理");
	}	
}
//清理文档
class ClearDocument implements ClearGarbage{
	private String name;
	
	public ClearDocument(String name) {
		this.name = name;
	}
	
	@Override
	public void clearGarbage() {
		System.out.println("文档文件-->"+name+"已被清理");
	}	
}
//清理一部分  
class ClearFolder implements ClearGarbage{
	private String name;
	//定义容器存放子节点
	private List<ClearGarbage> list=new ArrayList<ClearGarbage>();
	public ClearFolder(String name) {
		this.name=name;
	}
	public void add(ClearGarbage cg) {
		list.add(cg);
	}
	
	//可以实现该接口下的所有类都含有该方法  只要调用了该方法即可递归all
	@Override
	public void clearGarbage() {
		for(ClearGarbage cg:list) {
			System.out.println("指定文件清理-->"+name);
			System.out.println("\t");
			cg.clearGarbage();
		}
	}
}
