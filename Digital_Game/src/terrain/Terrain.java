package terrain;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Terrain {
	protected Vector pos;
	protected boolean solid;
	protected String type;
	public Terrain(int x,int y){
		pos=new Vector(x,y);
	}
	public boolean getSolid(){
		return solid;
	}
	public abstract void draw(Graphics g);
	
	public Rectangle getRect(){
		return new Rectangle(pos.getX(),pos.getY(),50,50);
	}
	public String toString(){
		return "Position: "+pos.getX()+" "+pos.getY();
	}
}
