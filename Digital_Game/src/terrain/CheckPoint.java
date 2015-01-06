package terrain;

import java.awt.Color;
import java.awt.Graphics;

public class CheckPoint extends Terrain{
	int count;
	boolean capt;
	public CheckPoint(int x, int y){
		super(x,y);
		super.solid=false;
		count=0;
		capt=false;
	}

	@Override
	public void draw(Graphics g) {
		if(!capt){
			g.setColor(Color.blue);
			g.fillRect(x-size, y-size, size*2, size*2);
		}
	}

	@Override
	public int active() {
		if(capt){
			return 1;
		}else{
			count++;
			if(count==100){
				capt=true;
			}
		}
		return 0;
		
		// TODO Auto-generated method stub
		
	}
}
