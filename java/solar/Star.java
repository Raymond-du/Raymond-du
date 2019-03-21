package solar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Star{
	Image img;//行星图
	
	private double x;//行星 的中心位置x
	private double y;//行星 的中心位置y
	
	private double longAxis;//轨迹长轴
	private double shortAxis;//轨迹短轴
	 
	private double degree;//当前角度
	private double speed;//公转角速度 
	
	private Star centerStar;//围绕的中心天体
	
	
	public Star(Image img,double x,double y)	{
		this.img=img;
		this.x =x;
		this.y=y;
		this.longAxis=0;
		this.shortAxis=0;
		this.degree=0;
		this.speed=0;
		this.centerStar=null;
	}
	
	public Star(String imgPath,double x,double y) {
		this(GameUtil.getImage(imgPath),x,y);
	}
	
	public Star(String imgPath,double longAxis,double shortAxis,double speed,Star centerSatr) {
		this.x=centerSatr.x+longAxis/2;
		this.y=0;
		this.img=GameUtil.getImage(imgPath);
		this.longAxis=longAxis;
		this.shortAxis=shortAxis;
		this.speed=speed;
		this.centerStar=centerSatr;
	}
	
	public void draw (Graphics g) {
		if(speed==0) {
			g.drawImage(img, (int)(x-img.getWidth(null)/2), (int)(y-img.getHeight(null)/2), null);
			return ;
		}
		this.x= centerStar.x+longAxis/2*Math.cos(degree);
		this.y= centerStar.y+shortAxis/2*Math.sin(degree);
		Color color=g.getColor();
		g.setColor(Color.BLUE);//设置划线颜色
		g.drawOval((int)(centerStar.x-longAxis/2), (int)(centerStar.y-shortAxis/2), (int)longAxis, (int)shortAxis);
		g.drawImage(img,(int) (this.x-img.getWidth(null)/2),(int)(y-img.getHeight(null)/2), null);
		degree+=speed;
		g.setColor(color);
	}
	 
}
