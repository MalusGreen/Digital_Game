import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/* Bugs individually move.
 * There is a non-grid based map and path-finder.
 * Map is similar to: http://i.telegraph.co.uk/multimedia/archive/01512/The-Space-Game_1512822c.jpg
 * Bugs extends Unit(){
 * 		int x,y;
 * 		int health;
 * 		int damage;
 * 		contains methods:
 * 		private int[] findPath();
 * 		public void moveTo(int[] pos);
 * 		public void stop();
 * }
 * Modes extends Bugs(int num){
 * 		num is number of bugs combined.
 * }
 * 
 * Enemies extend Unit{
 * 		int x,y;
 * 		int health;
 * 		int damage;
 * 		private int[] findPath();
 * 		public void moveTo(int[] pos);
 * 		public void stop();
 * }
 * 
 * Bugs move to locations and encounter enemies, they will attack them. With Melee attacks.
 * Kind of like this: http://www.sc2blog.com/wp-content/uploads/2009/04/holding-ground.jpg
 * The objective is to eliminate enemies on the map.
 * If you select more than one bug you will be able to combine them into special bugs, allowing for range, 
 * This will be important as enemies be more diverse. 
 * Be careful though, you only have a certain number of bugs.
 * 
 */
public class GameFrame extends JFrame implements ActionListener {

	private Container c;
	private CardLayout cards;
	private Menu menu = new Menu();
	private JButton play, pause, exit, menuExit, unpause, menubtn, menubtn2,
			menubtn3, instructions, about;
	private DefPanel pausePanel, instPanel, aboutPanel;
	private Game game;

	public GameFrame(String title) throws IOException {
		super(title);
		c = this.getContentPane();
		cards = new CardLayout();
		c.setLayout(cards);
		game = new Game();
		play = menu.play;
		pause = game.pause;
		exit = game.exit;
		menuExit = menu.exit;
		about = menu.about;
		instructions = menu.instructions;
		pausePanel = new DefPanel("PAUSED");
		instPanel = new DefPanel("INSTRUCTIONS");
		instPanel.addLabel(new JLabel(new ImageIcon("instructions.png")));
		aboutPanel = new DefPanel("ABOUT");
		aboutPanel.addLabel("Source Code was created by Emma Xie and Kevin Zheng in ICS4U, 2014-15");
		unpause = new PrettyBtn("UNPAUSE", 2);
		instructions = menu.instructions;
		menubtn = pausePanel.toMenu;
		menubtn2 = instPanel.toMenu;
		menubtn3 = aboutPanel.toMenu;
		pausePanel.addButton(unpause);
		menubtn.addActionListener(this);
		menubtn2.addActionListener(this);
		menubtn3.addActionListener(this);
		instructions.addActionListener(this);
		about.addActionListener(this);
		unpause.addActionListener(this);
		play.addActionListener(this);
		exit.addActionListener(this);
		menuExit.addActionListener(this);
		pause.addActionListener(this);
		// menubtn.addKeyListener(game);
		// menubtn2.addKeyListener(game);
		// menubtn3.addKeyListener(game);
		// instructions.addKeyListener(game);
		// about.addActionListener(game);
		// unpause.addKeyListener(game);
		// play.addKeyListener(game);
		// exit.addKeyListener(game);
		// menuExit.addKeyListener(game);
		// pause.addKeyListener(game);

		c.add(menu, "Menu");
		c.add(game, "Game Name");
		c.add(pausePanel, "Paused");
		c.add(instPanel, "Instructions");
		c.add(aboutPanel, "About");
		this.addKeyListener(game);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == play) {
			cards.show(c, "Game Name");
			game.requestFocus();
			game.getTimer().start();
		} else if (e.getSource() == pause) {
			cards.show(c, "Paused");
			game.getTimer().stop();
		} else if (e.getSource() == unpause) {
			cards.show(c, "Game Name");
			game.requestFocus();
			game.getTimer().start();
		} else if (e.getSource() == exit||e.getSource() == menubtn) {
			cards.show(c, "Menu");
			game.reset();
		} else if (e.getSource() == instructions)
			cards.show(c, "Instructions");
		else if (e.getSource() == about)
			cards.show(c, "About");
		else if (e.getSource() == menuExit)
			System.exit(0);
		else
			cards.show(c, "Menu");

	}

	public static void main(String args[]) throws IOException {
		GameFrame frame = new GameFrame("Source Code");
		frame.setSize(1000, 700);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
