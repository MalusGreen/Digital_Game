import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import graphics.*;

public class Game extends JPanel implements ActionListener, KeyListener,
		MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timer timer;
	public static ArrayList<Bug> bugs = new ArrayList<>();
	public static ArrayList<ArrayList<Enemy>> enemies = new ArrayList<>();
	static ArrayList<Bug> selectedBugs = new ArrayList<>();
	// public static ArrayList<Bullet> bullets = new ArrayList<>();
	// public static ArrayList<Bullet> enemyBullets = new ArrayList<>();
	public static boolean left_clicked, right_clicked, shifted, space;
	private int mx, my; // movement
	private Rectangle dragBox; // Selection
	private int sx, sy;
	public static World map;
	public static int score;
	public static int click_count;
	public JButton pause, exit, combine;
	private ArrayList<JButton> selectButtons = new ArrayList<>();
	PointerInfo a = MouseInfo.getPointerInfo();
	Point b = a.getLocation();

	// BackGround
	public JPanel background_1, background_2;
	public Pixel[] pixels;
	public Pixel[] pixels_2;

	public Game() throws IOException {
		score = 0;
		click_count = 0;
		right_clicked = false;
		left_clicked = false;
		dragBox = new Rectangle();
		mx = 0;
		my = 0;
		sx = 0;
		sy = 0;

		addBugs(bugs);
		selectedBugs.add((Bug) bugs.get(bugs.size() - 1));
		selectedBugs.get(0).selected = true;
		map = new World("test.txt");
		System.out.println("level" + map.getLevel());

		setBackground(50);

		setLayout(null);
		combine = new PrettyBtn("COMBINE", 2);
		pause = new PrettyBtn("PAUSE", 2);
		exit = new PrettyBtn("MENU", 2);
		add(combine);
		add(pause);
		add(exit);
		for (int i = 0; i < 4; i++) {
			JButton newJB = new JButton();
			newJB.addKeyListener(this);
			newJB.setBorderPainted(false);
			selectButtons.add(newJB);
			add(newJB);
			newJB.addActionListener(this);
		}
		addKeyListener(this);
		combine.addActionListener(this);
		combine.addKeyListener(this);
		pause.addKeyListener(this);
		exit.addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(new Color(255, 255, 255));
		timer = new Timer(17, this);
	}

	public void addBugs(ArrayList<Bug> arr) {
		int size = (int) Math.sqrt(10);
		for (int i = 0; i < 1; i++) {
			bugs.add(new Bug(300 + i % size * Bug.size * 2 - size * Bug.size,
					100 + i * Bug.size * 2 / size - size * Bug.size));
			bugs.get(i).setType(1);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBG(g);
		map.draw(g);
		Unit.setXY(map.getX(), map.getY());
		drawUnits(g);// units

		/* -----------side menu ------------- */
		drawMenu(g);//Draw the Menu
		drawButtons(g);// draw invisible buttons to deselect bugs
		miniMap(g);// minimap
		drawBugIcons(g);// paint number of bugs, background, type,image for selected bugs
		clicked(g);// Shows where user clicks.
		dragBox(g);// Shows the box the user creates with left mouse click.
	}
	private void drawBG(Graphics g){
		for (int i = 0; i < pixels.length; i++) {
			pixels[i].draw(g, map.getX() / 2, map.getY() / 2);
			pixels_2[i].draw(g, map.getX() / 4, map.getY() / 4);
		}
	}

	private void drawUnits(Graphics g) {
		for (Bug i : bugs) {
			i.draw(g);
		}
		// for (Bullet i : bullets) {
		// i.draw(g);
		// }
		// for (Bullet i : enemyBullets) {
		// i.draw(g);
		// }
		for (Enemy i : enemies.get(map.getLevel()))
			i.draw(g);

	}

	private void drawButtons(Graphics g) {
		for (int i = 0; i < selectButtons.size(); i++) {
			selectButtons.get(i).setBounds(getWidth() - 189 + i % 2 * 88,
					getHeight() - 419 + i / 2 * 116, 75, 70);
		}
	}

	private void drawBugIcons(Graphics g) {
		g.drawString(bugs.size() + "", getWidth() - 120, getHeight() - 565);
		for (int i = 0; i < selectedBugs.size(); i++) {
			g.fillRect(getWidth() - 189 + i % 2 * 88, getHeight() - 535 + i / 2
					* 116, 75, 70);// background
			g.drawImage(selectedBugs.get(i).getImage(), getWidth() - 176 + i
					% 2 * 88, getHeight() - 525 + i / 2 * 116, Bug.size * 2,
					Bug.size * 2, null);// image
			g.drawString(selectedBugs.get(i).getType() + "", getWidth() - 129
					+ i % 2 * 88, getHeight() - 435 + i / 2 * 115);// type
																	// string
		}
	}

	private void miniMap(Graphics g) {
		int l = map.getLevel();
		if (l > 3) {
			System.out.println(">");
			l -= 4;
			g.fillOval(getWidth() - 195 + l * 55, getHeight() - 646 + 35, 10,
					10);
		} else
			g.fillOval(getWidth() - 195 + l * 55, getHeight() - 646, 10, 10);
	}

	public void drawMenu(Graphics g) {
		Image sm = new ImageIcon("side-menu.png").getImage();
		g.drawImage(sm, getWidth() - 220, 0, 220, getHeight(), null);
		combine.setBounds(getWidth() - 190, getHeight() - 190, 170, 30);
		pause.setBounds(getWidth() - 190, getHeight() - 130, 170, 30);
		exit.setBounds(getWidth() - 190, getHeight() - 90, 170, 30);
		g.setColor(Color.white);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x=e.getX() + map.getX(), y=e.getY() + map.getY();
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (e.getX() < 780) {
				dragBox.width = (x) - dragBox.x;
				dragBox.height = (y) - dragBox.y;
			}
		} else if (SwingUtilities.isRightMouseButton(e)) { // ensure player is
															// clicking on game
															// panel
			click_count = 10;
			if (e.getX() < 780) {
				mx = x;// Accounts for map scrolling
											// displacement
				my = y;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x=e.getX() + map.getX(), y=e.getY() + map.getY();
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (e.getX() < 780) {
				left_clicked = true;
				dragBox.x = x;
				dragBox.y = y;
			}
		} else if (SwingUtilities.isRightMouseButton(e)) {
			if (space) {
				sx = x;
				sy = y;
				return;
			}
			if (e.getX() < 780) { // ensure player is clicking on game panel
				right_clicked = true;
				click_count = 10;
				mx = x;// Accounts for map scrolling
											// displacement
				my = y;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x=e.getX() + map.getX(), y=e.getY() + map.getY();
		if (SwingUtilities.isLeftMouseButton(e)) {
			left_clicked = false;
			dragBox.width = 1;
			dragBox.height = 1;
		} else if (SwingUtilities.isRightMouseButton(e)) { // ensure player is
			if (space) {
				return;
			} // clicking on game // panel
			right_clicked = false;
			click_count = 10;
			// if(e.getX()<780){
			// mx = e.getX() + map.getX();//Accounts for map scrolling
			// displacement
			// my = e.getY() + map.getY();
			// }
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 38) {// up arrow
			if (selectedBugs.size() < 6) {
				Bug newSelected = (Bug) bugs.get(0);
				bugs.remove(newSelected);
				bugs.add(newSelected);
				// selected is empty, take first bug in bugs and add to selected
				selectedBugs.add(newSelected);
				newSelected.selected = true;
			}
		} else if (e.getKeyCode() == 40) {// down arrow
			if (selectedBugs.size() > 1) {
				Bug unSelected = selectedBugs.get(selectedBugs.size() - 1);
				unSelected.selected = false;
				selectedBugs.remove(unSelected);
			}
		} else if (e.getKeyCode() == 16)// shift
			shifted = true;
		else if (e.getKeyCode() == 32) { // space
			space = true;
			right_clicked = false;
			// if (shifted) {
			// for (Unit i : bugs) {
			// ((Bug) i).attack();
			// }
			// } else {
			// for (Bug i : selectedBugs) {
			// i.attack();
			// }
			// }
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			map.setY(-10);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			map.setY(10);
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			map.setX(-10);
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			map.setX(10);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 16)// shift
			shifted = false;
		else if (e.getKeyCode() == 32) { // space
			space = false;
			sx = -50;
			sy = -50;
		} else if (e.getKeyCode() == KeyEvent.VK_W)
			map.setY(10);
		else if (e.getKeyCode() == KeyEvent.VK_S)
			map.setY(-10);
		else if (e.getKeyCode() == KeyEvent.VK_A)
			map.setX(10);
		else if (e.getKeyCode() == KeyEvent.VK_D)
			map.setX(-10);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			if (bugs.size() == 0) {// losing condition
				System.out.println("Game Over");
				GameFrame.showGameOver();
			}
			if (right_clicked) {
				right_clicked();
			}
			if (left_clicked) {
				left_clicked();
			}
			if (space) {
				space();
			}
			
			tele();
			
			for (int i = 0; i < bugs.size(); i++) {
				bugs.get(i).update(map);
			}
			enemies_update();
			//Bug Death, Combine removal
			for (int i = 0; i < selectedBugs.size(); i++) {
				if (!bugs.contains(selectedBugs.get(i)))
					selectedBugs.remove(selectedBugs.get(i));
			}
			// for (int i = 0; i < bullets.size(); i++) {
			// bullets.get(i).update();
			// }
			// for (int i = 0; i < enemyBullets.size(); i++) {
			// enemyBullets.get(i).update();
			// }
			//
			// for (Bullet i : bullets) {
			// i.moveTo((int) p.getX(), (int) p.getY() - 50);
			// }
		}

		if (e.getSource() == combine) {/* combine */
			combine();
		}
		for (int i = 0; i < selectButtons.size(); i++) {
			if (e.getSource() == selectButtons.get(i)) {
				removeBug(i);
			}
		}
		repaint();
	}
	public void tele(){
		if(map.isTele()!=0){
			int[] a=map.getExit();
			for(Unit i:bugs){
					i.setX(a[0]);
					i.setY(a[1]);
					mx=a[0];
					my=a[1];
					shifted=true;
		 	}
		}
	}
	public void enemies_update(){
		
		for (int i = 0; i < enemies.get(map.getLevel()).size(); i++) {
			Enemy curr = enemies.get(map.getLevel()).get(i);
			curr.update(map);
			if(curr.attack!=null){
				continue;
			}
			for (int j = 0; j < bugs.size(); j++) {
				if (curr.getRange().intersects(bugs.get(j).getCollision())) {
					curr.setAttack(bugs.get(j));
				}
			}
		}
	}
	
	public void right_clicked(){
		int size = 0;
		if (shifted) { // shift on, move all bugs
			if (bugs.size() == 1) {
				bugs.get(0).moveTo(mx, my);
			} else {
				size = (int) Math.sqrt(bugs.size());
				for (int i = 0; i < bugs.size(); i++) {
					bugs.get(i).moveTo(
							mx + i % size * Bug.size * 2 - size
									* Bug.size,
							my + i * Bug.size * 2 / size - size
									* Bug.size);
				}
			}
		} else {
			// shift off, only move selected bugs

			if (selectedBugs.size() == 1) {
				selectedBugs.get(0).moveTo(mx, my);
			} else {
				size = (int) Math.sqrt(selectedBugs.size());
				for (int i = 0; i < selectedBugs.size(); i++) {
					selectedBugs.get(i).moveTo(
							mx + i % size * Bug.size * 2 - size
									* Bug.size,
							my + i * Bug.size * 2 / size - size
									* Bug.size);
				}
			}
		}
	}
	
	public void left_clicked(){
		selectedBugs.clear();
		Rectangle rect = normalize(dragBox);
		for (Bug i : bugs) {
			i.selected = false;
			if (i.getCollision().intersects(rect)
					&& selectedBugs.size() < 6) {
				selectedBugs.add(i);
				i.selected = true;
			}
		}
	}
	
	public void space(){
		// for (Enemy i : enemies.get(map.getLevel())) {
		// if (i.getCollision().intersects(
		// new Rectangle(sx - i.getSize(), sy - i.getSize(), i
		// .getSize(), i.getSize()))) {
		// for (Unit k : selectedBugs) {
		// k.setAttack((Enemy) i);
		// }
		//
		// }
		// }

		for (int j = 0; j < enemies.get(map.getLevel()).size(); j++) {
			Enemy curE = enemies.get(map.getLevel()).get(j);
			if (curE.getCollision().intersects(
					new Rectangle(sx - curE.getSize(), sy
							- curE.getSize(), curE.getSize(), curE
							.getSize()))) {
				for (Bug i : selectedBugs) {
					if (i.getRange().intersects(curE.getCollision())) {
						System.out.println("attack");
						i.setAttack(curE);
					} else
						i.setAttack(null);
				}
			}
		}
		for (Unit i : bugs) {
			if (i.getCollision()
					.intersects(new Rectangle(sx, sy, 1, 1))) {
				for (Unit j : selectedBugs) {
					if(j.getRange().intersects(i.getCollision())){
						j.setSupport((Bug) i);
					}
				}
			}
		}
	}
	
	public void remove(){
		for(Unit i:bugs){
			if (i.health<=0){
				bugs.remove(i);
			}
		}
		for(Unit i: enemies.get(map.getLevel())){
			if(i.health<=0){
				enemies.get(map.getLevel()).remove(i);
			}
		}
	}
	
	public void removeBug(int i){
		if (i < selectedBugs.size()) {
			Bug removeBug = selectedBugs.get(i);
			removeBug.selected = false;
			selectedBugs.remove(removeBug);
		}
	}
	
	public void combine(){
		int newType = 0;
		requestFocus();
		Bug combined = new Bug(selectedBugs.get(0).getX(), selectedBugs
				.get(0).getY());
		while (selectedBugs.size() > 0) {
			if (newType + selectedBugs.get(0).getType() < 7)
				newType += selectedBugs.get(0).getType();
			else
				break;
			bugs.remove(selectedBugs.get(0));
			selectedBugs.remove(0);
		}
		combined.setType(newType);
		combined.updateImage();
		combined.selected = true;
		selectedBugs.add(combined);
		bugs.add(combined);
	}

	public void clicked(Graphics g) {
		if (click_count < 1) {
			return;
		}
		g.setColor(Color.blue);
		g.fillOval(mx - click_count / 2 - map.getX(), my - click_count / 2
				- map.getY(), click_count, click_count);
		click_count--;

	}

	public void dragBox(Graphics g) {
		if (!left_clicked) {
			return;
		}
		Rectangle rect = normalize(dragBox);
		g.setColor(new Color(0, 255, 0, 25));
		g.fillRect(rect.x - map.getX(), rect.y - map.getY(), rect.width,
				rect.height);
	}

	public Rectangle normalize(Rectangle dragBox) {
		Rectangle rect = new Rectangle(dragBox);
		if (dragBox.width < 0) {
			rect.x += dragBox.width;
			rect.width *= -1;
		}
		if (dragBox.height < 0) {
			rect.y += dragBox.height;
			rect.height *= -1;
		}
		return rect;
	}
	
	public Timer getTimer() {
		return timer;
	}

	public void setBackground(int size) {
		pixels = new Pixel[size];
		pixels_2 = new Pixel[size];
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = new Pixel(new Color(225, 225, 225),
					map.getSect().rect.width, map.getSect().rect.height);
			pixels_2[i] = new Pixel(new Color(200, 200, 120),
					map.getSect().rect.width, map.getSect().rect.height);
		}
	}

	public void reset() throws IOException {
		bugs.clear();
		selectedBugs.clear();
		enemies.clear();
		// bullets.clear();
		// enemyBullets.clear();
		new Game();

	}
}
