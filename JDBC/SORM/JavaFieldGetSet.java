package SORM;
/**
 * 封装了属性的set get 方法源码信息
 * @author Raymond-du
 *
 */
public class JavaFieldGetSet {
	// 属性的源码 如:private String name;
	private String fieldSource;
	//生成的get方法源码
	private String getFieldSource;
	//生成的set方法源码
	private String setFieldSource;
	public String getFieldSource() {
		return fieldSource;
	}
	public void setFieldSource(String fieldSource) {
		this.fieldSource = fieldSource;
	}
	public String getGetFieldSource() {
		return getFieldSource;
	}
	public void setGetFieldSource(String getFieldSource) {
		this.getFieldSource = getFieldSource;
	}
	public String getSetFieldSource() {
		return setFieldSource;
	}
	public void setSetFieldSource(String setFieldSource) {
		this.setFieldSource = setFieldSource;
	}
	@Override
	public String toString() {
		return fieldSource+"\n"+getFieldSource+"\n"+setFieldSource;
	}	
}
