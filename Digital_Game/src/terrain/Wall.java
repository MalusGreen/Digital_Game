package terrain;

import java.awt.Color;
import java.awt.Graphics;


public class Wall extends Terrain {

	public Wall(int x, int y) {
		super(x, y);
		super.solid = true;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.red);

		g.fillRect(x-size, y-size, size*2, size*2);
	}

	@Override
	public int active() {
		return 0;
	}
}
