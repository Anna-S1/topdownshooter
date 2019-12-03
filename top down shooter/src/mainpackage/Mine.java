package mainpackage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Mine {
	int x;
	int y;
	int width = 16;
	int height = 16;
	boolean armed;
	int radius;
	BufferedImage img;
	BufferedImage img2;
	long timeCreated = System.currentTimeMillis();
	int power = 50;
	int midx;
	int midy;
	
	public Mine(int x, int y)
	{
		this.x = x;
		this.y = y;
		armed = false;
		
		midx = x - (width/2);
		midy = y - (height/2);
		
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
		if ((System.currentTimeMillis() - timeCreated) > 1500)
		{
			armed = true;
		}
	}
	
	public void draw(Graphics2D g, Map m)
	{
		if (armed)
		{
			explode(m);
			//g.drawImage(img2, x, y, null);
		}else
		{
			g.drawImage(img, x, y, null);
		}
	}
	
	public void explode(Map m)
	{
		ArrayList itemsToRemove = new ArrayList();
		int adj = 0;
		for (int i=0; i<m.walls.size(); i++)
		{
			if (m.walls.get(i).type==1)
			{
				double dist = Math.sqrt((midx - (m.walls.get(i).x - 16))^2 + (midy - (m.walls.get(i).y - 16))^2);
				if (dist<=power)
				{
					m.walls.remove(i-adj);
					adj = adj+1;
				}
			}
		}
	}
}
