package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Driver extends JPanel implements ActionListener {
	
	private int[] mouse = new int[2];
	
	private int screenWidth;
	private int screenHeight;
	
	private int stageScore = 0;
	
	private int lettersGenerated = 0;
	private boolean gameOver = false;
	
	private Input input = new Input();
	private Tree tree = new Tree();
	private Basket basket = new Basket();
	
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 1920, 1080);
		
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
			
			if (tree.generateLetter()) {
				lettersGenerated++;
			}
			
			tree.fall();
			basket.move(mouse);
			basket.checkBasketCollision(tree);
			
			basket.render(g);
			tree.render(g, screenWidth, screenHeight);
			
			if (lettersGenerated >= 100) {
				if (stageScore < 10) {
					gameOver = true;
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
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Driver d = new Driver();
	}
}
