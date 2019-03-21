package main;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

public class Text {
	
	private int x, y, width, height;
	private Font font;
	private FontMetrics fm;
	
	private int yOffset;
	
	private String[] words;
	private ArrayList<String> lines = new ArrayList<>();
	
	public Text(int x, int y, int width, int height, String text, Font font) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.font = font;
		
		Canvas c = new Canvas();
		fm = c.getFontMetrics(font);
		yOffset = fm.getHeight();
		
		words = text.split(" ");
		String line = "";
		for (int i = 0; i < words.length; i++) {
			String w = words[i];
			if (fm.stringWidth(line) + fm.stringWidth(" " + w) > width) {
				lines.add(line);
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
		for (String w : lines) {
			System.out.println(w);
		}
	}
	
	public void render(Graphics g) {
		g.setFont(font);
		g.drawRect(x, y, width, height);
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			g.drawString(line, x , y + yOffset + fm.getHeight() * i);
		}
	}
}
