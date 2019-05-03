package test;

public interface CarFactory {
	Engine getEngine() ;
	Tyre getTyre();
	Seat getSeat();
}

class LuxuryCarFactory implements CarFactory{

	@Override
	public Engine getEngine() {
		return new LuxuryEngine();
	}

	@Override
	public Tyre getTyre() {
		return new LuxuryTyre();
	}

	@Override
	public Seat getSeat() {
		return new LuxurySeat();
	}
	
}

class LowCarFactory implements CarFactory{
	@Override
	public Engine getEngine() {
		return new LowEngine();
	}

	@Override
	public Tyre getTyre() {
		return new LowTyre();
	}

	@Override
	public Seat getSeat() {
		return new LowSeat();
	}
}