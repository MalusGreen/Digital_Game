package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends Terrain {
	Rectangle collisionBox = new Rectangle(0, 0, 0, 0);

	public Wall(int x, int y) {
		super(x, y);
		super.solid = true;
		collisionBox.setBounds(x, y, 50, 50);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(x, y, 50, 50);

		g.drawRect(x, y, 50, 50);
	}

	public Rectangle getRect() {
		return collisionBox;
	}
}
