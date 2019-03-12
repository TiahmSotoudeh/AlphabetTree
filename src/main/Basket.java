package main;

import java.awt.Color;
import java.awt.Graphics;

public class Basket {
	
	private int x, y;
	private int width = 100, height = 50;

	public Basket() {
		x = 0;
		y = 0;
	}
	
	public void move(int[] mouse) {
		x = mouse[0] - width/2;
		y = mouse[1] - height/2;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
	}
}
