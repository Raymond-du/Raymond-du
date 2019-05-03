package test;

//创建飞船的构造
public interface AirshipBuilder {
	Engine createEngine();//构建引擎
	EscapeTower createEscapeTower();//构建逃离塔
	OrbitalModule createOritalModule();//构建轨道舱	
}
class DuAirshipBuilder implements AirshipBuilder{
	//这里的参数可以使用xml解析等  进行添加
	@Override
	public Engine createEngine() {
		System.out.println("构建引擎");
		return new Engine("Du");
	}

	@Override
	public EscapeTower createEscapeTower() {
		System.out.println("构建逃离塔");
		return new EscapeTower("Du");
	}

	@Override
	public OrbitalModule createOritalModule() {
		System.out.println("构建轨道舱");
		return new OrbitalModule("Du");
	}
	
}