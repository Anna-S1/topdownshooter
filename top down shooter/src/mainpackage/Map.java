package mainpackage;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

public class Map {
	
	int[][] mapArray;
	int mapHeight;
	int mapWidth;
	public ArrayList<BufferedImage> mapSprites;
	int tileSize;
	ArrayList<Wall> walls;
	SpriteSheet mapSpriteSheet;
	int xoffset;
	int yoffset;
	
	public Map(String path, int ts, Player p) throws Exception
	{
		tileSize = ts; //sets the tilesize
		JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader(path)); 
		//imports selected file as JSONObject
		JSONArray ja = (JSONArray) jo.get("layers");
		//gets the layer part of that JSONObject as a JSONArray
		JSONObject layer = (JSONObject) ja.get(0);
		//gets the 1st layer of the layer JSONArray as a JSONObject and calls it layer
		ArrayList rawMap = (ArrayList) layer.get("data");
		//gets the data from layer and stores it as an ArrayList (this is the numbers of which tile in which place)
		mapHeight = Math.toIntExact((long) jo.get("height"));
		//gets the map's height and performs necesssary casting
		mapWidth = Math.toIntExact((long) jo.get("width"));
		//gets the map's width and performs necesssary casting
		mapArray = new int[mapHeight][mapWidth]; //creates an array
		for (int i=0; i < (rawMap.size()); i++) //this for loop turns the raw data into a 2d array so its easier to work with
		{
			int h = Math.floorDiv(i, mapWidth);
			int w = Math.floorMod(i, mapWidth);
			mapArray[h][w] = Math.toIntExact((long)  rawMap.get(i));
		}
		
		xoffset = 1280/2 - ((mapWidth*tileSize) / 2);
		yoffset = 720/2 - ((mapHeight*tileSize) / 2);
		
		p.x = xoffset + 6*(tileSize/4);
		p.y = yoffset + 6*(tileSize/4);
		
		//IMPORT IMAGES
		
		mapSpriteSheet = new SpriteSheet("tanktiles.png",32,32);
		mapSprites = mapSpriteSheet.getSprites();
		
		//GENERATE WALLS
		 walls = createWalls();
		
	}
	
	public void draw(Graphics2D g)
	{
		for (Wall el : walls)
		{
			el.draw(g, this);
		}
	}
	
	private ArrayList<Wall> createWalls()
	{
		ArrayList<Wall> walls = new ArrayList<Wall>();
		for (int i=0; i<mapHeight; i++)
		{
			for (int j=0; j<mapWidth; j++)
			{
				if (mapArray[i][j]!=0)
				{
					walls.add(new Wall(j*tileSize+xoffset, i*tileSize+yoffset, (mapArray[i][j])-1));
				}
			}
		}
		return walls;
	}
	
	public BufferedImage getImage(int num)
	{
		return mapSprites.get(num);
	}
	
}
