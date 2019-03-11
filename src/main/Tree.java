package main;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Tree {
	
	private Stack<Letter> letters;
	private int wordValue;
	
	private HashMap <Character, Integer> values = new HashMap<Character, Integer>();
	
	public Tree() {
		letters = new Stack<>();
		wordValue = 0;
		Scanner scan = new Scanner("letter_values.txt");
		while (scan.hasNext()) {
			values.put(scan.next().charAt(0), scan.nextInt());
		}
		scan.close();
	}

	public void push(Letter l) {
		letters.push(l);
	}
	
	public void pop() {
		if (!letters.isEmpty()) {
			letters.pop();
		}
	}
	
	public int calculateValue() {
		int value = 0;
		for (Letter l : letters) {
			value += values.get(l.getChar());
		}
		return value;
	}
}
