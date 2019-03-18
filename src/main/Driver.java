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
	
	int[] mouse = new int[2];
	
	int screenWidth;
	int screenHeight;
	
	Input input = new Input();
	Tree tree = new Tree();
	Basket basket = new Basket();
	
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 1920, 1080);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica", Font.PLAIN, 32));
		if(!tree.getGameOver()) {
		g.drawString("Alphabet Tree!", 500, 500);
		basket.render(g);
		tree.render(g, screenWidth, screenHeight);
		}
		
		mouse = input.getMouse();
		if (input.getLeft()) {
			tree.submit();
		}
		if (input.getRight()) {
			tree.pop();
		}
		
		if(input.getSpaceBar()) {
			tree.pop();	
		}
		
		tree.generateLetter();
		tree.fall();
		tree.checkLetters();
		basket.move(mouse);
		basket.checkBasketCollision(tree.getLetterList(), tree);
		
		
		if(tree.getGameOver()) {
			g.drawString("ALL GOOD THINGS MUST COME TO AN END", 300, 700);
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
