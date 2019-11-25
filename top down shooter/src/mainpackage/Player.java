package mainpackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player implements GameObject {
	
	int x = 400;
	int y = 300;
	int xvel = 0;
	int yvel = 0;
	int velmult = 3;
	
	int hitx = x+4;
	int hity = y+4;
	int hitwidth = 23;
	int hitheight = 23;
	
	boolean wPressed = false;
	boolean dPressed = false;
	boolean sPressed = false;
	boolean aPressed = false;
	
	double angle = Math.PI/2;
	private BufferedImage pBase;
		
	public Player ()
	{
		
	}
	
	public void update()
	{
		xvel = 0;
		yvel = 0;
		if (wPressed)
			yvel = -1;
		if (dPressed)
			xvel = 1;
		if (sPressed)
			yvel = 1;
		if (aPressed)
			xvel = -1;
		if (wPressed && sPressed)
			yvel = 0;
		if (aPressed && dPressed)
			xvel = 0;
		
		xvel = xvel * velmult;
		yvel = yvel * velmult;
		x = x + xvel;
		y = y + yvel;
		
		//angle = 0;
		if (dPressed)
			angle = Math.PI/2;
		else if (aPressed)
			angle = 3*(Math.PI/2);
		else if (sPressed)
			angle = Math.PI;
		else if (wPressed)
			angle = 0;
		
		if (dPressed && wPressed)
			angle = Math.PI/4;
		else if (dPressed && sPressed)
			angle = Math.PI/2 + Math.PI/4;
		else if (aPressed && wPressed)
			angle = 2*Math.PI - Math.PI/4;
		else if (aPressed && sPressed)
			angle = Math.PI + Math.PI/4;
	}

	public void draw(Graphics2D g)
	{
		try {
			BufferedImage pBase = ImageIO.read(new File("playerbase.png"));
			// create the transform, note that the transformations happen
            // in reversed order (so check them backwards)
            AffineTransform at = new AffineTransform();

            // 3. translate it to its real position
            at.translate(x, y);

            // 2. do the actual rotation
            at.rotate(angle);

            // 1. translate the object so that you rotate it around the 
            //    center (easier :))
            at.translate(-16, -16);

            // draw the image
            g.drawImage(pBase, at, null);
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("An Error Occured While Drawing The Player");
			e.printStackTrace();
		}
		
		
	}	
	
	public void keyPressed(KeyEvent e)
	{
		switch (returnKey(e))
		{
		case "W":
			wPressed = true;
			break;
		case "D":
			dPressed = true;
			break;
		case "S":
			sPressed = true;
			break;
		case "A":
			aPressed = true;
			break;
		default:
			break;
		}
		
	}
	
	public void keyReleased(KeyEvent e)
	{
		switch (returnKey(e))
		{
		case "W":
			wPressed = false;
			break;
		case "D":
			dPressed = false;
			break;
		case "S":
			sPressed = false;
			break;
		case "A":
			aPressed = false;
			break;
		default:
			break;
		}
	}
	
	private String returnKey(KeyEvent e)
	{
		return KeyEvent.getKeyText(e.getKeyCode());
	}
	
}
