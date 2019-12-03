package mainpackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;

@SuppressWarnings("serial")
public class Game extends JPanel 
{

	public static final int WINDOWWIDTH = 1280;
	public static final int WINDOWHEIGHT = 720;
	Player player = new Player();
	ArrayList<PlayerBullet> playerBullets = new ArrayList<>(); //creates an ArrayList that only allows
	//PlayerBullet objects. This syntax allows the object's methods to still be used.
	Map map;
	
	public static void main(String[] args) throws InterruptedException, IOException 
	{
		JFrame frame = new JFrame("Top Down Shooter"); //creates a new JFrame object with title
		Game game = new Game(); //creates a new game object to be displayed on the JFrame
		frame.add(game);
		frame.setSize(WINDOWWIDTH,WINDOWHEIGHT);
		frame.setResizable(true); //stops the window from being resized
		frame.setVisible(true); //makes the window visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes the window close when X is pressed
		
		Toolkit toolkit = Toolkit.getDefaultToolkit(); //creates a toolkit
		Image image = toolkit.getImage("crosshair.png"); //imports the cursor image
		Cursor c = toolkit.createCustomCursor(image , new Point(16,16), "img"); //imports the cursor image as a cursor
		//and it sets the cursor centre as 16,16
		frame.setCursor (c); //sets the crosshair as the current cursor
		
		
		
		while (true) //this is the game loop
		{
			game.updategame(); //runs my update function
			game.repaint(); //runs the paint function again
			Thread.sleep(10); //makes this thread sleep for 10ms so other threads can run.
		}
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g); //clears the screen of previous paints
		Graphics2D g2d = (Graphics2D) g; //creates a graphics2d object
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //turns aa on
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, WINDOWWIDTH, WINDOWHEIGHT); //creates a background rectangle that is dark gray
		g2d.setColor(Color.WHITE);
		for (int i=0; i<player.mines.size(); i++)
		{
			player.mines.get(i).draw(g2d, map);
		}
		player.draw(g2d);
		map.draw(g2d);
		for (int i=0; i<(playerBullets.size()); i++) //iterates through the playerBullets ArrayList
		{
			playerBullets.get(i).draw(g2d); //runs the draw method of each object in the ArrayList
		}
		
		
	}
	
	private void updategame()
	{
		player.update(map); //runs the player object's update method
		for (int i=0; i<(playerBullets.size()); i++) //iterates through the ArrayList
		{
			playerBullets.get(i).update(map); //runs the update method for each bullet
			if (playerBullets.get(i).offScreen) //if the bullet's offscreen attribute is true
				playerBullets.remove(i); //remove it from the ArrayList, since this is its only reference,
					//the garbage collector should remove it
			else if (playerBullets.get(i).dead) //if the bullet's offscreen attribute is true
				playerBullets.remove(i); //remove it from the ArrayList, since this is its only reference,
					//the garbage collector should remove it
		}
		for (int i=0; i<player.mines.size(); i++)
		{
			player.mines.get(i).update();
		}
	}
	
	public Game() { //constructor that adds a key and mouse listener for control of the game
		
		try {
			Map map2 = new Map("level1.json", 32, player);
			map = map2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) { //when a key is released
				player.keyReleased(e); //run the player method keyReleased
			}

			@Override
			public void keyPressed(KeyEvent e) { //when a key is being pushed down
				player.keyPressed(e); //run the player method KeyPressed
			}
		});
		addMouseListener(new MouseListener() { //this is a mouse listener to detect mouse inputs

			@Override
			public void mouseClicked(MouseEvent e) { //when any of the mouse buttons are clicked
				int xclick = e.getX(); //x coordinate of the click
				int yclick = e.getY(); //y coordinate of the click
				double angle = Math.atan2(player.y-yclick, player.x-xclick)+Math.PI;
				//gets the angle between the player and the click
				playerBullets.add(new PlayerBullet((player.x), (player.y), 2, angle)); //spawns a new player bullet
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
		});
		setFocusable(true); //allows both of the listeners to be focused
	}
	
}
