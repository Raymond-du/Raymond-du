package solar;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class GameUtil{

	private GameUtil() {}//如果是个工具类的话   将构造函数私有化
	
	public static Image getImage(String path) {
		URL url= GameUtil.class.getClassLoader().getResource(path);
		Image img=null;
		try
		{
			img = ImageIO.read(url);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return img;
	}
}
