import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import terrain.*;

public class Sector {
	Rectangle rect;
	String data;
	private ArrayList<Terrain> map;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	ImageIcon bg;
	private int num;

	public Sector(String path, int x, int y, int sx, int sy, int num)
			throws NumberFormatException, IOException {
		bg = new ImageIcon(path);
		rect = new Rectangle(x, y, sx, sy);
		this.num = num;

	}

	public void draw(Graphics g, int x, int y) {
		g.drawImage(bg.getImage(), 0, 0, 1024, 720, null);
		for (int i = 0; i < map.size(); i++) {
			map.get(i).draw(g, x, y);
		}
	}
	
	public Terrain getTele(int tele){
		int t=tele;
		if(tele==-1){
			t=0;
		}
		for(int i=0;i<map.size();i++){
			if(map.get(i) instanceof Teleporter){
				if(t==((Teleporter)map.get(i)).getTo()){
					return map.get(i);
				}
			}
		}
		return null;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setMap(ArrayList<Terrain> map) {
		this.map = map;
		// map.add(0, new Boundary(0, 0, rect.width, rect.height));
	}

	public ArrayList<Terrain> getMap() {
		return map;
	}
	public void addEnemiesToGame() {
		Game.enemies.add(enemies);
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getNum() {
		return num;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

}