package terrain;

import java.util.Comparator;

public class Vector {
	// holds x and y coordinates,
	// can be used for position for bugs & enemies,
	// velocity & acceleration for projectiles
	private int x, y;
	public static final Vector ZERO=new Vector(0,0);
	
	//Overrides java compare method for objects, so arrayList.contains and Collections.sort can sort this object.
	public static class VectorComparator implements Comparator<Vector>{

		@Override
		public int compare(Vector a, Vector b) {
			return (a.getX()-b.getX())*10+a.getY()-b.getY();
		}
	}
	
	public Vector(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}

	public Vector() {
		this(0, 0);
	}
	public Vector(Vector v){
		this(v.x,v.y);
	}
	public void set(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void set(Vector v){
		this.set(v.x,v.y);
	}
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
	public void normalize(){
		x=x/Math.abs(x);
		y=y/Math.abs(y);
	}
	public float length(){
		//returns len of vector
		float len = (float) (Math.sqrt(x*x+y*y));
		return len;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	@Override
	public boolean equals(Object c){
		if(!(c instanceof Vector))
			return false;
		
		
		Vector other=(Vector)c;
		if(this.x==other.x&&this.y==other.y){
			return true;
		}
		return false;
	}
	
	public Vector sum(Vector other) {
		Vector res = new Vector();
		res.x = this.x + other.x;
		res.y = this.y + other.y;
		return res;
	}

	public void show() {
		System.out.println(x+" "+y);		
	}
}
