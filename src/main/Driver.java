package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Driver extends JPanel implements ActionListener {

	private int[] mouse = new int[2];
	private int[] click = new int[2];

	private int screenWidth;
	private int screenHeight;

	private int stageScore = 0; // total score as displayed at the top

	private boolean gameOver = false; // boolean to keep track of game session
	private boolean menu = true; // menu

	private Input input = new Input(); // input stack of letters
	private Tree tree = new Tree(); // has everything in it
	private Basket basket = new Basket(); // basket to collect letters
	private int gracePeriod = 120; // how long you have to get a score without dying
	private int day = 0; // counter for tree cropper on sprite sheet for seasons
	private int count = 0; // counter for frames for trees

	private Button play = new Button(1920 / 2 - 100, 400, 200, 100, "Play", new Font("Helvetica", Font.PLAIN, 40));
	
	Image bg;

	public void paint(Graphics g) {
		mouse = input.getMouse();
		click = input.getClick();
		
		g.drawImage(bg, 0, 0, screenWidth, screenHeight, null);

		if (menu) {
			play.render(g);
			if (play.clicked(click)) {
				menu = false;
			}
		} else {
			if (gameOver) {
				g.setFont(new Font("Helvetica", Font.PLAIN, 30));
				g.setColor(Color.BLACK);
				g.drawString("ALL GOOD THINGS MUST COME TO AN END", 300, 700);
			} else {

				if (input.getLeft()) {
					stageScore += tree.submit();
				}
				if (input.getRight()) {
					tree.pop();
				}
				if (input.getSpaceBar()) {
					tree.pop();
				}

				count++;
				if (count % 100 == 0)
					day++;
				// tree.resize(2);
				tree.changeTree(day);
				tree.render(g, screenWidth, screenHeight);

				tree.fall();
				basket.move(mouse);
				basket.checkBasketCollision(tree);
				tree.generateLetter();
				basket.render(g);

				if (tree.getLettersGenerated() >= tree.getLevelCap()) {
					if (stageScore < 10) {
						gracePeriod--;
						if (gracePeriod == 0) {
							gameOver = true;
						}
					} else if (gameOver == false) {
						gracePeriod--;
						g.setFont(new Font("Helvetica", Font.PLAIN, 60));
						g.setColor(Color.BLACK);
						g.drawString("THE NEXT MONTH ARRIVES", 365, 450);
						if (gracePeriod == 0) {
							tree.addSeasonState();
							tree.setLettersGenerated(0);
							gracePeriod = 120;
						}
					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}

	Timer t;

	public Driver() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setResizable(false);
		f.setUndecorated(true);
		f.setBackground(Color.LIGHT_GRAY);
		f.setVisible(true);
		
		//add keyboard and mouse input
		f.add(this);
		f.addKeyListener(input);
		f.addMouseListener(input);
		f.addMouseMotionListener(input);
		
		//get screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
		
		//load image
		try {
			bg = ImageIO.read(new File("src/resources/pixelbg.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		t = new Timer(1000 / 60, this);
		t.start();
	}

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		Driver d = new Driver();

	}

}
