/*
 * Author: Jake Carfagno
 * Co-Author: Adam Feldscher
 * 
 * PURPOSE: This class stores an ArrayList of Card objects and allows for manipulation of the deck with functions to
 * shuffle and obtain the top card
 */

import java.util.ArrayList;

public class Deck
{
	private ArrayList deck = new ArrayList();
	
	public Deck()
	{
		// Instantiate the deck ArrayList with the ArrayList returned by buildDeck()
		deck = buildDeck();
		shuffle();
	}
	
	// Builds the deck and then 
	private ArrayList<Card> buildDeck()
	{
		ArrayList deck = new ArrayList<Card>();
		
		// Array containing all card names through which a for loop will cycle to create entire deck in order
		String[] names = {
			"Two",
			"Three",
			"Four",
			"Five",
			"Six",
			"Seven",
			"Eight",
			"Nine",
			"Ten",
			"Jack",
			"Queen",
			"King",
			"Ace"
		};
		
		// Array containing all possible suits through which a for loop will cycle to create entire deck in order
		String[] suits = {
			"Clubs",
			"Spades",
			"Hearts",
			"Diamonds"
		};
		
		// Adds a new card to the deck with suit of the i'th place of suits and name of the j'th place of names
		// Deck will be sorted numerically by suit
		for(int i = 0; i < suits.length; i++)
		{
			for(int j = 0; j < names.length; j++)
			{
				deck.add(new Card(names[j], suits[i]));
			}
		}
		
		// Shuffles the deck
		shuffle();
		
		return deck;
	}
	
	// Shuffles the deck by placing each card into a random "pile" before putting all cards back into the deck in order by
	// pile; There are 13 separate piles to increase chances that the cards will be spread out
	private void shuffle()
	{
		// Creates an array that stores 13 Card arrays
		// Each Card array can be thought of as a pile into which one card might be thrown before they are all combined
		Card[][] piles = new Card[13][4];
		
		// Goes through each card in the deck and distributes them among the piles
		for(int index = deck.size() - 1; index > -1; index--)
		{
			
			/* Keeps generating a new random pile number into which it will try to put the last card in the deck;
			 * Checks every entry in the pile for a null entry and replaces the null if one is found]
			 * Toggles isValid to true if a null entry is replaced and otherwise lets the loop run again
			 */
			boolean isValid = false;
			do
			{
				// Chooses a random "pile" into which the card will be placed while shuffling
				int pile = (int)Math.floor(Math.random() * 13);
				
				
				for(int i = 0; i < piles[pile].length; i++)
				{
					if(piles[pile][i] == null && deck.size() > index)
					{
						piles[pile][i] = (Card)deck.remove(index);
						
						isValid = true;
					}
				}
			} while(!isValid);
		}
		
		// Rebuilds the deck using all piles, going through each pile and adding each card from the pile
		for(int pile = 0; pile < piles.length; pile++)
		{
			for(int card = 0; card < piles[pile].length; card++)
			{
				deck.add(piles[pile][card]);
			}
		}
	}
	
	// Simulates drawing a card from the top of the deck by calling the ArrayList function remove, which returns the
	// object removed from the list
	public Card drawCard()
	{
		if(deck.size() == 0)
		{
			deck = buildDeck();
			shuffle();
		}
		return (Card)deck.remove(0);
	}
}