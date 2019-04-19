package Server;

import java.util.HashSet;
import java.util.Set;

/**
 * 登陆url的字符串和  名的对应
 * 可能出现1对多的情况
 * @author 26368
 *
 */
public class Mapping {
	private String name;
	private Set<String> pattern;
	Mapping(){
		pattern=new HashSet<>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<String> getPattern() {
		return pattern;
	}
}
