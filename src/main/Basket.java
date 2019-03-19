package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class Basket {
	
	private int x, y;
	private int width = 65, height = 30;
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
	
	public void checkBasketCollision(Tree b) {
		List<Letter> a = b.getLetterList();
		for (int i = 0; i < a.size(); i++) {
			Letter l = a.get(i);
			if(l.getX() < this.x + width && l.getX() > this.x && l.getY() < this.y + height && l.getY() > this.y ) {
				b.push(l.getChar());
				a.remove(i);
			}
		}
	}
}
