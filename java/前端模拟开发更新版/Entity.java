package Server;
/**
 * 实体  类地址和  字符串的对应关系
 * 1对1关系
 * @author 26368
 *
 */
public class Entity {
	private String name;
	private String clz;
	public Entity() {
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClz() {
		return clz;
	}
	public void setClz(String clz) {
		this.clz = clz;
	}	
}
