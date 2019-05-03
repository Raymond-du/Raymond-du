package test;
//
public interface DirectorAirship {
	
	Airship directAirship();
}
class DuDirctorAirship implements DirectorAirship{

	private AirshipBuilder builder;
	public DuDirctorAirship(AirshipBuilder builder) {
		this.builder = builder;
	}
	@Override
	public Airship directAirship() {
		Engine e=builder.createEngine();
		OrbitalModule om=builder.createOritalModule();
		EscapeTower et=builder.createEscapeTower();
		Airship airship=new Airship(e,et,om);
		return airship;
	}
	
}
