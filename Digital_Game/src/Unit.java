import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import terrain.Terrain;

public class Unit {
	protected int x, y; // position coordinates
	protected int tx, ty; // target coordinates
	protected double dx, dy; // velocity x & y (has direction)
	protected static int cx, cy; // Map Scrolling Displacement
	protected int speed = 5;
	protected int size;
	public boolean reached;
	public boolean moved = false;
	protected int type;
	protected int damage;
	protected int range;
	protected int cooldown;

	// Allows for interactions between units.
	public Unit attack;
	public Bug support;
	public boolean alive;
	public int combat;
	protected int health;
	protected int maxHealth;

	public Unit(int x, int y) {
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		reached = false;
		combat = 0;
		tx = x;
		ty = y;
		cooldown=0;
	}

	public void update(World map) {
		if(combat>=0){
			System.out.println(combat);
			combat--;
			return;
		}
		//Basic Movement.
		moveUnit();
		//Checks collision with Terrain
		checkCollision(map);
		//Checks collision with sector boundaries.
		checkBoundaries(map);
		
	}
	public void moveUnit(){
		int differenceX = tx - x;
		int differenceY = ty - y;
		if (differenceX == 0 && differenceY == 0) {
			reached = true;
			dx = 0;
			dy = 0;
			return;
		} else {
			reached = false;
		}
		double radius = Math.sqrt(differenceX * differenceX + differenceY
				* differenceY);
		double cosangle = differenceX / radius;
		double sinangle = differenceY / radius;
		dx = speed * cosangle;
		dy = speed * sinangle;
		if (dx == -5 || (dx >= 4.1 && dx <= 4.5))
			dx = 0;
		if (dy == -5 || (dy >= 4.1 && dy <= 4.5))
			dy = 0;
		x += dx;
		y += dy;
	}
	
	public void checkBoundaries(World map){
		if(x>map.getSect().getRect().width){
			x=map.getSect().getRect().width;
		}
		else if(x<0) {
			x=0;
		}
		if(y>map.getSect().getRect().height){
			y=map.getSect().getRect().height;
		}
		else if(y<0){
			y=0;
		}
	}
	
	public void checkCollision(World map){
		for (int i = 0; i < map.getSect().getMap().size(); i++) {
			// check collision with terrain
			
		}
	}

	public void draw(Graphics g, Color c) {
		Graphics2D graph = (Graphics2D) g;
		graph.setStroke(new BasicStroke(3));
		g.setColor(c);
		if (attack != null) {
			System.out.println("attack");
			graph.drawLine(x - cx, y - cy, attack.x - cx, attack.y - cy);// attack
																			// like
		} else if (support != null) {
			g.setColor(Color.green);
			graph.drawLine(x - cx, y - cy, support.x - cx, support.y - cy);
		}
	}

	public void combat() {
		if(cooldown==0){
			cooldown=10;
			if (attack != null) {
				attack.health -= damage;
				attack.combat=100;
				if (attack.health <= 0) {
					attack = null;
				}
				
			}
			else if (support != null) {
//				combat = true;
				support.health++;
				if (support.health <=0) {
					support = null;
				}
				
			}
			return;
		}
		else if (attack!=null){
			if(!getRange().intersects(attack.getCollision())){
				attack=null;
			}
		}
		else if(support!=null){
			if(!getRange().intersects(support.getCollision())){
				support = null;
			}
		}
		//Still need to understand where attack is made true or false;
		//True, whenever something tries to damage it.
		//False, when attacker dies, when out of range.
		cooldown--;
	}

	public void setAttack(Unit opponent) {
		attack = opponent;
		if(attack!=null){
			support=null;
		}
	}

	public void setSupport(Bug ally) {
		support = ally;
		if(support!=null){
			attack=null;
		}
	}

	public void stop() {

	}

	public boolean intersects(Unit other) { // check collision with another bug
		return this.getCollision().intersects(other.getCollision());
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public static void setXY(int x, int y) {
		cx = x;
		cy = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getTx() {
		return tx;
	}

	public int getTy() {
		return ty;
	}

	public int getSize() {
		return size;
	}

	public Rectangle getCollision() {
		return new Rectangle(x - size, y - size, size * 2, size * 2);
	}

	public Ellipse2D.Double getRange() {
		return new Ellipse2D.Double(x - range, y - range,
				(int) (range * 2), (int) (range * 2));
	}
}
