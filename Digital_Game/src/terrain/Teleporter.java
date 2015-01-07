package terrain;

import java.awt.Color;
import java.awt.Graphics;

public class Teleporter extends Terrain{
	public Teleporter(int x, int y){
		super(x,y);
		
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.green);
		g.fillRect(this.x-size-x, this.y-size-y, size*2, size*2);
	}

	@Override
	public int active() {
		// TODO Auto-generated method stub
		return 0;
	}
}
