package mainpackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

@SuppressWarnings("serial")
public class Game extends JPanel 
{

	public static final int WINDOWWIDTH = 800;
	public static final int WINDOWHEIGHT = 600;
	Player player = new Player();
	ArrayList<PlayerBullet> playerBullets = new ArrayList<>();
	
	public static void main(String[] args) throws InterruptedException, IOException 
	{
		JFrame frame = new JFrame("Top Down Shooter");
		Game game = new Game();
		frame.add(game);
		frame.setSize(WINDOWWIDTH,WINDOWHEIGHT);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//comment
		
		while (true)
		{
			game.updategame();
			game.repaint();
			Thread.sleep(10);
		}
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, 800, 600);
		g2d.setColor(Color.WHITE);
		player.draw(g2d);
		for (int i=0; i<(playerBullets.size()); i++)
		{
			playerBullets.get(i).draw(g2d);
		}
		
	}
	
	private void updategame()
	{
		player.update();
		for (int i=0; i<(playerBullets.size()); i++)
		{
			playerBullets.get(i).update();
		}
	}
	
	public Game() { //constructor that adds a key listener for control of the game
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				player.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				player.keyPressed(e);
			}
		});
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int xclick = e.getX();
				int yclick = e.getY();
				double angle = Math.atan2(player.y-yclick, player.x-xclick) + Math.PI/2;
				playerBullets.add(new PlayerBullet(player.x, player.y, 5, angle));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		setFocusable(true);
	}
	
}
