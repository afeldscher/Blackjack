//Author: Adam Feldscher
//Purpose: Moves 52 cards from random positions back to the deck in an animation
public class Shuffler extends Thread {
	private int cardLocation[][] = new int[52][3]; //[card] [0 = x, 1 = y, 2 = angle]
	
	private Painter paint;
	private Dealer dealer;
	private Timer time = new Timer();
	
	public Shuffler (Painter paint, Dealer dealer) {
		this.paint = paint;
		this.dealer = dealer;
	}
	
	public void run() { //called when the thread is started. Runs the animation of shuffling cards
		for (int i = 0; i < 52; i++) { //randomly assigns the initial location of all 52 cards
			cardLocation[i][0] = (int)(Math.random() * 780);
			cardLocation[i][1] = (int)(Math.random() * 600);
			cardLocation[i][2] = (int)(Math.random() * 360);
		}
		while(!checkCardsDone() && dealer.getState() == 0) { //moves the cards slowly twords the deck
			time.start();
			if (time.get() > 6) { //delay between draws. Timer makes motion smoother because thread.sleep is not precise. 
				for (int i = 0; i < 52; i++) { //for each card
					for (int j = 0; j < 3; j++) { //for each element of each card
						cardLocation[i][j] -= cardLocation[i][j] > 0 ? 4 : 0; //subtract 4 if the element is > 0. no neg numbers
																			  // brings cards back to center. 
						cardLocation[i][j] = cardLocation[i][j] < 0 ? 0 : cardLocation[i][j]; //if the card location is less then 0 set it to 0. Prevents overshoot.
					}
				}
				paint.repaint(); //refresh the screen (painter pulls the cardLocation through the dealer) 
				time.reset(); //resets the timer, doesn't stop it
			} else {
				try {
					Thread.sleep(2); //sleeps the thread if it is not time yet
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//Run Deal Animation
		DealAnimate dealAn = new DealAnimate(paint, dealer, true);
		
		//Check for blackjack
		if(dealer.getHandValue(dealer.getHands()[1]) != 21 && dealer.getHandValue(dealer.getHands()[0]) != 21)
		{
			dealer.setState(1);
		}
		else
		{
			dealer.setState(4);
		}
		dealAn.start();
	}
	
	public boolean checkCardsDone() { //checks if all of the cards are back in the deck
		for (int i = 0; i < 52; i++) { //for each card
			for (int j = 0; j < 3; j++) { //for each element of each card
				if (cardLocation[i][j] > 0){
					return false;
				}
			}
		}
		return true;
	}
	
	public int[][] getShuffleCards() {
		return cardLocation;
	}
}
