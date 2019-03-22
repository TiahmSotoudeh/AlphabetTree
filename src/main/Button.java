package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

//generic class to create and draw buttons
public class Button {
	
	private int x, y, width, height;
	private String text;
	private Font font;
	private FontMetrics fm;
	
	private int xOffset, yOffset;
	
	public Button(int x, int y, int width, int height, String text, Font font) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.font = font;
		Canvas c = new Canvas();
		fm = c.getFontMetrics(font);
		xOffset = (width - fm.stringWidth(text))/2;
		yOffset = (height - fm.getHeight())/2 + fm.getAscent();
	}
	
	//check if the button was clicked
	public boolean clicked(int[] mouse) {
		return mouse[0] > x && mouse[0] < x + width && mouse[1] > y && mouse[1] < y + width;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		g.setFont(font);
		g.drawString(text, x + xOffset, y + yOffset);
	}
}
