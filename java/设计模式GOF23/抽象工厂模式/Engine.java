package test;

public interface Engine {
	void performance();
}

class LuxuryEngine implements Engine{

	@Override
	public void performance() {
		System.out.println("性能好,动力强");
	}
	
}
class LowEngine implements Engine{

	@Override
	public void performance() {
		System.out.println("性能差,动力弱");
	}
	
}