package main;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

//generic class to draw long text on screen to fit inside a rectangle
public class Text {
	
	private int x, y;
	private Font font;
	private FontMetrics fm;
	
	private int yOffset;
	
	private String[] words;
	//list of lines to draw
	private ArrayList<String> lines = new ArrayList<>();
	//x offsets based on alignment
	private ArrayList<Integer> xOffsets = new ArrayList<>();
	
	//alignment options
	public static final int LEFT = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;
	
	public Text(int x, int y, int width, int height, String text, Font font, int alignment) {
		this.x = x;
		this.y = y;
		this.font = font;
		
		Canvas c = new Canvas();
		fm = c.getFontMetrics(font);
		yOffset = fm.getHeight();
		
		//split up text and determine where to put line breaks
		words = text.split(" ");
		String line = "";
		for (int i = 0; i < words.length; i++) {
			String w = words[i];
			if (fm.stringWidth(line) + fm.stringWidth(" " + w) > width) {
				lines.add(line);
				if (alignment == LEFT) {
					xOffsets.add(0);
				} else if (alignment == CENTER) {
					xOffsets.add((width - fm.stringWidth(line))/2);
				} else if (alignment == RIGHT) {
					xOffsets.add(width - fm.stringWidth(line));
				}
				line = w;
			} else {
				if (i == 0) {
					line += w;
				} else {
					line += " " + w;
				}
			}
		}
		lines.add(line);
		if (alignment == LEFT) {
			xOffsets.add(0);
		} else if (alignment == CENTER) {
			xOffsets.add((width - fm.stringWidth(line))/2);
		} else if (alignment == RIGHT) {
			xOffsets.add(width - fm.stringWidth(line));
		}
	}
	
	public void render(Graphics g) {
		g.setFont(font);
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			g.drawString(line, x + xOffsets.get(i), y + yOffset + (yOffset * i));
		}
	}
}
