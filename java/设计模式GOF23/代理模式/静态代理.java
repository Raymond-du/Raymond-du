package Test;

/**
 * 测试静态代理模式  (经纪人)
 * @author Raymond-du
 *
 */
public class Test {
	public static void main(String[] args) {
		LiuStar liu=new LiuStar();
		ProxyStar proxyStar=new ProxyStar(liu);
		try {
			proxyStar.sing();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
