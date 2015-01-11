import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener,
		MouseListener {
	private Timer timer;
	public static ArrayList<Unit> bugs = new ArrayList<>();
	public static ArrayList<Unit> enemies = new ArrayList<>();
	private ArrayList<Bug> selectedBugs = new ArrayList<>();
	public static boolean clicked, shifted;
	public static ArrayList<Bullet> bullets = new ArrayList<>();
	public static ArrayList<Bullet> enemyBullets = new ArrayList<>();
	private int mx, my;
	public static World map;
	public static int score;
	public static int click_count;
	public JButton pause, exit, combine;
	PointerInfo a = MouseInfo.getPointerInfo();
	Point b = a.getLocation();

	public Game() throws IOException {
		click_count=0;
		
		addKeyListener(this);
		addBugs(bugs);
		addEnemies(enemies);
		
		map = new World("test.txt");
		setLayout(null);
		
		
		combine = new PrettyBtn("COMBINE", 2);
		pause = new PrettyBtn("PAUSE", 2);
		exit = new PrettyBtn("MENU", 2);
		timer = new Timer(17, this);
		
		selectedBugs.add((Bug) bugs.get(bugs.size() - 1));
		selectedBugs.get(0).selected = true;
		
		add(combine);
		add(pause);
		add(exit);
		
		combine.addActionListener(this);
		combine.addKeyListener(this);
		pause.addKeyListener(this);
		exit.addKeyListener(this);
		addMouseListener(this);

		setBackground(Color.WHITE);
	}

	public void addBugs(ArrayList<Unit> arr) {
		for (int i = 0; i < 5; i++) {
			bugs.add(new Bug((int) (i/10.0 * 100) + 12, (int) (i/10.0 * 100) + 12));
		}
	}

	public void addEnemies(ArrayList<Unit> arr) {
		enemies.add(new Enemy(300, 300, 5));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Shows where user clicks.
		clicked(g);
		
		//map
		map.draw(g);
		
		//SideBar- Commands
		Image sm = new ImageIcon("side-menu.png").getImage();
		g.drawImage(sm, getWidth() - 220, 0, 220, getHeight(), null);
		combine.setBounds(getWidth() - 190, getHeight() - 190, 170, 30);
		pause.setBounds(getWidth() - 190, getHeight() - 130, 170, 30);
		exit.setBounds(getWidth() - 190, getHeight() - 90, 170, 30);
		Unit.setXY(map.getX(), map.getY());
		
		//Units
		for (Unit i : bugs) {
			i.draw(g);
		}
		for (Bullet i : bullets) {
			i.draw(g);
		}
		for (Bullet i : enemyBullets) {
			i.draw(g);
		}

		for (Unit i : enemies) {
			i.draw(g);
		}
		// paint background, type, health for selected bugs
		for (int i = 0; i < selectedBugs.size(); i++) {
			g.setColor(Color.white);
			g.fillRect(getWidth() - 189 + i % 2 * 88, getHeight() - 419 + i / 2
					* 116, 75, 70);
			g.drawImage(selectedBugs.get(i).getImage(), getWidth() - 176 + i
					% 2 * 88, getHeight() - 409 + i / 2 * 116, Bug.size * 2,
					Bug.size * 2, null);
			g.drawString(selectedBugs.get(i).getType() + "", getWidth() - 129
					+ i % 2 * 88, getHeight() - 322 + i / 2 * 115);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)){
			if (e.getX() < 780) { // ensure player is clicking on game panel
				clicked = true;
				click_count=10;
				mx = e.getX() + map.getX();//Accounts for map scrolling displacement
				my = e.getY() + map.getY();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("yes");
		if (e.getKeyCode() == 38) {// up arrow
			Bug newSelected = null;
			if (selectedBugs.size() == 0) {
				newSelected = (Bug) bugs.get(0);

			} else if (selectedBugs.size() < 4) {
				newSelected = (Bug) bugs.get(bugs.indexOf(selectedBugs
						.get(selectedBugs.size() - 1)) - 1);
			}
			selectedBugs.add(newSelected);
			newSelected.selected = true;
		}
		else if (e.getKeyCode() == 40) {// down arrow
			if (selectedBugs.size() > 1) {
				Bug unSelected = selectedBugs.get(selectedBugs.size() - 1);
				unSelected.selected = false;
				selectedBugs.remove(unSelected);
			}
		}
		else if (e.getKeyCode() == 16)// shift
			shifted = true;
		else if (e.getKeyCode() == 32) { // space
			System.out.println("space");
			if (shifted) {
				for (Unit i : bugs) {
					((Bug) i).attack();
				}
			} else {
				for (Bug i : selectedBugs) {
					i.attack();
					// i.setBulletTarget(bx,by);
				}
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_W){
			map.setY(-10);
		}
		else if(e.getKeyCode() == KeyEvent.VK_S){
			map.setY(10);
		}
		else if(e.getKeyCode() == KeyEvent.VK_A){
			map.setX(-10);
		}
		else if(e.getKeyCode() == KeyEvent.VK_D){
			map.setX(10);
		}
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 16)// shift
			shifted = false;
		else if(e.getKeyCode() == KeyEvent.VK_W){
			map.setY(10);
		}
		else if(e.getKeyCode() == KeyEvent.VK_S){
			map.setY(-10);
		}
		else if(e.getKeyCode() == KeyEvent.VK_A){
			map.setX(10);
		}
		else if(e.getKeyCode() == KeyEvent.VK_D){
			map.setX(-10);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == timer) {
			if (clicked) {
				int size = 0;
				int bug_size=0;
				if (shifted) { // shift on, move all bugs
					for (Unit i : bugs)
						((Bug) i).moveTo(mx, my);
				} else
					// shift off, only move selected bugs
					size=(int)Math.sqrt(selectedBugs.size());
					bug_size=bugs.get(0).size;
					for (int i=0;i<selectedBugs.size();i++)
						selectedBugs.get(i).moveTo(mx+i%size*bug_size*2-size*bug_size, my+i*bug_size*2/size-size*bug_size);
			}
			for (Unit i : bugs) {
				((Bug) i).update(map);
			}
			for (Unit i : enemies) {
				i.update();
			}
			for (int i = 0; i < selectedBugs.size(); i++) {
				if (!bugs.contains(selectedBugs.get(i)))
					selectedBugs.remove(selectedBugs.get(i));
			}
			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).update();
			}
			for (int i = 0; i < enemyBullets.size(); i++) {
				enemyBullets.get(i).update();
			}
			Point p = MouseInfo.getPointerInfo().getLocation();

			for (Bullet i : bullets) {
				i.moveTo((int) p.getX(), (int) p.getY() - 50);
			}
		}

		if (e.getSource() == combine) {/* combine */
			System.out.println("combined");
			int newType = 0;
			Bug combined = new Bug(selectedBugs.get(0).getX(), selectedBugs
					.get(0).getY());
			while (selectedBugs.size() > 0) {
				newType += selectedBugs.get(0).getType();
				bugs.remove(selectedBugs.get(0));
				selectedBugs.remove(0);
			}
			combined.setType(newType);
			combined.updateImage();
			System.out.println(combined.getType());
			combined.selected = true;
			selectedBugs.add(combined);
			System.out.println(selectedBugs.size());
			bugs.add(combined);
		}
		repaint();
		clicked = false;
	}
	public void clicked(Graphics g){
		g.setColor(Color.blue);
		g.fillOval(mx-click_count/2,my-click_count/2,click_count,click_count);
		click_count--;
	}
	public Timer getTimer() {
		return timer;
	}
	public void reset(){
		bugs.clear();
		selectedBugs.clear();
		enemies.clear();
		bullets.clear();
		enemyBullets.clear();
		addBugs(bugs);
		addEnemies(enemies);
		selectedBugs.add((Bug) bugs.get(0));
		selectedBugs.get(0).selected = true;
	}
}
