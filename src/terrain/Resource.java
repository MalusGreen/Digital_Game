package terrain;

import java.awt.Color;
import java.awt.Graphics;

public class Resource extends Terrain{
	int count;
	int size = 10;
	public Resource(int x, int y) {
		super(x, y);
		super.solid = false;
		count = 0;
		super.size=size;
	}
//
//	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(new Color(0, 0, (int) (255 - 255.0 * count / 100.0)));
		g.fillRect(this.x - size - x, this.y - size - y, size * 2, size * 2);
	}
//	@Override
	public int active() {
		return 100;
		// TODO Auto-generated method stub
	}

}
