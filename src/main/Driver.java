package main;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Driver extends JPanel implements ActionListener {
	
	Input input;
	Tree tree;
	
	public void paint(Graphics g) {
		int[] mouse = new int[2];
		mouse = input.getMouse();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}
	
	Timer t;
	public Driver() {
		input = new Input();
		tree = new Tree();
		
		JFrame f = new JFrame();
		f.setResizable(true);
		f.setSize(1920, 1080);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		f.add(this);
		f.addKeyListener(input);
		f.addMouseListener(input);
		f.addMouseMotionListener(input);
		
		t = new Timer(1000/60, this);
		t.start();
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Driver d = new Driver();
	}
}
