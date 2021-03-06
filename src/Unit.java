import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Unit {
	protected int x, y; // position coordinates
	protected int tx, ty; // target coordinates
	protected double dx, dy; // velocity x & y (has direction)
	protected static int cx, cy; // Map Scrolling Displacement
	protected int speed = 5;
	public int size = 5;
	public boolean reached;
	public boolean moved = false;
	

	//Allows for interactions between units.
	public Enemy attack;
	public Bug support;
	public boolean alive;
	public boolean combat;
	
	public Unit(int x, int y) {
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		reached = false;
		tx = x;
		ty = y;
		alive=true;
	}

	public void update() {
		//Interactions with other units.
		if(attack!=null){
			support=null;
			combat=true;
			attack.health--;
			if(!attack.alive){
				attack=null;
				combat=false;
			}
		}
		else if(support!=null){
			attack=null;
			combat=true;
			support.health++;
			if(!support.alive){
				support=null;
				combat=false;
			}
		}
		if(combat){
			return;
		}
		
		// move bug
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
		 if (dx == -5 || (dx >= 4.1 && dx <= 1))
		 dx = 0;
		 if (dy == -5 || (dy >= 4.1 && dy <= 1))
		 dy = 0;

		x += dx;
		y += dy;
		// keep bug within frame
		if (x > 780 - size)
			x = 780 - size;
		if (y > 680 - size)
			y = 680 - size;
		if (x < size)
			x = size;
		if (y < size)
			y = size;
	}

	public void draw(Graphics g) {
		Graphics2D graph=(Graphics2D)g;
		graph.setStroke(new BasicStroke(3));
		if(attack!=null){
			graph.drawLine(x-cx,y-cy,attack.x-cx,attack.y-cy);
		}
		graph.setStroke(new BasicStroke(3));
		if(support!=null){
			graph.drawLine(x-cx,y-cy,support.x-cx,support.y-cy);
		}
	}
	
	public void setAttack(Enemy enemy){
		attack=enemy;
	}
	
	public void setSupport(Bug ally){
		support=ally;
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
		// TODO Auto-generated method stub
		return tx;
	}

	public int getTy() {
		// TODO Auto-generated method stub
		return ty;
	}

	public Rectangle getCollision() {
		return new Rectangle(x - size, y - size, size * 2, size * 2);
	}
}
