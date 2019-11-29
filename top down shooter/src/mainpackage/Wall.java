package mainpackage;

import java.awt.Graphics2D;

public class Wall {
	int x;
	int y;
	int type;
	
	public Wall(int x, int y, int type)
	{
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public void draw(Graphics2D g, Map m)
	{
		g.drawImage(m.mapSprites.get(type), x, y, null);
	}
}
