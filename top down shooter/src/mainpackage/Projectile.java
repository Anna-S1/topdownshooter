package mainpackage;

import java.awt.Graphics2D;

public class Projectile {

	double x;
	double y;
	double hitx;
	double hity;
	double spd;
	double angle;
	double xmov;
	double ymov;
	int width;
	int height;
	boolean offScreen = false;
	boolean dead = false;
	int maxBounces;
	int bounces = 0;
	
	public Projectile(int xin,int yin,int spdin, double anglein, int widthin, int heightin, int maxBounces)
	{ //this is a generic constructor that all straight line projectiles should use
		x = xin;
		y = yin;
		hitx = xin-8;
		hity = yin-8;
		spd = spdin;
		angle = anglein;
		width = widthin;
		height = heightin;
		xmov = spd*Math.cos(angle); //calculates the x and y components of movement
		ymov = spd*Math.sin(angle);
		this.maxBounces = maxBounces;
	}
	
	public void update(Map m) {
		
		
		if (x<-20) //calculates whether the bullet is off screen.
			offScreen = true;
		else if (x>Game.WINDOWWIDTH+20)
			offScreen = true;
		else if (y<-20)
			offScreen = true;
		else if (y>Game.WINDOWHEIGHT+20)
			offScreen = true;
		
		for (int i=0; i<m.walls.size(); i++)
		{
			if (AABBCollision(m.walls.get(i).x, m.walls.get(i).y, m.tileSize, m.tileSize, (int)(hitx+xmov), (int)(hity+ymov), width, height))
			{
				if (maxBounces==bounces)
				{
					dead = true;
				}else
				{
					bounces = bounces+1;
					if (AABBCollision(m.walls.get(i).x, m.walls.get(i).y, m.tileSize, m.tileSize, (int)(hitx+xmov), (int)hity, width, height))
					{
						xmov = -xmov;
					}else
					{
						ymov = -ymov;
					}
				}
			}
		}
		
		x = x+xmov; //moves the object by the x and y components
		y = y+ymov;
		hitx = hitx + xmov;
		hity = hity + ymov;
		angle = Math.atan2(ymov, xmov);
		
	}

	public void draw(Graphics2D g) {
		//write nothing here
	}

	private boolean AABBCollision(int x, int y, int w, int h, int x2, int y2, int w2, int h2)
	{		
		if (x2+w2>x && x2<x+w && y2+h2>y && y2<y+h)
			return true;
		else
			return false;
	}
	
}
