package main;

import java.awt.Graphics;

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
		y+=5;
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
		g.drawString(Character.toString(c), x, y);
	}
}
