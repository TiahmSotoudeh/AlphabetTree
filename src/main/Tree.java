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
	
	private static final int LETTER_COOLDOWN = 60;
	
	private Stack<Character> word;
	private int wordValue, totalScore;
	private int treeX, treeY;
	private List<Letter> letters;
	private int letterTimer = LETTER_COOLDOWN;
	private HashMap <Character, Integer> values = new HashMap<Character, Integer>();
	private HashSet<String> dictionary = new HashSet<>();
	private int vowelTimer = 2;
	private char[] vowels = {'A', 'E', 'I', 'O', 'U'};
	private int lettersGenerated;
	private int stagepoints;
	private boolean gameOver;
	
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
		if (lettersGenerated < 100) {
			if (letterTimer > 0) {
				letterTimer--;
			} else if (vowelTimer == 0) {
				char c = vowels[(int)(Math.random() * vowels.length)];
				int x = (int)(Math.random()*1900);
				int y = (int)(Math.random()*51);
				letters.add(new Letter(c, x, y));
				vowelTimer = 2;
			} else {
				vowelTimer--;
				char c = (char)((int)(Math.random() * 26) + 65);
				int x = (int)(Math.random()*1900);
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
	
	public void submit() {
		if (checkWord()) {
			word.clear();
			totalScore += wordValue;
			stagepoints += wordValue;
			wordValue = 0;
		}
	}
	
	public void checkLetters(){
		if(lettersGenerated>=100) {
			if(stagepoints<10) {
				gameOver = true;
			}else {
				gameOver = false;
				lettersGenerated = 0;
			}
		}
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
		String s = stackToString(word);
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

	public int getStagepoints() {
		return stagepoints;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}

}
