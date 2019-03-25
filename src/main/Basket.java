package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class Basket { // object for basket which collects letters for words

	private int x, y;
	private int width = 65, height = 30;
	public int score;

	public Basket() {
		x = 0;
		y = 0;
	}

	public void move(int[] mouse) {
		// make basket centered
		x = mouse[0] - width / 2;
		y = mouse[1] - height / 2;
	}

	public void render(Graphics g) {
		// draw black rectangle
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
	}

	public void checkBasketCollision(Tree b) {
		// go through each letter
		List<Letter> a = b.getLetterList();
		for (int i = 0; i < a.size(); i++) {
			Letter l = a.get(i);
			// check if its collides with any letters
			if (l.getX() < this.x + width && l.getX() > this.x && l.getY() < this.y + height && l.getY() > this.y) {
				// add the letter to the word and remove it from the screen
				b.push(l.getChar());
				a.remove(i);
			}
		}
	}
}
