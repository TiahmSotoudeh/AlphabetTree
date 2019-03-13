package main;

import java.awt.Color;
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
			values.put(scan.next().charAt(0), scan.nextInt());
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
		char c = (char)((int)(Math.random() * 26) + 65);
		int x = (int)(Math.random()*51);
		int y = (int)(Math.random()*51);
		letters.add(new Letter(c, x, y));
	}
	
	public void fall() {
		for (Letter l : letters) {
			l.falling();
		}
		letters.removeIf(l -> l.getY() > 1080);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 1920, 1080);
		for (Letter l : letters) l.render(g);
	}
	
	public int getWordValue() {
		return wordValue;
	}
}
