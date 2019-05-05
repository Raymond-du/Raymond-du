package Test;

//明星接口   可以创建多个明星对象
public interface Star {
	//面谈
	public void confer();
	//签合同
	public void signContract();
	//订票
	public void bookTicket();
	//唱歌
	public void sing() throws Exception;
	//收钱
	public void collectMoney();
}

//创建刘德华star对象
class LiuStar implements Star{

	@Override
	public void confer() {
		System.out.println("LiuStar.confer()");
	}

	@Override
	public void signContract() {
		System.out.println("LiuStar.signContract()");
	}

	@Override
	public void bookTicket() {
		System.out.println("LiuStar.bookTicket()");
	}

	@Override
	public void sing() {
		System.out.println("LiuStar.sing()");
	}

	@Override
	public void collectMoney() {
		System.out.println("LiuStar.collectMoney()");
	}
	
}

//创建代理(经纪人)对象  代理人不能够sing 需要让明星对象干
class ProxyStar implements Star{
	//初始化对象时先指明明星对象
	private Star star=null;
	public ProxyStar(Star star) {
		this.star=star;
	}
	
	@Override
	public void confer() {
		System.out.println("ProxyStar.confer()");
	}

	@Override
	public void signContract() {
		System.out.println("ProxyStar.signContract()");
	}

	@Override
	public void bookTicket() {
		System.out.println("ProxyStar.bookTicket()");
	}

	@Override
	public void sing() throws Exception {
		if(null==star) {
			throw new Exception();
		}
		star.sing();
	}

	@Override
	public void collectMoney() {
		System.out.println("ProxyStar.collectMoney()");
	}
	
}