package Test;

//品牌
public interface Brand {
	String getName();
}
class Lenovo implements Brand{
	private final String name="联想";
	@Override
	public String getName() {
		return name;
	}
}
class Dell implements Brand{
	private final String name="戴尔";
	@Override
	public String getName() {
		return name;
	}
}
class Unknown implements Brand{
	private String name="无记录";

	public Unknown(String brand) {
		name+="("+brand+")";
	}
	@Override
	public String getName() {
		return name;
	}
}
