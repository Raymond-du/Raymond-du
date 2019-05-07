package Test;
import java.util.Set;


/**
 * 测试桥接模式
 * 例如 电脑存在品牌和类型  (联想台式机,联想手机, 戴尔笔记本,戴尔平板电脑,等)
 * 创建一个电脑销售系统   测试 加入电脑仓库  xml获取库存  利用单例模式
 * 
 * 记录销售对象和销售员信息    获取销售权限(添加代做)
 * 1.创建一个解析xml的类    单例模式  
 * 2.实现一个添加库存的方法并生成日志
 * 3.获取库存的信息的类
 * 4.销售电脑的类 并生成日志
 * @author 26368
 *
 *
 *合成和聚合的关系
 *合成对象拥有对成员部分的完全支配权 (即对象创建则部件也创建,对象泯灭则部件也泯灭)
 *聚合关系   部件不会随着对象的销毁而销毁
 */
public class Test{
	
	
	public static void main(String[] args) {
		Util.Purchase("笔记本", "苹果", 10);
		Util.printAllComputer();
		Util.saleComputer( "笔记本","联想");
		Util.printAllComputer();
	}
}