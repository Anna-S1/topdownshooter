package mainpackage;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerBullet extends Projectile {
	
	BufferedImage bulletImage;
	public PlayerBullet(int xin, int yin, int spdin, double anglein) {
		super(xin, yin, spdin, anglein, 16, 16);
		try 
		{
			BufferedImage temp = ImageIO.read(new File("bullet.png"));
			bulletImage = temp;
		}catch (IOException e)
		{
			System.out.println("An Error Occured whilst constructing the bullet");
		}
		
	}
	public void update()
	{
		super.update();
	}
	
	public void draw(Graphics2D g)
	{
		AffineTransform at = new AffineTransform();

		//transforms are written backwards. The number represents the order in which its performed
		
        // 3. translate it to its real position
        at.translate(x, y);

        // 2. do the actual rotation
        at.rotate(angle);

        // 1. translate the object so that you rotate it around the 
        //    centre (easier :))
        at.translate(-width/2, -height/2);

        // draw the image
        g.drawImage(bulletImage, at, null);
	}

}
