import javax.swing.JFrame;

// PURPOSE: Runs the game by creating any objects needed for game to run
/*
 * Adam's improvements
 * 	shuffles deck each time to prevent card counting
 * 	fixed bug in dealerTurn to prevent "card spew"
 * 	fixed fonts in results display to user
 *  Fixed card stacking in shuffler
 */
public class Launcher {
	static Launcher launch = new Launcher();
	private Painter paint = new Painter(this); //no parameters draws start screen
	private JFrame window = new JFrame("Blackjack");
	private Listener mouseListen = new Listener(paint, this);
	private Dealer deal = null;
	public static void main(String[] args) {
		//Run Game
		launch.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		launch.window.setBounds(500, 200, 800, 650); 
		launch.window.addMouseListener(launch.mouseListen); //add the mouse listeners
		launch.window.addMouseMotionListener(launch.mouseListen);
		launch.window.setResizable(false);//makes the window not resizeable
		launch.window.add(launch.paint);//adding the painter, right now start screen
		launch.window.setVisible(true);
	}
	
	public JFrame getWindow(){
		return window;
	}
	public void createDealer(){
		deal = new Dealer(paint);
	}
	public Dealer getDealer(){
		return deal;
	}
}
