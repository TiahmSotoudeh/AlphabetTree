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
}
