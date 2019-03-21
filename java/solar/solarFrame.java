package solar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class solarFrame	extends javax.swing.JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image backGround=GameUtil.getImage("images/bg.jpg");
	
	Star sun=new Star("images/sun.jpg", backGround.getWidth(null)/2, backGround.getHeight(null)/2);
	Star mercury=new Star("images/Mercury.jpg", 140, 70, 0.05, sun);//水星
	Star venus=new Star("images/Venus.jpg",200,120,0.09,sun);//金星
	Star earth=new Star("images/Earth.jpg", 290, 170,0.16,sun);
	Star mars=new Star("images/Mars.jpg", 350, 220,0.22,sun);//火星
	Star jupiter=new Star("images/jupiter.jpg",490,300,0.3,sun);//木星
	Star saturn=new Star("images/Saturn.jpg", 550, 400, 0.42, sun);//土星
	Star uranus=new Star("images/Uranus.jpg", 680, 520,0.53,sun);////天王星
	Star neptune=new Star("images/Neptune.jpg", 800, 600, 0.66, sun);//海王星
	Star moon=new Star("images/moon.jpg", 30, 30,0.1,earth);
	
	class PaintThread extends Thread{//重绘线程

		@Override
		public void run()		{
			while(true) {
				repaint();
				try				{
					Thread.sleep(100);
				} catch (InterruptedException e)				{
					e.printStackTrace();
				}
			}
		}		
	}
	
	public void launchFrame() {//设置桌面大小
		setSize(backGround.getWidth(null), backGround.getWidth(null));//设置窗口大小
		setLocation(200, 200);//设置位置
		setVisible(true);//这里必须设置为true 默认为false
		new PaintThread().start();//启动线程
		
		addWindowListener(new WindowAdapter() {//添加关闭按钮消息
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
			
		});
	}
	
	@Override
	public void paint(Graphics g)	{//重载重绘函数
		g.drawImage(backGround, 0,0, null);
		sun.draw(g);
		jupiter.draw(g);
		mars.draw(g);
		mercury.draw(g);
		neptune.draw(g);
		saturn.draw(g);
		uranus.draw(g);
		venus.draw(g);
		earth.draw(g);
		moon.draw(g);
	}

	public static void main(String[] args)	{
		solarFrame s=new solarFrame();
		s.setResizable(false);//不可调节大小
		s.launchFrame();
	}
}
