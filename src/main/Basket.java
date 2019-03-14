package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class Basket {
	
	private int x, y;
	private int width = 100, height = 50;
	public int score;

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
	
	public void checkBasketCollision(List<Letter> a, Tree b) {
		for(int i=0;i<a.size();i++) {
			if(a.get(i).getX() < this.x + width && a.get(i).getX() > this.x && a.get(i).getY() < this.y + height && a.get(i).getY() > this.y ) {
				b.push(a.get(i).getChar());
				a.remove(i);
			}
		}
	}
}
