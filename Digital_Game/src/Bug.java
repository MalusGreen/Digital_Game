import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import terrain.*;
public class Bug extends Unit {
	private int type;
	private Image timg;
	public boolean selected;
	public Bullet bullet;
	protected int health, damage;
	public Bug(int x, int y) {
		super(x, y);
		type = 1;
		updateImage();
		health = 100;
		damage = 0;
		health = 80 + 20 * type;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) { // types are added together for new type
		this.type = type;
	}
	
	public void updateImage() {
		timg = new ImageIcon("t" + type + ".png").getImage();
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.drawImage(timg, x - size, y - size, size * 2, size * 2, null);
		if (selected)
			g.drawRect(x - size, y - size, size * 2, size * 2);
	}
	
	public Image getImage() {
		return timg;
	}
	
	public void attack() {
		switch (type) {
		case 1:
			fire(20);
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			fire(20);
			break;

		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void fire(int distance) {
		Bullet newBullet = new Bullet(x, y, distance);
		// move toward nearest enemy
		Game.bullets.add(newBullet);
	}
	
	public int getHealth() {
		return health;
	}
	
	public void update(World map) { // collision rect
		for (Terrain i : map.getSect().getMap()){
			if (i.getSolid()&&super.getRect().intersects(i.getRect())) {
				if(this.x>=i.getX())
					x=i.getX()+i.getSize()+1;
				else if(this.x<i.getX())
					x=i.getX()-i.getSize()-1;
				if(this.y>=i.getY())
					this.y=i.getY()+i.getSize()+1;
				else if(this.y<i.getY())
					y=i.getY()-i.getSize()-1;
				tx=x;
				ty=y;
			}
			else{
				if(!i.getSolid()&&super.getRect().intersects(i.getRect())){
					int active=i.active();
					switch(active){
						case 0:
							break;
						case 1:
							//Check point captured. Show's the captured point.
							System.out.println("Wow, I did it!");
							break;
						case 2:
							System.out.println("Activating Teleporter!(Down)");
							map.change(1);
							break;
						case 3:
							System.out.println("Activating Teleporter!(Up)");
							map.change(-1);
							break;
					}
				}
				super.update();
			}
		}
	}
	
	public void moveTo(int tx, int ty) {
		this.tx = tx;
		this.ty = ty;
//		this.tx += (int) (Math.random() * 50);
//		this.tx -= (int) (Math.random() * 50);
//		this.ty += (int) (Math.random() * 50);
//		this.ty -= (int) (Math.random() * 50);
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}
}