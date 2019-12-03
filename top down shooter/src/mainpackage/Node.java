package mainpackage;

public class Node {
	int tx;
	int ty;
	int x;
	int y;
	boolean wall;
	double hcost;
	int gcost;
	double fcost;
	Node parent;
	boolean hasParent;
	
	public Node(int x, int y, Map m, Node parent, int dtX, int dtY)
	{
		this.tx = x;
		this.ty = y;
		this.x = m.tileSize*tx;
		this.y = m.tileSize*ty;
		
		if (m.mapArray[ty][tx]==0)
		{
			this.wall = false;
		}else
		{
			this.wall = true;
		}
		
		if (parent!=null)
		{
			this.parent = parent;
			this.hasParent = true;
		}else
		{
			this.parent = null;
			this.hasParent = false;
		}
		
		if (this.hasParent)
		{
			this.gcost = this.parent.gcost + 1;
		}else
		{
			this.gcost = 0;
		}
		int dx = dtX*32;
		int dy = dtY*32;
		this.hcost = Math.sqrt((dx-x)^2 - (dy-y)^2) / 32;
		this.fcost = this.gcost + this.hcost;
	}
}
