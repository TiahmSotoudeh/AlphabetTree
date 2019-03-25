package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener { // mouse methods to get inputs

	// mouse position
	private int[] mouse = new int[2];
	// mouse click position
	private int[] click = new int[2];
	// true if left mouse released
	private boolean leftClick;
	// true if right mouse released
	private boolean rightClick;
	// true if spacebar released
	private boolean spaceBar; // space bar also removes top letter from stack storage

	public int[] getMouse() {
		return mouse;
	}

	public int[] getClick() {
		int[] temp = click;
		click = new int[2];
		return temp;
	}

	public boolean getSpaceBar() {
		boolean temp = spaceBar;
		spaceBar = false;
		return temp;
	}

	public boolean getLeft() {
		boolean temp = leftClick;
		leftClick = false;
		return temp;
	}

	public boolean getRight() {
		boolean temp = rightClick;
		rightClick = false;
		return temp;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) { // left click removes letter, right click adds word
		click[0] = e.getX();
		click[1] = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1) {
			// button1 is left click
			leftClick = true;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			// button3 is right click
			rightClick = true;
		}
	}

	private void moveMouse(MouseEvent e) {
		mouse[0] = e.getX();
		mouse[1] = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		moveMouse(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		moveMouse(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// keycode 32 is spacebar
		if (e.getKeyCode() == 32) {
			spaceBar = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
