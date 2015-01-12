package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Data extends Terrain{

	public Data(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(new Color(0,0,255,100));
		g.fillRect(x,y,25,25);
	}

	@Override
	public int active() {
		// TODO Auto-generated method stub
		return -1;
	}
	@Override
	public Rectangle getCollision(){
		return new Rectangle(x,y,25,25);
	}
}
