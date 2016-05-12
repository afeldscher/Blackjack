//Author: Adam Feldscher
// Purpose: Animates the dealing of the cards

import java.awt.Point;
import java.util.ArrayList;

public class DealAnimate extends Thread {
	private Dealer dealer;
	private Painter paint;
	private boolean initial;
	
	public DealAnimate(Painter paint, Dealer dealer, boolean initial) { //initial sets where it is the first deal so the cards are delt in a "criss cross" fashion. 
		this.paint = paint;
		this.dealer = dealer;
		this.initial = initial;
	}
	
	public void run() { //called when the thread is run.
		ArrayList<Card>[] hands = dealer.getHands();
		ArrayList<String> drawCardsDelt = paint.getDrawCardsDeltList(); //strings in format "fileName, x, y"
		for (int i = 0; i < 2; i++) { //runs through both the dealer 0 and the player 1
			for (int j = 0; j < hands[i].size(); j++) { //runs through every card in the hand
				int i2 = initial ? j : i; //if it is the first deal then switch i and j so that it deals the player 1 and then the dealer 1, and then 1 and 1.
				int j2 = initial ? i : j;
				if (!concatArrayList(drawCardsDelt).contains(hands[i2].get(j2).getKey())) { //if the current drawn array list does not contain the current card
					Point endLocation = new Point(500 - j2 * 80, 35 + i2 * 325); //move left for each card. Move up or down for dealer or player. Dealer  i = 0, player 1
					animateCard(endLocation, drawCardsDelt); //animates the card sliding into its location
					drawCardsDelt.add(hands[i2].get(j2).getKey() + "," + endLocation.x + "," + endLocation.y); //adds the card so it is drawn
					paint.setDrawCardsDeltList(drawCardsDelt); //updates the list
					paint.repaint();
				}
			}
		}
		if (dealer.getState() == 1) { //if it was in the deal state advance to the player state
			dealer.setState(2);
		}
	}
	
	private String concatArrayList(ArrayList<String> in) { //takes in an array list and outputs a string of each element concatenated into one string
		String out = "";
		for (int i = 0; i < in.size(); i++){
			out += in.get(i) + " ";
		}
		return out;
	}

	private void animateCard (Point endLocation, ArrayList<String> drawCardsDelt) { //animates the card going to the players hand from the deck
		double m = (endLocation.y - 5.0) / (endLocation.x - 5.0); //slope
		for	(int x = 5; x <= endLocation.x; x+= 10) {
			if (x != 5) { //if it is not the first run remove the last draw of the card
				drawCardsDelt.remove(drawCardsDelt.size() -1);
			}
			int y = (int) (m*(x-endLocation.x) + endLocation.y);
			
			drawCardsDelt.add("b2fv," + x + "," + y); //red back of card
			paint.setDrawCardsDeltList(drawCardsDelt);
			paint.repaint();
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		drawCardsDelt.remove(drawCardsDelt.size() -1); //remove the last card back
		paint.setDrawCardsDeltList(drawCardsDelt);
	}
}
