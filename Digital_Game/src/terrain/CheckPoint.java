package terrain;

import java.awt.Color;
import java.awt.Graphics;

public class CheckPoint extends Terrain{
	public CheckPoint(int x, int y){
		super(x,y);
		super.solid=false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(pos.getX(),pos.getY(),50,50);	
	}
}
