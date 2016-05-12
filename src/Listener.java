/*
 * author: Adam Feldscher
 * coAuthor: Jake C
 */

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Listener implements MouseListener, MouseMotionListener {
	private Painter paint;
	private Launcher launch;
	private ShapeUtils utils = new ShapeUtils();

	public Listener(Painter paint, Launcher launch) {
		this.paint = paint;
		this.launch = launch;
	}

	public void mouseClicked(MouseEvent e) { //every time the mouse is clicked in the window
		switch (paint.getState()) {
			case -1: // startup screen
				if (utils.isPtInSquare(e.getPoint(), new Point(214, 288), 252, 160)) { //clicked start button
					launch.createDealer();
				}
				break;
			case 2:
				if (utils.isPtInSquare(e.getPoint(), new Point(626, 356), 160, 65)) { //hit button pushed
					launch.getDealer().playerAction(1); //hit
					DealAnimate dealAni = new DealAnimate(paint, launch.getDealer(), false); //create a dealAnimator to deal the new card.
					dealAni.start(); //deal the new card
				} else if (utils.isPtInSquare(e.getPoint(), new Point(622, 447), 160, 65)) { //stay button pushed
					launch.getDealer().playerAction(0); //stay
				}
				launch.getWindow().repaint();
				break;
			case 4:
				launch.getDealer().startHands();
		}

	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) { //ever time the mouse is moved. 
		switch (paint.getState()) {
			case -1: // startup screen
				if (utils.isPtInSquare(e.getPoint(), new Point(214, 288), 252, 160)) { //if the mouse is over the start button
					paint.setStartHighlight(true);
				} else {
					paint.setStartHighlight(false);
				}
				launch.getWindow().repaint();
				break;
			case 2://player 1
				if (utils.isPtInSquare(e.getPoint(), new Point(626, 356), 160, 65)) { //hit button highlight
					paint.setButtonsHighlight(true, false);
				} else if (utils.isPtInSquare(e.getPoint(), new Point(622, 447), 160, 65)) { //stay button highlight
					paint.setButtonsHighlight(false, true);
				} else { //no button highlight
					paint.setButtonsHighlight(false, false);
				}
				launch.getWindow().repaint();
				break;
		}
	}

}
