package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Driver extends JPanel implements ActionListener {
	
	private int[] mouse = new int[2];
	private int[] click = new int[2];
	
	private int screenWidth;
	private int screenHeight;
	
	private int stageScore = 0;
	
	private boolean gameOver = false;
	private boolean menu = true;
	
	private Input input = new Input();
	private Tree tree = new Tree();
	private Basket basket = new Basket();
	private int gracePeriod = 120;
	
	private Button play = new Button(1920/2 - 100, 400, 200, 100, "a", new Font("Helvetica", Font.PLAIN, 40));
	private Text text = new Text(100, 100, 100, 100, "a b c d e f g ewa fdsasdfuiahfekaa ewuff", new Font("Helvetica", Font.PLAIN, 20));
	
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 1920, 1080);
		
		mouse = input.getMouse();
		click = input.getClick();
		
		if (menu) {
			play.render(g);
			if (play.clicked(click)) {
				menu = false;
			}
			text.render(g);
		} else {
			if (gameOver) {
				g.setFont(new Font("Helvetica", Font.PLAIN, 30));
				g.setColor(Color.BLACK);
				g.drawString("ALL GOOD THINGS MUST COME TO AN END", 300, 700);
			} else {
				
				if (input.getLeft()) {
					stageScore += tree.submit();
				}
				if (input.getRight()) {
					tree.pop();
				}
				if(input.getSpaceBar()) {
					tree.pop();	
				}
				
				
				tree.fall();
				basket.move(mouse);
				basket.checkBasketCollision(tree);
				tree.generateLetter();
				basket.render(g);
				tree.render(g, screenWidth, screenHeight);
				
				if (tree.getLettersGenerated() >= tree.getLevelCap()) {
					if (stageScore < 10) {
						gracePeriod--;
						if(gracePeriod==0) {
							gameOver = true;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	Timer t;
	public Driver() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setResizable(false);
		f.setUndecorated(true);
		f.setBackground(Color.LIGHT_GRAY);
		f.setVisible(true);
		
		f.add(this);
		f.addKeyListener(input);
		f.addMouseListener(input);
		f.addMouseMotionListener(input);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
		
		t = new Timer(1000/60, this);
		t.start();
	}
	
	public static void main(String[] args) throws IOException{
		@SuppressWarnings("unused")
		Driver d = new Driver();
		
	}
	
}
