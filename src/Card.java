/*
 * Author: Jake Carfagno
 * Co-Author: Adam Feldscher
 * 
 * PURPOSE: This class is used as a template for the Card objects of which the deck and hands will be comprised
 * Each Card object contains a name (such as "Two" and "Jack"), a value (Number card values are just their number,
 * faces are 10, and ace starts as 11), and suit (Spades, Clubs, Hearts, Diamonds)
 * 
 * NOTE: I tried to get used to opening brackets on the same line, but it felt so weird that I had to revert to my
 * blasphemous ways
 */

public class Card
{
	
	private String name, suit;
	private int value;
	
	/*
	 * FUNCTION: Create a Card object and store values for name, suit, and value; determines value based on the name
	 * PARAMETERS: nm - the Card object's string name; sut - the Card's suit
	 */
	public Card(String nm, String sut)
	{
		name = nm;
		suit = sut;
		
		// Determine the Card's numerical value based on the name
		switch(name)
		{
			case "Two":
				value = 2;
				break;
			case "Three":
				value = 3;
				break;
			case "Four":
				value = 4;
				break;
			case "Five":
				value = 5;
				break;
			case "Six":
				value = 6;
				break;
			case "Seven":
				value = 7;
				break;
			case "Eight":
				value = 8;
				break;
			case "Nine":
				value = 9;
				break;
			// Sets value to 10 for Ten, Jack, Queen, and King
			case "Ten":
			case "Jack":
			case "Queen":
			case "King":
				value = 10;
				break;
			case "Ace":
				value = 11;
				break;
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getValue()
	{
		return value;
	}
	
	// Returns the "key" used to determine which picture to use
	// Creates the key by concatenating first char of suit and numeral of name (if 1-10) or first letter of face card name
	public String getKey()
	{
		String key = suit.substring(0,1).toLowerCase();
		
		// Determine the Card's numerical value based on the name
				switch(name)
				{
					case "Two":
						key += "2";
						break;
					case "Three":
						key += "3";
						break;
					case "Four":
						key += "4";
						break;
					case "Five":
						key += "5";
						break;
					case "Six":
						key += "6";
						break;
					case "Seven":
						key += "7";
						break;
					case "Eight":
						key += "8";
						break;
					case "Nine":
						key += "9";
						break;
					case "Ten":
						key += "10";
						break;
					case "Jack":
						key += "j";
						break;
					case "Queen":
						key += "q";
						break;
					case "King":
						key += "k";
						break;
					case "Ace":
						key += "a";
						break;
					default:
						key = "b2fv"; //card back
						break;
				}
		
		return key;
	}
	
	public String toString()
	{
		return name + " of " + suit;
	}
}