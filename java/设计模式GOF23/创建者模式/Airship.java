package test;

/**
 * 了解创建者模式 (存在较多的组建  需要构建和装配)
 * 飞船测试
 * @author 26368
 *
 */
public class Airship {
	private Engine engine;
	private EscapeTower escapeTower;
	private OrbitalModule orbitalModule;
	public Airship(Engine engine, EscapeTower escapeTower, OrbitalModule orbitalModule) {
		this.engine = engine;
		this.escapeTower = escapeTower;
		this.orbitalModule = orbitalModule;
	}
	public Engine getEngine() {
		return engine;
	}

	public EscapeTower getEscapeTower() {
		return escapeTower;
	}

	public OrbitalModule getOrbitalModule() {
		return orbitalModule;
	}
	
	public static void main(String[] args) {
		//先构建组建
		AirshipBuilder builder=new DuAirshipBuilder();
		//再把组建组装起来
		DirectorAirship director=new DuDirctorAirship(builder);
		//再将组装起来的结果返回
		Airship airship=director.directAirship();
		
		System.out.println(airship.getEngine().getName());
	}
}
