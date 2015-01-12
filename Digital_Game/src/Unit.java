
import java.awt.Graphics;
import java.awt.Rectangle;

public class Unit {
	protected int x, y; // position coordinates
	protected int tx, ty; // target coordinates
	protected double dx, dy; // velocity x & y (has direction)
	protected static int cx, cy; //Map Scrolling Displacement
	protected int speed = 5;
	public int size;
	public boolean reached;

	public Unit(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		dx = 0;
		dy = 0;
		reached=true;
		tx = x;
		ty = y;
	}

	public void update() {
		// move bug
		int differenceX = tx - x;
		int differenceY = ty - y;
		if (differenceX == 0 && differenceY == 0) {
			reached=true;
			dx = 0;
			dy = 0;
			return;
		}
		else{
			reached=false;
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
		// keep bug within frame
//		if (x > 780 - size)
//			x = 780 - size;
//		if (y > 680 - size)
//			y = 680 - size;
//		if (x < size)
//			x = size;
//		if (y < size)
//			y = size;
	}

	public void draw(Graphics g) {
		g.fillOval(x - size - cx, y - size - cy, size * 2, size * 2);
	}

	public void stop() {

	}

	public boolean intersects(Unit other) { // check collision with another bug
		return this.getCollision().intersects(other.getCollision());
	}
	
	public static void setXY(int x, int y){
		cx=x;
		cy=y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle getCollision() {
		return new Rectangle(x - size, y - size, size * 2, size * 2);
	}
}
