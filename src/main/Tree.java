package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Tree implements ImageObserver {
	
	private static final int LETTER_COOLDOWN = 60;
	
	private Stack<Character> word;
	private int wordValue, totalScore;
	private int treeX, treeY;
	private List<Letter> letters;
	private int letterTimer = LETTER_COOLDOWN;
	private HashMap <Character, Integer> values = new HashMap<Character, Integer>();
	private HashSet<String> dictionary = new HashSet<>();
	private int vowelTimer=2;
	private ArrayList<Character> vowels = new ArrayList<Character>();
	private ArrayList<BufferedImage> trees;

	
	public Tree() {
		word = new Stack<>();
		wordValue = 0;
		totalScore = 0;
		treeX = 50;
		treeY = 50;
		letters = new ArrayList<>();
		Scanner scan = null;
		try {
			scan = new Scanner(new File("src/resources/letter_values.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while (scan.hasNext()) {
			values.put(scan.next().toLowerCase().charAt(0), scan.nextInt());
		}
		
		try {
			scan = new Scanner(new File("src/resources/dict.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while (scan.hasNext()) {
			dictionary.add(scan.next());
		}
		
		scan.close();
		
		vowels.add('a');
		vowels.add('e');
		vowels.add('i');
		vowels.add('o');
		vowels.add('u');
		
		BufferedImage one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen;
		try {
			one = ImageIO.read(new File("src/resources/Growing_Tree1.png"));
			two = ImageIO.read(new File("src/resources/Growing_Tree2.png"));
			three = ImageIO.read(new File("src/resources/Budding_Tree.png"));
			four = ImageIO.read(new File("src/resources/Flowered_Tree.png"));
			five = ImageIO.read(new File("src/resources/Apple_Tree.png"));
			six = ImageIO.read(new File("src/resources/Fall_Tree1.png"));
			seven = ImageIO.read(new File("src/resources/Fall_Tree2.png"));
			eight = ImageIO.read(new File("src/resources/Fall_Tree3.png"));
			nine = ImageIO.read(new File("src/resources/Fall_Tree4.png"));
			ten = ImageIO.read(new File("src/resources/Fall_Tree5.png"));
			eleven = ImageIO.read(new File("src/resources/Fall_Tree6.png"));
			twelve = ImageIO.read(new File("src/resources/Fall_Tree7.png"));
			thirteen = ImageIO.read(new File("src/resources/Leafless_Tree.png"));
			trees.add(one); trees.add(two); trees.add(three); trees.add(four); trees.add(five); trees.add(six); trees.add(seven);
			trees.add(eight); trees.add(nine); trees.add(ten); trees.add(eleven); trees.add(twelve); trees.add(thirteen);
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
		if (letterTimer > 0) {
			letterTimer--;
		} else if(vowelTimer==0){
			char c = vowels.get((int) (Math.random()*4));
			c = Character.toUpperCase(c);
			int x = (int)(Math.random()*1900);
			int y = (int)(Math.random()*51);
			letters.add(new Letter(c, x, y));
			vowelTimer = 2;
		}else {
			vowelTimer--;
			char c = (char)((int)(Math.random() * 26) + 65);
			int x = (int)(Math.random()*1900);
			int y = (int)(Math.random()*51);
			letters.add(new Letter(c, x, y));
			letterTimer = LETTER_COOLDOWN;
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
	
	public void submit() {
		if (checkWord()) {
			word.clear();
			totalScore += wordValue;
			wordValue = 0;
		}
	}
	
	public BufferedImage resize(Graphics g, BufferedImage img, double scale) {
		BufferedImage result = new BufferedImage((int) (img.getWidth() * scale),
                (int) (img.getHeight() * scale), BufferedImage.TYPE_INT_ARGB);
		
		return result;		
	}
	
	public BufferedImage changeTree(int day) {
		return trees.get(day);
	}
	
	public void render(Graphics g, int screenWidth, int screenHeight) {
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 1920, 1080);
		
		for (Letter l : letters) l.render(g);
		
		g.setFont(new Font("Helvetica", Font.BOLD, 60));
		if (checkWord()) {
			g.setFont(new Font("Helvetica", Font.BOLD|Font.ITALIC, 60));
		}

		FontMetrics fm = g.getFontMetrics();
		String s = stackToString(word).toUpperCase();
		int x = (screenWidth - fm.stringWidth(s))/2;
		g.drawString(s, x, screenHeight - 80);
		
		g.setFont(new Font("Helvetica", Font.PLAIN, 20));
		fm = g.getFontMetrics();
		s = "Word Score: " + Integer.toString(wordValue);
		x = (1920 - fm.stringWidth(s))/2;
		g.drawString(s, x, screenHeight - 40);
		
		g.setFont(new Font("Helvetica", Font.PLAIN, 72));
		fm = g.getFontMetrics();
		s = "Total Score: " + Integer.toString(totalScore);
		x = (screenWidth - fm.stringWidth(s))/2;
		g.drawString(s, x, 100);
	}
	
	public int getWordValue() {
		return wordValue;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
}
