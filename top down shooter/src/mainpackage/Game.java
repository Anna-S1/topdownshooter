package mainpackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

@SuppressWarnings("serial")
public class Game extends JPanel 
{

	Player player = new Player();
	
	public static void main(String[] args) throws InterruptedException, IOException 
	{
		JFrame frame = new JFrame("Top Down Shooter");
		Game game = new Game();
		frame.add(game);
		frame.setSize(800,600);
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
		
	}
	
	private void updategame()
	{
		player.update();
	}
	
	public Game() { //constructor that adds a keylistener for control of the game
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
		setFocusable(true);
	}
	
}
