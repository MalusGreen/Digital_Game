import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Unit {
	private int x, y; // x,y 
	private static int size = 5; // width and height
	private int time;

	// constructor
	public Bullet(int x, int y,int time) {
		super(x, y);
		System.out.println(tx+" "+ty);
		System.out.println(x+" "+y);
		this.time=time;
	}
	public void setTx(int tx){
		this.tx=tx;
	}
	public void setTy(int ty){
		this.ty=ty;
	}
	public void update(){
		super.update();
	}
	// returning all the necessary value of this class

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	public void draw(Graphics g){
		g.setColor(Color.GREEN);
		g.fillRect(x, y, size, size);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	// move toward the angle
	// //forward

}
