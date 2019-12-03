package mainpackage;

import java.awt.Point;
import java.util.ArrayList;

public class Enemy { //this will be a superclass that all other enemies will be derived from
	
	int x;
	int y;
	int rot; //rotation of sprite
	int tX; //tile x
	int tY; //tile y
	int dtX; //destination tile x
	int dtY; //destination tile y
	Point dPoint;
	
	public Enemy(int x, int y, int px, int py, Map m)
	{
		this.x = x;
		this.y = y;
		this.dtX = px/32;
		this.dtY = py/32;
		this.tX = x/32;
		this.tY = y/32;
		this.dPoint = new Point(dtX, dtY);
		ArrayList<Point> testpath = new ArrayList<Point>();
		testpath = findPath(dPoint, m);
		for (int i=0; i<testpath.size(); i++)
			System.out.println(testpath.get(i).x + " " + testpath.get(i).y);
	}
	
	private ArrayList<Point> findPath(Point p, Map m)
	{
		ArrayList<Point> path = new ArrayList<Point>();
		ArrayList<Node> open = new ArrayList<Node>();
		ArrayList<Node> closed = new ArrayList<Node>();
		
		//A* algorithm
		open.add(new Node(tX, tY, m, null, p.x, p.y));
		while (open.size()>0)
		{
			int currentNodePos = 0;
			for (int i=0; i<open.size(); i++) //find the node with smallest f cost
			{
				if (open.get(currentNodePos).fcost < open.get(i).fcost)
					currentNodePos = i;
			}
			Node currentNode = open.get(currentNodePos);
			
			closed.add(currentNode);
			open.remove(currentNode);
			System.out.println(currentNode.tx + " " + p.x + "   " + currentNode.ty + " " + p.y);
			
			if (currentNode.tx==p.x && currentNode.ty==p.y) //check if we are at the end node
			{
				System.out.print("here");
				while (true){ //iterate until the current node has no parent
					if (currentNode.hasParent==false)
						return path;
					path.add(new Point(currentNode.tx, currentNode.ty));
					currentNode = currentNode.parent;
				}
			}
			
			Node newnode;
			//add more nodes to the open list
			try {
				newnode = new Node(currentNode.tx + 1, currentNode.ty, m, currentNode, p.x, p.y);
				if (! newnode.wall)
				{
					if (!isNodeInList(newnode, open) && !isNodeInList(newnode, closed))
						open.add(newnode);
				}
			}catch (Exception e)
			{
				newnode = null;
			}
			
			try {
				newnode = new Node(currentNode.tx - 1, currentNode.ty, m, currentNode, p.x, p.y);
				if (! newnode.wall)
				{
					if (!isNodeInList(newnode, open) && !isNodeInList(newnode, closed))
						open.add(newnode);
				}
			}catch (Exception e)
			{
				newnode = null;
			}
			
			try {
				newnode = new Node(currentNode.tx, currentNode.ty + 1, m, currentNode, p.x, p.y);
				if (! newnode.wall)
				{
					if (!isNodeInList(newnode, open) && !isNodeInList(newnode, closed))
						open.add(newnode);
				}
			}catch (Exception e)
			{
				newnode = null;
			}
			
			try {
				newnode = new Node(currentNode.tx, currentNode.ty - 1, m, currentNode, p.x, p.y);
				if (! newnode.wall)
				{
					if (!isNodeInList(newnode, open) && !isNodeInList(newnode, closed))
						open.add(newnode);
				}
			}catch (Exception e)
			{
				newnode = null;
			}
		}
		return path;
	}
	
	private boolean isNodeInList(Node n, ArrayList<Node> a)
	{
		for (int i=0; i<a.size(); i++)
		{
			if (n.tx == a.get(i).tx && n.ty == a.get(i).ty)
			{
				return true;
			}
		}
		return false;
	}
	
}