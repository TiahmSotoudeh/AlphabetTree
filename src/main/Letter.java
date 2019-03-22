package main;

import java.awt.Font;
import java.awt.Graphics;

//stores a falling letter and its x and y position
public class Letter {	
	private int x, y;
	private char c;
	
	public Letter(char c, int x, int y) {
		this.c = c;
		this.x = x;
		this.y = y;
	}
	
	public char getChar() {
		return c;
	}
	
	public void falling() {
		y += 10;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void render(Graphics g) {
		g.setFont(new Font("Helvetica", Font.BOLD, 40));
		g.drawString(Character.toString(c), x, y);
	}
}
