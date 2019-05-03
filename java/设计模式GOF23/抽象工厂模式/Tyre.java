package test;

public interface Tyre {
	void performance();
}

class LuxuryTyre implements Tyre{

	@Override
	public void performance() {
		System.out.println("抗磨擦");
	}
	
}
class LowTyre implements Tyre{

	@Override
	public void performance() {
		System.out.println("容易破");
	}
	
}
