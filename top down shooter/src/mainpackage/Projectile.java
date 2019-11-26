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
	
	public Projectile(int xin,int yin,int spdin, double anglein, int widthin, int heightin)
	{
		xmov = spd*Math.sin(angle);
		ymov = spd*Math.cos(angle);
		x = xin;
		y = yin;
		spd = spdin;
		angle = anglein;
		width = widthin;
		height = heightin;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		x = x+xmov;
		y = y+ymov;
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub

	}

}
