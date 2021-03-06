package terrain;

import java.awt.Color;
import java.awt.Graphics;


public class Wall extends Terrain {

	public Wall(int x, int y) {
		super(x, y);
		super.solid = true;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.red);
		
		g.fillRect(this.x-size-x, this.y-size-y, size*2, size*2);
	}

	@Override
	public int active() {
		return 0;
	}
}
