package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Tree {
	
	private Stack<Character> word;
	private int wordValue;
	private int treeX, treeY;
	private List<Letter> letters;
	private int letterTimer = 60;
	private HashMap <Character, Integer> values = new HashMap<Character, Integer>();
	private HashSet<String> dictionary = new HashSet<>();
	
	public Tree() {
		word = new Stack<>();
		wordValue = 0;
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
		} else {
			char c = (char)((int)(Math.random() * 26) + 65);
			int x = (int)(Math.random()*1921);
			int y = (int)(Math.random()*51);
			letters.add(new Letter(c, x, y));
			letterTimer = 60;
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
		if (word.size() <= 3) return false;
		return dictionary.contains(stackToString(word));
	}
	
	public int submit() {
		if (checkWord()) {
			word.clear();
			int temp = wordValue;
			wordValue = 0;
			return temp;
		}
		return 0;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 1920, 1080);
		
		for (Letter l : letters) l.render(g);
		
		g.setFont(new Font("Helvetica", Font.BOLD, 60));
		FontMetrics fm = g.getFontMetrics();
		String s = stackToString(word);
		int x = (1920 - fm.stringWidth(s))/2;
		g.drawString(s, x, 1000);
		g.setFont(new Font("Helvetica", Font.PLAIN, 30));
		fm = g.getFontMetrics();
		s = Integer.toString(wordValue);
		x = (1920 - fm.stringWidth(s))/2;
		g.drawString(s, x, 1040);
	}
	
	public int getWordValue() {
		return wordValue;
	}
}
