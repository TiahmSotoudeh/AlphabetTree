package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
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

	private static final int LETTER_COOLDOWN = 20;

	private Stack<Character> word; // word in the stack of inputted letters
	private int wordValue, totalScore; // keeping track of game scores
	private List<Letter> letters; // letters which are valid
	private int letterTimer = LETTER_COOLDOWN;
	private HashMap<Character, Integer> values = new HashMap<Character, Integer>(); // values of the letters
	private HashSet<String> dictionary = new HashSet<>(); // valid words dictionary

	private int vowelTimer = 2;
	private char[] vowels = { 'A', 'E', 'I', 'O', 'U' }; // vowels so that they can have higher frequency
	private int levelCap;
	private int lettersGenerated; // random
	private int seasonState; // current season based on timer
	private BufferedImage sprite; // sprite sheet with trees
	private BufferedImage image; // individual tree image

	public Tree() {
		word = new Stack<>();
		levelCap = 100;
		wordValue = 0;
		totalScore = 0;
		letters = new ArrayList<>();
		seasonState = 0;
		Scanner scan = null;
		lettersGenerated = 0;

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
			sprite = ImageIO.read(new File("src/resources/season-trees-spritesheet.png")); // getting file of sprite
																							// sheet of tree images

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Letter> getLetterList() {
		return letters;
	}

	public void push(char l) {
		word.push(l);
		wordValue += values.get(l);
	}

	public void pop() { // popping top letter from stack
		if (!word.isEmpty()) {
			wordValue -= values.get(word.pop());
		}
	}

	public void generateLetter() { // generates a random letter, with more vowels than consonants
		if (lettersGenerated < levelCap) {
			if (letterTimer > 0) {
				letterTimer--; // on a timer so that the vowels are more frequent
			} else if (vowelTimer == 0) {
				char c = vowels[(int) (Math.random() * vowels.length)];
				int x = (int) (Math.random() * 1000) + 300;
				int y = (int) (Math.random() * 51);
				letters.add(new Letter(c, x, y));
				vowelTimer = 2;
				lettersGenerated++;
			} else {
				vowelTimer--;
				char c = (char) ((int) (Math.random() * 26) + 65); // int to char casting so it's v random
				int x = (int) (Math.random() * 1000) + 300;
				int y = (int) (Math.random() * 51);
				letters.add(new Letter(c, x, y));
				letterTimer = LETTER_COOLDOWN;
				lettersGenerated++;
			}
		}
	}

	public void fall() { // velocity of letters falling from top to bottom, removed when out of the
							// screen
		for (Letter l : letters) {
			l.falling();
		}
		letters.removeIf(l -> l.getY() > 1080);
	}

	private String stackToString(Stack<Character> stack) { // converts letters in stack to Strings to display
		Character[] c = stack.toArray(new Character[0]);
		String s = "";
		for (char ch : c) {
			s += ch;
		}
		return s;
	}

	public boolean checkWord() { // if the word is valid
		if (word.size() <= 2) // can't be only two chars
			return false;
		return dictionary.contains(stackToString(word));
	}

	public int submit() { // submit the word in the bank after checking if it's valid
		if (checkWord()) {
			word.clear();
			totalScore += wordValue; // word score resets and total score is added to
			int temp = wordValue;
			wordValue = 0;
			return temp;
		}
		return 0;
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) { // resizes the sprite sheet and any
																				// buffered image to magnify for
																				// graphics display
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	public BufferedImage changeTree(int day) { // crops the sprite sheet to a certain tree based on desired season and
												// also scales it
		int scale = 25;
		BufferedImage img = sprite.getSubimage(day * 64, 0, 64, 43); // fill in the corners of the desired crop location
																		// here
		img = resize(img, img.getWidth() * scale, img.getHeight() * scale);
		this.image = img;
		return img;
	}

	public void render(Graphics gd, int screenWidth, int screenHeight) { // drawing everything on screen
		Graphics2D g = (Graphics2D) gd; // using graphics2d so that we can work with bufferedimage

		g.drawImage(image, screenWidth / 2 - image.getWidth() / 2 + image.getWidth() / 8, 0, null); //

		g.setColor(Color.YELLOW); // yellow text so that it shows up!

		for (Letter l : letters)
			l.render(g);

		g.setFont(new Font("Helvetica", Font.BOLD, 60));
		if (checkWord()) {
			g.setFont(new Font("Helvetica", Font.BOLD | Font.ITALIC, 60)); // italicizes valid words
		}

		FontMetrics fm = g.getFontMetrics();
		String s = stackToString(word);
		int x = (screenWidth - fm.stringWidth(s)) / 2;
		g.drawString(s, x, screenHeight - 80);

		g.setFont(new Font("Helvetica", Font.PLAIN, 20));
		fm = g.getFontMetrics();
		s = "Word Score: " + Integer.toString(wordValue);
		x = (screenWidth - fm.stringWidth(s)) / 2;
		g.drawString(s, x, screenHeight - 40); // draws the string with the updated wordScore

		g.setFont(new Font("Helvetica", Font.PLAIN, 20));
		fm = g.getFontMetrics();
		s = "Letters Left: " + Integer.toString(levelCap - lettersGenerated); // how many letters left until gameover
		x = (screenWidth / 10 - fm.stringWidth(s));
		g.drawString(s, x, screenHeight - (screenHeight * 7) / 8);

		g.setFont(new Font("Helvetica", Font.PLAIN, 72));
		fm = g.getFontMetrics();
		s = "Total Score: " + Integer.toString(totalScore);
		x = (screenWidth - fm.stringWidth(s)) / 2;
		g.drawString(s, x, 100); // prints total score

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
		return false; // needed for the bufferedimage object
	}

	public int getSeasonState() { // season with respect to timer and game position
		return seasonState;
	}

	public void addSeasonState() {
		this.seasonState++;
	}
}
