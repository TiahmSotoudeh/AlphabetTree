package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
	
	private int[] mouse = new int[2];
	private boolean leftClick;
	private boolean rightClick;
	
	public int[] getMouse() {
		return mouse;
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
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftClick = true;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			rightClick = true;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouse[0] = x;
		mouse[1] = y;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}
