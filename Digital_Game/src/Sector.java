import java.awt.*;

import javax.swing.*;

import terrain.Terrain;




public class Sector {
	Rectangle rect;
	String data;
	Terrain[] map;
	ImageIcon bg;
	public Sector(String path, int x, int y, int sx, int sy){
		bg=new ImageIcon(path);
		System.out.println(path);
		rect=new Rectangle(x,y,sx,sy);
	}
	public void draw(Graphics g, int x, int y){
		g.drawImage(bg.getImage(), 0, 0, null);
		for(int i=0;i<map.length;i++){
			map[i].draw(g, x, y);
		}
	}
	public Rectangle getRect(){
		return rect;
	}
	public void setMap(Terrain[] map){
		this.map=map;
	}
	public Terrain[] getMap(){
		return map;
	}
}