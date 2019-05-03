package test;

public class Client {
	public static void main(String[] args) {
		CarFactory factory=new LuxuryCarFactory();
		Engine engine= factory.getEngine();
		engine.performance();
	}
}
