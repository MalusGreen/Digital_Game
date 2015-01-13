import java.awt.Graphics;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.Timer;

import terrain.Vector;

public class GUI extends JFrame implements ActionListener, MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int count=0;
	int x=0, y=0;
	Vector a, b;
	JPanel p1;
	static World test;
	Timer timer;
	static World map;
	public static void main(String args[]) throws IOException{
		test=new World();
		map=new World("test.txt");
		new GUI("Map of Game").setVisible(true);
	}
	
	public GUI(String name){
		super(name);
		setSize(1024,720);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		p1=new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g){
				map.draw(g);
			}
		};
		add(p1);
		addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		map.change(1);
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
//	
//	public GUI(){
//		super();
//		setSize(900,900);
//		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		p1=new JPanel(){
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void paintComponent(Graphics g){
//				super.paintComponent(g);
//				for(int i=0;i<10;i++){
//					for(int j=0;j<10;j++){
//						if(test.getMap(j,i).getSolid()){
//							g.drawRect(j*80,i*80,80,80);
//						}
//					}
//				}
//				g.drawOval(x+35, y+35, 10, 10);
//			}
//		};
//		p1.addMouseListener(this);
//		add(p1);
//		timer=new Timer(10, this);
//		timer.addActionListener(this);
//		timer.start();
//	}
//	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==timer){
//			int num=path.size()-1;
//			if(!path.isEmpty()){
//				if(x-path.get(num).getX()*80!=0)
//					x+=(path.get(num).getX()*80-x)/Math.abs(path.get(num).getX()*80-x);
//				if(y-path.get(num).getY()*80!=0)
//					y+=(path.get(num).getY()*80-y)/Math.abs(path.get(num).getY()*80-y);
//				if(x-path.get(num).getX()*80==0&&y-path.get(num).getY()*80==0){
//					path.remove(num);
//				}
//			}
//		}
//		repaint();
//	}
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		if(e.getX()<801&&e.getY()<801){
//			b=new Vector(e.getX()/80,e.getY()/80);
//			path=test.getPath(new Vector(x/80,y/80),b);
//		}
//
//		count++;
//	}
//	
//	@Override v
//	public void mouseEntered(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//		
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		
//	}
//
//	
//	
//}
