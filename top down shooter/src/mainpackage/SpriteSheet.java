package mainpackage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SpriteSheet { //this class assumes sprite sheets are only horizontal as they are from aseprite
	
	BufferedImage sheet;
	int tileWidth;
	int tileHeight;
	int totalWidth;
	int totalHeight;
	int numOfTiles;
	
	public SpriteSheet(String path, int width, int height)
	{
		
		try 
		{
			sheet = ImageIO.read(new File(path)); 
		}
		catch (IOException e)
		{
			System.out.println("An Error Occured whilst constructing the sprite sheet");
		}
		
		tileWidth = width;
		tileHeight = height;
		totalWidth = sheet.getWidth();
		totalHeight = sheet.getHeight();
		numOfTiles = totalWidth/tileWidth;
	}
	
	public ArrayList<BufferedImage> getSprites()
	{
		ArrayList<BufferedImage> x = new ArrayList<BufferedImage>();
		for (int i=0; i<numOfTiles; i++)
		{
			x.add(sheet.getSubimage(i*tileWidth, 0, tileWidth, tileHeight));
		}
		return x;
	}
	
	public BufferedImage getSpecificSprite(int num)
	{
		return (sheet.getSubimage((num*tileWidth), 0, tileWidth, tileHeight));
	}
}
