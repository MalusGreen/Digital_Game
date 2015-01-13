import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy extends Unit {
	private static int size;
	protected int health;
	private int type;
	private int cooldown = 50;

	public Enemy(int x, int y, int type) {
		super(x, y, size);
		this.type = type;
		size = 20 * type;
		health = (int) (90 + Math.pow(2, type));
	}

	public void attack() {
		switch (type) {
		case 1: // straight
			fireStraight(1);
			break;
		case 2: // straight, 2x damage
			fireStraight(2);
			break;
		case 3: // four streams of bullets
			fireFour(1);
			break;
		case 4: // eight streams of bullets
			fireEight(1);
			break;
		case 5: // eight streams of bullets,2x damage
			fireEight(2);
			break;
		}
	}

	public void update() {
		cooldown--;
		if (cooldown == 0) {
			attack();
			cooldown = 50;
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		((Graphics2D) g).setStroke(new BasicStroke(2));
		drawHCircle(x - cx, y - cy, size, type, g);
		drawVCircle(x - cx, y - cy, size, type, g);
		g.setColor(Color.RED);
		g.fillRect(x - size - cx, (int) ((y - cy) + size * 3),
				(int) (health * type / 1.3), 10);
	}

	public void drawHCircle(int cx, int cy, int radius, int depth, Graphics g) {
		g.drawOval(cx, cy, radius * 2, radius * 2);
		if (depth > 0) { // what happens if you change the 8?
			drawHCircle(cx + radius * 3 / 2, cy + radius / 2, radius / 2,
					depth - 1, g);
			drawHCircle(cx - radius / 2, cy + radius / 2, radius / 2,
					depth - 1, g);
		}
	}

	public void drawVCircle(int cx, int cy, int radius, int depth, Graphics g) {
		g.drawOval(cx, cy, radius * 2, radius * 2);
		if (depth > 0) { // what happens if you change the 8?
			drawVCircle(cx + +radius / 2, cy + radius * 3 / 2, radius / 2,
					depth - 1, g);
			drawVCircle(cx + radius / 2, cy - radius / 2, radius / 2,
					depth - 1, g);
		}
	}

	public void fireStraight(int damage) {
		int x2 = x + size, y2 = y + size;
		Bullet newBullet = new Bullet(x2, y2, 50, 1, "enemy");
		int rnd = (int) Math.random() * 4 + 1;
		switch (rnd) {
		case 1: // move straight left
			newBullet.tx = 0;
			newBullet.ty = y2;
			break;
		case 2: // move straight right
			newBullet.tx = 700;
			newBullet.ty = y2;
			break;
		case 3: // move straight up
			newBullet.tx = x2;
			newBullet.ty = 0;
			break;
		case 4: // move straight down
			newBullet.tx = x2;
			newBullet.ty = 700;
			break;
		}
		Game.enemyBullets.add(newBullet);
	}

	public void fireFour(int damage) {
		for (int i = 0; i < 4; i++) {
			int x2 = x + size, y2 = y + size;
			Bullet newBullet = new Bullet(x2, y2, 50, 1, "enemy");
			switch (i) {
			case 1: // move straight left
				newBullet.tx = 0;
				newBullet.ty = y2;
				break;
			case 2: // move straight right
				newBullet.tx = 700;
				newBullet.ty = y2;
				break;
			case 3: // move straight up
				newBullet.tx = x2;
				newBullet.ty = 0;
				break;
			case 4: // move straight down
				newBullet.tx = x2;
				newBullet.ty = 700;
				break;
			}
			Game.enemyBullets.add(newBullet);
		}
	}

	public void fireEight(int damage) {
		for (int i = 0; i < 6; i++) {
			int x2 = x + size, y2 = y + size;
			Bullet newBullet = new Bullet(x2, y2, 50, 1, "enemy");
			switch (i) {
			case 1: // move straight left
				newBullet.tx = 0;
				newBullet.ty = y2;
				break;
			case 2: // move straight right
				newBullet.tx = 700;
				newBullet.ty = y2;
				break;
			case 3: // move straight up
				newBullet.tx = x2;
				newBullet.ty = 0;
				break;
			case 4: // move straight down
				newBullet.tx = x2;
				newBullet.ty = 700;
				break;
			case 5: // move diagonal right down
				newBullet.tx += (int) (Math.sqrt(100));
				newBullet.ty += (int) (Math.sqrt(100));
				break;
			case 6: // move diagonal left up
				newBullet.tx -= (int) (Math.sqrt(100));
				newBullet.ty -= (int) (Math.sqrt(100));
				break;
			case 7: // move diagonal right up
				newBullet.tx += (int) (Math.sqrt(100));
				newBullet.ty -= (int) (Math.sqrt(100));
				break;
			case 8: // move diagonal left down
				newBullet.tx -= (int) (Math.sqrt(100));
				newBullet.ty += (int) (Math.sqrt(100));
				break;

			}
			Game.enemyBullets.add(newBullet);
		}
	}

	public Rectangle getCollision() {
		return new Rectangle(x, y, size * 2, size * 2);
	}
}
