package mainpackage;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player {
	
	int x = 0;
	int y = 0;
	int xvel = 0;
	int yvel = 0;
	int velmult = 2;
	
	int hitx = x+4;
	int hity = y+4;
	int hitwidth = 23;
	int hitheight = 23;
	
	boolean wPressed = false;
	boolean dPressed = false;
	boolean sPressed = false;
	boolean aPressed = false;
	boolean spacePressed = false;
	
	BufferedImage pBase;
	BufferedImage pTurret;
	
	double angle = Math.PI/2;
	double turretangle = 0;
	
	Point mouseCoords;
	
	ArrayList<Mine> mines = new ArrayList();
	
	public Player () //this constructor loads both images for the player sprite
	{
		try { // try-catch loop in case the file cannot be located
			BufferedImage temp = ImageIO.read(new File("playerbase.png"));
			pBase = temp;
			temp = ImageIO.read(new File("playerturret.png"));
			pTurret = temp;
		} catch (IOException e) { //this exception means the file could not be opened or does not exist
			System.out.println("An Error Occured While Importing The Player Sprite");
			e.printStackTrace(); //prints the stack trace to help debug
		}
	}
	
	public void update(Map m) //this method is run before drawing everything every gameloop
	{
		mouseCoords = MouseInfo.getPointerInfo().getLocation(); //these are the mouse coordinates
		turretangle = Math.atan2((y+hitheight/2)-mouseCoords.y, (x+hitwidth/2)-mouseCoords.x)-Math.PI/2; //this is the angle the turret faces
		
		xvel = 0; //sets x velocity to 0
		yvel = 0; //sets y velocity to 0
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
		
		// ^^ works out which way the player should be moving on this frame ^^
		
		xvel = xvel * velmult;
		yvel = yvel * velmult;
		
		
		//collision detection
		boolean collidedx = false;
		boolean collidedy = false;
		for (int i=0; i<m.walls.size(); i++)
		{
			if (AABBCollision(m.walls.get(i).x+12, m.walls.get(i).y+12, m.tileSize, m.tileSize, x+xvel, y, hitwidth, hitheight))
				collidedx = true;
			if (AABBCollision(m.walls.get(i).x+12, m.walls.get(i).y+12, m.tileSize, m.tileSize, x, y+yvel, hitwidth, hitheight))
				collidedy = true;
		}
		if (!collidedx)
		{
			x = x + xvel;
			hitx = hitx + xvel;
		}
		if (!collidedy)
		{
			y = y + yvel;
			hity = hity+yvel;
		}
		
		// ^^ moves the player by the correct amount ^^
		
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
		
		// ^^ works out which angle the player sprite should be facing ^^
	}

	public void draw(Graphics2D g)
	{
		// create the transform, note that the transformations happen
        // in reversed order (so check them backwards)
		AffineTransform at = new AffineTransform();

        // 3. translate it to its real position
        at.translate(x, y);

        // 2. do the actual rotation
        at.rotate(angle);

        // 1. translate the object so that you rotate it around the 
        //    centre (easier :))
        at.translate(-16, -16);

        // draw the image
        g.drawImage(pBase, at, null);
        
        // create the transform, note that the transformations happen
        // in reversed order (so check them backwards)
		at = new AffineTransform();

        // 3. translate it to its real position
        at.translate(x, y);

        // 2. do the actual rotation
        at.rotate(turretangle);

        // 1. translate the object so that you rotate it around the 
        //    centre (easier :))
        at.translate(-16, -16);

        // draw the image
        g.drawImage(pTurret, at, null);
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
		case "Space":
			if (spacePressed==false)
			{
				mines.add(new Mine(x, y));
			}
		default:
			break;
		}
		//determines which key has been pressed
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
		case "Space":
			spacePressed = false;
		default:
			break;
		} //determines if any keys have been released. this allows the game to have intuitive movement
	}
	
	private String returnKey(KeyEvent e) //this function returns the key that was pressed from the keyevent
	{
		return KeyEvent.getKeyText(e.getKeyCode());
	}
	
	private boolean AABBCollision(int x, int y, int w, int h, int x2, int y2, int w2, int h2)
	{		
		if (x2+w2>x && x2<x+w && y2+h2>y && y2<y+h)
			return true;
		else
			return false;
	}
	
}
