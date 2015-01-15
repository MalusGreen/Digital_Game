import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import terrain.CheckPoint;
import terrain.Data;
import terrain.Teleporter;
import terrain.Terrain;
import terrain.Wall;

public class World {
	private int mx, my;
	private int level;
	private int tx, ty;
	private int cx, cy;
	private int teleporting;
	static Sector[] sectors;
	final int[] A = { 0, -1, 1, 0 };
	final int[] B = { -1, 0, 0, 1 };

	public World() {
		teleporting=0;
		level = 0;
		tx = 0;
		ty = 0;

		cx = 0;
		cy = 0;

		mx = 10;
		my = 10;
	}

	public World(String path) throws IOException {
		teleporting=0;
		level = 0;
		tx = 0;
		ty = 0;
		cx = 0;
		cy = 0;
		mx = 0;
		my = 0;

		readFile(path);
	}

	public void readFile(String path) throws IOException {
		@SuppressWarnings("resource")
		BufferedReader sc = new BufferedReader(new FileReader(path));
		sectors = new Sector[Integer.parseInt(sc.readLine())];
		String data;
		my = sectors.length;
		for (int i = 0; i < sectors.length; i++) {
			sc.readLine();
			data = sc.readLine();
			sectors[i] = new Sector(sc.readLine(), Integer.parseInt(data
					.substring(data.indexOf("x") + 1, data.indexOf("y"))),
					Integer.parseInt(data.substring(data.indexOf("y") + 1,
							data.indexOf("sx"))), Integer.parseInt(data
							.substring(data.indexOf("sx") + 2,
									data.indexOf("sy"))), Integer.parseInt(data
							.substring(data.indexOf("sy") + 2)), i);
			Terrain[] map = new Terrain[Integer.parseInt(sc.readLine())];
			for (int u = 0; u < map.length; u++) {
				data = sc.readLine();
				Terrain t = null;
				switch (data.charAt(0)) {
				case 'W':
					t = new Wall(
							Integer.parseInt(data.substring(
									data.indexOf("x") + 1, data.indexOf("y"))),
							Integer.parseInt(data.substring(data.indexOf("y") + 1)));

					break;
				case 'D':
					t = new Data(
							Integer.parseInt(data.substring(
									data.indexOf("x") + 1, data.indexOf("y"))),
							Integer.parseInt(data.substring(data.indexOf("y") + 1)));

					break;
				case 'C':
					t = new CheckPoint(
							Integer.parseInt(data.substring(
									data.indexOf("x") + 1, data.indexOf("y"))),
							Integer.parseInt(data.substring(data.indexOf("y") + 1)));
					break;
				case 'T':
					t = new Teleporter(
							Integer.parseInt(data.substring(
									data.indexOf("x") + 1, data.indexOf("y"))),
							Integer.parseInt(data.substring(data.indexOf("y") + 1)),
							0);
					break;
				case 't':
					t = new Teleporter(
							Integer.parseInt(data.substring(
									data.indexOf("x") + 1, data.indexOf("y"))),
							Integer.parseInt(data.substring(data.indexOf("y") + 1)),
							1);
					break;
				}
				map[u] = t;
			}
			ArrayList<Terrain> m = new ArrayList<>();
			for (Terrain j : map)
				m.add(j);
			sectors[i].setMap(m);
		}
		BufferedReader br = new BufferedReader(new FileReader("enemies.txt"));
		String d;
		br.readLine();
		for (int i = 0; i < sectors.length; i++) {
			d=br.readLine();
			System.out.println(d);
			int n = Integer.parseInt(d);
			for (int j = 0; j < n; j++) {
				String str = br.readLine();
				String[] st = str.split(" ");
				Enemy newE = new Enemy(Integer.parseInt(st[1]),
						Integer.parseInt(st[2]), Integer.parseInt(st[0]),
						st[3], Integer.parseInt(st[4]), Integer.parseInt(st[5]));
				sectors[i].getEnemies().add(newE);

			}
			sectors[i].addEnemiesToGame();
			br.readLine();
		}

	}

	public void draw(Graphics g) {
		tx += cx;
		ty += cy;
		if (tx < -50) {
			tx = -50;
		}
		else if(tx>sectors[level].getRect().width-690){
			tx=sectors[level].getRect().width-690;
			System.out.println(tx);
		}
		if (ty < -50) {
			ty = -50;
		}
		else if(ty>sectors[level].getRect().height -600){
			ty=sectors[level].getRect().height-600;
			System.out.println(ty);
		}
		sectors[level].draw(g, tx, ty);
	}

	public void change(int c) {
 		level += c;
		teleporting=-c;
	}
	
	public int[] getExit(){
		Terrain	tele=sectors[level].getTele(-teleporting);
		System.out.println(teleporting);
		teleporting=0;
		System.out.println(level);
		return new int[]{tele.getX(), tele.getY()};
 	}
	
	public int isTele(){
		return teleporting;
	}

	public Sector getSect() {
		return sectors[level];
	}

	public void setX(int a) {
		cx += a;
		if (Math.abs(cx) > 10) {
			cx -= a;
		}
	}

	public void setY(int a) {
		cy += a;
		if (Math.abs(cy) > 10) {
			cy -= a;
		}

	}

	public int getX() {
		return tx;
	}

	public int getY() {
		return ty;
	}

	public int getLevel() {
		return level;
	}

}
