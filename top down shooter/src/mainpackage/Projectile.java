package mainpackage;

import java.awt.Graphics2D;

public class Projectile implements GameObject {

	double x;
	double y;
	double spd;
	double angle;
	double xmov;
	double ymov;
	int width;
	int height;
	boolean offScreen = false;
	
	public Projectile(int xin,int yin,int spdin, double anglein, int widthin, int heightin)
	{ //this is a generic constructor that all straight line projectiles should use
		x = xin;
		y = yin;
		spd = spdin;
		angle = anglein;
		width = widthin;
		height = heightin;
		xmov = spd*Math.cos(angle); //calculates the x and y components of movement
		ymov = spd*Math.sin(angle);
	}
	
	@Override
	public void update() {
		x = x+xmov; //moves the object by the x and y components
		y = y+ymov;
		
		if (x<-20) //calculates whether the bullet is off screen.
			offScreen = true;
		else if (x>Game.WINDOWWIDTH+20)
			offScreen = true;
		else if (y<-20)
			offScreen = true;
		else if (y>Game.WINDOWHEIGHT+20)
			offScreen = true;
		
	}

	@Override
	public void draw(Graphics2D g) {
		//write nothing here
	}

}
