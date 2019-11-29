package mainpackage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mine {
	int x;
	int y;
	boolean armed;
	int radius;
	BufferedImage img;
	BufferedImage img2;
	long timeCreated = System.currentTimeMillis();
	
	public Mine(int x, int y)
	{
		this.x = x;
		this.y = y;
		armed = false;
		
		try { // try-catch loop in case the file cannot be located
			img = ImageIO.read(new File("mine1.png"));
			img2 = ImageIO.read(new File("mine2.png"));
		} catch (IOException e) { //this exception means the file could not be opened or does not exist
			System.out.println("An Error Occured While Importing The Mine Sprite");
			e.printStackTrace(); //prints the stack trace to help debug
		}
	}
	
	public void update()
	{
		if (System.currentTimeMillis() - timeCreated < 2500)
		{
			armed = true;
		}
	}
	
	public void draw(Graphics2D g)
	{
		if (armed)
		{
			g.drawImage(img2, x, y, null);
		}else
		{
			g.drawImage(img2, x, y, null);
		}
	}
}
