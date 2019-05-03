package test;

public interface Seat {
	void performance();
}

class LuxurySeat implements Seat{

	@Override
	public void performance() {
		System.out.println("可以按摩");
	}
	
}
class LowSeat implements Seat{

	@Override
	public void performance() {
		System.out.println("不可按摩");
	}
	
}
