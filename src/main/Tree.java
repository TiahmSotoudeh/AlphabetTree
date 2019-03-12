package main;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

public class Tree {
	
	private Stack<Character> letters;
	private int wordValue;
	private int treeX, treeY;
	private HashMap <Character, Integer> values = new HashMap<Character, Integer>();
	private HashSet<String> dictionary = new HashSet<>();
	
	public Tree() {
		letters = new Stack<>();
		wordValue = 0;
		treeX = 50;
		treeY = 50;
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

	public void push(char l) {
		letters.push(l);
		wordValue += values.get(l);
	}
	
	public void pop() {
		if (!letters.isEmpty()) {
			wordValue -= values.get(letters.pop());
		}
	}
	public Letter generateLetter(){
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		int index = (int)(Math.random()*26);
		char c = alphabet.charAt(index);
		int x = (int)(Math.random()*51);
		int y = (int)(Math.random()*51);
		return new Letter(c, x, y);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 1920, 1080);
	}
	
	public int getWordValue() {
		return wordValue;
	}
}
