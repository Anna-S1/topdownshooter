package mainpackage;

import java.awt.Graphics2D;
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
	ArrayList<BufferedImage> mapSprites;
	int tileSize;
	
	public Map(String path, int ts) throws Exception
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
		
		//IMPORT IMAGES
		
		SpriteSheet mapSpriteSheet = new SpriteSheet("tanktiles.png",32,32);
		mapSprites = mapSpriteSheet.getSprites();
	}
	
	public void draw(Graphics2D g)
	{
		for (int i=0; i<mapHeight; i++)
		{
			for (int j=0; j<mapWidth; j++)
			{
				if (mapArray[i][j] != 0)
				{
					g.drawImage(mapSprites.get(mapArray[i][j]-1), j*tileSize, i*tileSize, null);
				}
			}
		}
	}
	
}
