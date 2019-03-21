package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;

public class Tree implements ImageObserver {
	
	private static final int LETTER_COOLDOWN = 60;
	
	private Stack<Character> word;
	private int wordValue, totalScore;
	private List<Letter> letters;
	private int letterTimer = LETTER_COOLDOWN;
	private HashMap <Character, Integer> values = new HashMap<Character, Integer>();
	private HashSet<String> dictionary = new HashSet<>();

	private int vowelTimer=2;
	private char[] vowels = {'A', 'E', 'I', 'O', 'U'};
	private int levelCap;
	private int lettersGenerated;
	private int seasonState;
	private BufferedImage sprite;
	private BufferedImage image;
	
	public Tree() {
		word = new Stack<>();
		levelCap = 100;
		wordValue = 0;
		totalScore = 0;
		letters = new ArrayList<>();
		seasonState = 0;
		Scanner scan = null;
		lettersGenerated=0;
		
		try {
			scan = new Scanner(new File("src/resources/letter_values.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while (scan.hasNext()) {
			values.put(scan.next().charAt(0), scan.nextInt());
		}
		
		try {
			scan = new Scanner(new File("src/resources/dict.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while (scan.hasNext()) {
			dictionary.add(scan.next().toUpperCase());
		}
		
		scan.close();	
		
		try {
			sprite = ImageIO.read(new File("src/resources/season-trees-spritesheet.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<Letter> getLetterList(){
		return letters;
	}

	public void push(char l) {
		word.push(l);
		wordValue += values.get(l);
	}
	
	public void pop() {
		if (!word.isEmpty()) {
			wordValue -= values.get(word.pop());
		}
	}
	
	public void generateLetter() {
		if(lettersGenerated<levelCap) {
			if (letterTimer > 0) {
				letterTimer--;
			} else if (vowelTimer == 0) {
				char c = vowels[(int)(Math.random() * vowels.length)];
				int x = (int)(Math.random()*500);
				int y = (int)(Math.random()*51);
				letters.add(new Letter(c, x, y));
				vowelTimer = 2;
				lettersGenerated++;
			} else {
				vowelTimer--;
				char c = (char)((int)(Math.random() * 26) + 65);
				int x = (int)(Math.random()*500)+300;
				int y = (int)(Math.random()*51);
				letters.add(new Letter(c, x, y));
				letterTimer = LETTER_COOLDOWN;
				lettersGenerated++;
			}
		}
	}
	
	public void fall() {
		for (Letter l : letters) {
			l.falling();
		}
		letters.removeIf(l -> l.getY() > 1080);
	}
	
	private String stackToString(Stack<Character> stack) {
		Character[] c = stack.toArray(new Character[0]);
		String s = "";
		for (char ch : c) {
			s += ch;
		}
		return s;
	}
	
	public boolean checkWord() {
		if (word.size() <= 2) return false;
		return dictionary.contains(stackToString(word));
	}
	
	public int submit() {
		if (checkWord()) {
			word.clear();
			totalScore += wordValue;
			int temp = wordValue;
			wordValue = 0;
			return temp;
		}
		return 0;
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  

	public BufferedImage changeTree(int day) {
		int scale = 20;
		BufferedImage img = sprite.getSubimage(day * 64, 0, 64, 43); //fill in the corners of the desired crop location here
		img = resize(img, img.getWidth()*scale, img.getHeight()*scale);
		this.image = img;
		return img;
	}
	
	public void render(Graphics g, int screenWidth, int screenHeight) {
		g.drawImage(image, 0, 0, null);

		g.setColor(Color.BLACK);
		
		for (Letter l : letters) l.render(g);
		
		g.setFont(new Font("Helvetica", Font.BOLD, 60));
		if (checkWord()) {
			g.setFont(new Font("Helvetica", Font.BOLD|Font.ITALIC, 60));
		}

		FontMetrics fm = g.getFontMetrics();
		String s = stackToString(word);
		int x = (screenWidth - fm.stringWidth(s))/2;
		g.drawString(s, x, screenHeight - 80);
		
		g.setFont(new Font("Helvetica", Font.PLAIN, 20));
		fm = g.getFontMetrics();
		s = "Word Score: " + Integer.toString(wordValue);
		x = (screenWidth - fm.stringWidth(s))/2;
		g.drawString(s, x, screenHeight - 40);
		
		g.setFont(new Font("Helvetica", Font.PLAIN, 20));
		fm = g.getFontMetrics();
		s = "Letters Left: " + Integer.toString(levelCap-lettersGenerated);
		x = (screenWidth/10 - fm.stringWidth(s));
		g.drawString(s, x, screenHeight - (screenHeight*7)/8);
		
		g.setFont(new Font("Helvetica", Font.PLAIN, 72));
		fm = g.getFontMetrics();
		s = "Total Score: " + Integer.toString(totalScore);
		x = (screenWidth - fm.stringWidth(s))/2;
		g.drawString(s, x, 100);
		
		
	}

	public int getLevelCap() {
		return levelCap;
	}

	public void setLevelCap(int levelCap) {
		this.levelCap = levelCap;
	}
	
	public void setLettersGenerated(int levelCap) {
		this.lettersGenerated = levelCap;
	}
	
	public int getLettersGenerated() {
		return lettersGenerated;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

	public int getSeasonState() {
		return seasonState;
	}

	public void addSeasonState() {
		this.seasonState++;
	}
}
