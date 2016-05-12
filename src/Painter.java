
/*
 * author: Adam Feldscher
 * coAuthor: Jake C
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Painter extends JComponent {

	private Dealer dealer;
	private int state;
	private Image startScreen, redChip, blueChip, redBack, buttons, buttonsHitH, buttonsStayH;
	private boolean startButtonHighlight, hitHighlight, stayHighlight;
	private ShapeUtils utils = new ShapeUtils();
	private Launcher launch;
	private ArrayList<String> drawCardsDelt = new ArrayList(); //0 is card that needs to be drawn, 1 is point to draw it at;

	public Painter(Launcher launch) { // draw startup screen
		this.launch = launch;
		
		try { //try to load the image files
			startScreen = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/startScreen.jpg"));
			redChip = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/redChip.png"));
			blueChip = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/blueChip.png"));
			redBack = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/cards/b2fv.png"));
			buttons = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/buttons.png"));
			buttonsHitH = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/buttonsHitH.png"));
			buttonsStayH = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/buttonsStayH.png"));
		} catch (Exception e) {
			System.out.println("Image File Read error");
		}
	}

	public void paintComponent(Graphics g) {
		Image backBuffer = createImage(800, 650);
		Graphics2D g2 = (Graphics2D) backBuffer.getGraphics(); //backbuffer to prevent flicker
		drawBackground(g2);

		if (dealer == null) { //if dealer is not instantiated
			dealer = launch.getDealer(); //try to get the instance of launch
		}
		
		if (dealer == null) { //if dealer not created yet
			state = -1;
		} else { //get the state from the dealer
			state = dealer.getState();
		}
		
		switch (state) {
			case -1: // start up screen
				g2.drawImage(startScreen, 0, 0, this);
				g2.drawImage(startButtonHighlight ? blueChip : redChip, 195, 255, this);//if the mouse is over the icon draw the blue image else red
				break;
			case 0: // shuffle
				g2.drawImage(redBack, 20, 5, this); //cards at the bottom of the pile
				g2.drawImage(redBack, 13, 5, this);
				int cardLocation[][] = dealer.getShuffleCards();
				for (int i = 0; i < 52; i++){
					double angleRAD = Math.toRadians(cardLocation[i][2]); //convert the given angle to radians
					Point convert = utils.rotatePoint(new Point(cardLocation[i][0], cardLocation[i][1]), angleRAD); //convert the point to account for the axis rotation
					g2.rotate(angleRAD); //rotate
					g2.drawImage(redBack, convert.x + 5, convert.y + 5, this); // +5 is a shift of all cards so they stack @ 5,5
					g2.rotate(-angleRAD); //rotate back
				}
				break;
			//Compare the results if the phase is 4 and continue into drawing cards
			case 4:
				//Declare integers to store player and dealer hand values
				int playerValue, dealerValue;
				playerValue = dealer.getHandValue(dealer.getHands()[1]);
				dealerValue = dealer.getHandValue(dealer.getHands()[0]);
				
				//Set font and color
				g2.setColor(Color.white);
				g2.setFont(new Font("Arial Black", Font.PLAIN, 20));
				//Check for busts
				if (playerValue == 21 && dealer.getHands()[1].size() == 2) {
					g2.drawString("Blackjack! You won!", 200, 200);
				} else if (dealerValue == 21 && dealer.getHands()[0].size() == 2) {
					g2.drawString("Dealer Blackjack. You lost.", 200, 200);
				} else if(playerValue > 21) {
					g2.drawString("You busted. You lost.", 200, 200);
				} else if(dealerValue > 21) {
					g2.drawString("Dealer busted. You won!", 200, 200);
				} else if(playerValue > dealerValue) { //neither busted
					g2.drawString("You won!", 200, 200);
				} else if(dealerValue > playerValue) { //neither busted
					g2.drawString("You lost.", 200, 200);
				} else if(playerValue == dealerValue) {//neither busted
					g2.drawString("Push. Tie.", 200, 200);
				}
				g2.drawString("Click anywhere to restart.", 200, 230);
			case 1: //deal
			case 2: //player turn
			case 3: //dealer turn
				g2.drawImage(redBack, 20, 5, this); // draws deck
				g2.drawImage(redBack, 13, 5, this);
				g2.drawImage(redBack, 5, 5, this);
				
				for (int i = 0; i < drawCardsDelt.size(); i++) {
					String currCard = drawCardsDelt.get(i);
					String filename = currCard.substring(0, currCard.indexOf(','));
					
					if (i == 0 && dealer.getState() < 3){ //don't display the dealers first card
						filename = "b2fv";
					}
					currCard = currCard.substring(currCard.indexOf(',') + 1); //remove 1st part of the string
					int x = Integer.parseInt(currCard.substring(0, currCard.indexOf(','))); //get x value
					currCard = currCard.substring(currCard.indexOf(',') + 1); //remove next part of string
					int y = Integer.parseInt(currCard); //get y
					
					try {
						g2.drawImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/cards/" + filename + ".png")), x, y, this);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (drawCardsDelt.size() >=4) {
					g2.drawImage(hitHighlight ? buttonsHitH : (stayHighlight ? buttonsStayH : buttons), 600, 300, this); //if the hit is highlighted, use that picture, else if stay button is highlighted use that picture, else normal buttons.
				}
				
				//get array of arraylists from dealAnimate that tells it where to draw cards. this list will contain the animated cards
				// and the player / dealer cards. Function in painter, setDeltCardsList(Array of ArrayList).
				//array list contains 0 = image, 1 = point for location of card.
				break;
		}

		g.drawImage(backBuffer, 0, 0, this); //draws the entire image at once so you don't see any flicker
	}
	
	private void drawBackground (Graphics2D g2) {
		g2.setColor(Color.decode("#017127"));
		g2.fillRect(0, 0, 800, 650);
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.drawLine(0, 300, 800, 300);
		g2.drawRect(365, 350, 75, 100);
	}

	public void setDrawCardsDeltList(ArrayList<String> drawCardsDelt) {
		this.drawCardsDelt = drawCardsDelt;
	}
	
	public ArrayList<String> getDrawCardsDeltList(){
		return drawCardsDelt;
	}
	
	public int getState() { //returns the painter state, controlled by the dealer
		return state;
	}
	public void setStartHighlight(boolean isHighlighted) { //sets if the mouse is hovering over the start button
		this.startButtonHighlight = isHighlighted;
	}
	public void setButtonsHighlight(boolean hitHighlight, boolean stayHighlight){
		this.hitHighlight = hitHighlight;
		this.stayHighlight = stayHighlight;
	}
}
