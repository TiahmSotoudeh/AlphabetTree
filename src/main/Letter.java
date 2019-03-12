package main;

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
	
	public void falling(Letter l) {
		l.setY(l.getY()+5);
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
}
