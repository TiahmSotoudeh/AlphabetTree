package main;

import java.util.HashMap;
import java.util.Scanner;

public class Letter {
	int x, y;
	String c;
	int value;
	HashMap <String, Integer> values = new HashMap<String, Integer>();

	
	public Letter(String c, int x, int y) {
		this.c = c;
		this.x = x;
		this.y = y;
		this.value = values.get(c);

	}
	
	public void makeValuesMap() {
		Scanner scan = new Scanner("letter_values.txt");
		while (scan.hasNext()) {
		values.put(scan.next(), scan.nextInt());
		}
		scan.close();
	}
	
	public int calcValues(String word) {
		int total = 0;
		for (int i = 0; i < word.length()-1; i++) {
			total += values.get(word.substring(i, i+1));
		}
		return total;
	}
	
	

}
