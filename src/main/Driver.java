package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Driver extends JPanel implements ActionListener {
	
	private int[] mouse = new int[2];
	
	private int screenWidth;
	private int screenHeight;
	
	private int stageScore = 0;
	
	private boolean gameOver = false;
	
	private Input input = new Input();
	private Tree tree = new Tree();
	private Basket basket = new Basket();
	private int gracePeriod = 120;
	private int day = 0;
	private int count = 0;
	
	public void paint(Graphics g) {
		
		try {
			g.drawImage(ImageIO.read(new File("src/resources/pixelbg.png")), 0, 0, screenWidth, screenHeight, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if (gameOver) {
			g.setFont(new Font("Helvetica", Font.PLAIN, 30));
			g.setColor(Color.BLACK);
			g.drawString("ALL GOOD THINGS MUST COME TO AN END", 300, 700);
		} else {
			mouse = input.getMouse();
			if (input.getLeft()) {
				stageScore += tree.submit();
			}
			if (input.getRight()) {
				tree.pop();
			}
			if(input.getSpaceBar()) {
				tree.pop();	
			}
			count++;
			if (count%100 == 0)
			day++;
			//tree.resize(2);
			tree.changeTree(day);
			tree.render(g, screenWidth, screenHeight);

			tree.fall();
			basket.move(mouse);
			basket.checkBasketCollision(tree);
			tree.generateLetter();
			basket.render(g);
			
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
