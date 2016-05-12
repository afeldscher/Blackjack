/*
 * Author: Jake Carfagno
 * Co-Author: Adam Feldscher
 * 
 * PURPOSE: Stores and manipulates the deck; holds the game's phase and ArrayLists for player and dealer hands;
 * Tracks hand count and number of hands player has won and lost
 */

import java.util.ArrayList;

public class Dealer
{
	// The current phase the game is in (0 = Shuffle Deck, 1 = Deal, 2 = Player Turn, 3 = Dealer Turn, 4 = Result)
	private int phase;
	
	Deck deck;
	Painter paint;
	Shuffler shuffle;
	private ArrayList dealerHand = new ArrayList(), playerHand = new ArrayList();
	
	public Dealer(Painter paint)
	{
		this.paint = paint;
		shuffle = new Shuffler(paint, this);
		// Enter Phase 0: Shuffling the Deck
		phase = 0;

		shuffle.start(); //goes to phase 1 once it is done shuffling
		// COMMENTED FOR RUNNING WITH ERRORS
		 // Create the deck
		deck = new Deck();
		
		// Enter Phase 1: Dealing Cards - phase set by shuffler when done animation
		dealerHand = new ArrayList<Card>();
		playerHand = new ArrayList<Card>();
		
		// Deal cards to player and dealer, starting with player and alternating
		playerHand.add(deck.drawCard());
		dealerHand.add(deck.drawCard());
		playerHand.add(deck.drawCard());
		dealerHand.add(deck.drawCard());
		
		// Enter Phase 2: Player Turn
	}
	
	public void startHands()
	{
		paint.setDrawCardsDeltList(new ArrayList<String>());
		
		phase = 0;
		deck = new Deck();
		dealerHand = new ArrayList<Card>();
		playerHand = new ArrayList<Card>();
		shuffle = new Shuffler(paint, this);
		shuffle.start();
		// Deal cards to player and dealer, starting with player and alternating
		playerHand.add(deck.drawCard());
		dealerHand.add(deck.drawCard());
		playerHand.add(deck.drawCard());
		dealerHand.add(deck.drawCard());
	}
	
	public int[][] getShuffleCards (){
		return shuffle.getShuffleCards();
	}
	
	public void setState(int state){
		this.phase = state;
	}
	
	// Returns the game's current phase
	public int getState()
	{
		return phase;
	}
	
	// Returns an array containing the dealer and player's hands (0 = Dealer, 1 = Player)
	public ArrayList<Card>[] getHands()
	{
		// The cast is necessary because simply creating an array of ArrayLists does not work
		ArrayList<Card>[] hands = (ArrayList<Card>[])new ArrayList[2];
		hands[0] = dealerHand;
		hands[1] = playerHand;
		
		return hands;
	}
	
	// Takes in a parameter (0 = stay, 1 = hit) and draws card for player if hit or moves to dealer turn if stay
	public void playerAction(int action)
	{
		// Action = 0 if player chooses to stay
		if(action == 0)
		{
			// Enter Phase 3: Dealer Turn
			phase = 3;
			dealerTurn();
		}
		else
		{
			playerHand.add(deck.drawCard());
			
			if(getHandValue(playerHand) > 21)
			{
				phase = 4;
			}
		}
	}
	
	// Determines the value of all cards together in a hand specified by parameter (0 = dealer, 1 = player)
	public int getHandValue(ArrayList<Card> hand)
	{
		int total = 0;
		
		// Ace count is tracked in case a hand busts and switching ace value from 11 to 1 could save the hand
		int aceCount = 0;
		
		for(Object card : hand)
		{
			total += ((Card)card).getValue();
			
			if(((Card)card).getName().equals("Ace"))
			{
				aceCount++;
			}
		}
		
		// Reduces total to be less than 21 if the hand contains aces and decrements aceCount with each reduction
		while(total > 21 && aceCount > 0)
		{
			total -= 10;
			aceCount--;
		}
		
		return total;
	}
	
	// Allows the dealer to take his turn, drawing cards until his hand reaches a value equal to or greater than 17
	private void dealerTurn()
	{
		while(getHandValue(dealerHand) < 17)
		{
			dealerHand.add(deck.drawCard());
		}
		DealAnimate dealAni = new DealAnimate(paint, this, false); //Moved out side of loop because it only needs to be called once
		dealAni.start();
		phase = 4;
	}
}