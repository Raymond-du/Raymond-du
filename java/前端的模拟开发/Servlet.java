package Server;

public abstract class Servlet {
	
	void server(Respond rep) {
		this.doGet(rep);
		this.doPost(rep);		
	}
	public abstract void doGet(Respond rep);
	public abstract void doPost(Respond rep);
}
