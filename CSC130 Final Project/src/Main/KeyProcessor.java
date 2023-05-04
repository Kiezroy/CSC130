/* This will handle the "Hot Key" system. */

package Main;

import Data.boundingBox;
import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		
		switch(key){
		
		case '%':								// ESC key
			System.exit(0);
			break;
			
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
			
		case 'w':
			boolean stopW;

			//If a collision is true, make the character stop being able to move that direction
			stopW = stop(Main.characterBox, Main.treeBoxNorth);
			
			//Allows player to move back up if hit boundary
			if(Main.characterBox.gety2() > Main.treeBoxSouth.gety1()) {
				stopW = false;
			}
			
			Main.nextSpriteIndexFront = 0; //Resets front side sprite so that if 's' is pressed, starts at frame 0

			if(Main.nextSpriteIndexBack > 2) {
				Main.nextSpriteIndexBack = 0;
			}
			
			if(stopW == false) {
				//Move the character and change the sprite images
				Main.spriteDisplayed.get(Main.currentSprite).setTag("back" + Integer.toString(Main.currentSprite + Main.nextSpriteIndexBack)); 
				Main.spriteDisplayed.get(Main.currentSprite).getCoords().adjustY(-35);
				Main.nextSpriteIndexBack++;
				
				//Adjust the boundingBox
				Main.characterBox.adjustY1(-35);
				Main.characterBox.adjustY2(-35);
			}
			
			break;
			
		case 's':
			boolean stopS;
			
			//If a collision is true, make the character stop being able to move that direction
			stopS = stop(Main.characterBox, Main.treeBoxSouth);
			
			//System.out.println("Tree North: "+ Main.treeBoxNorth.gety2());
			
			//Allows player to move back down if hit boundary
			if(Main.characterBox.gety1() < Main.treeBoxNorth.gety2()) {
				stopS = false;
			}
			
			Main.nextSpriteIndexBack = 0; //Resets back side sprite so that if 'w' is pressed, starts at frame 0

			if(Main.nextSpriteIndexFront > 2) {
				Main.nextSpriteIndexFront = 0;
			}
			
			if(stopS == false) {
				Main.spriteDisplayed.get(Main.currentSprite).setTag("front" + Integer.toString(Main.currentSprite + Main.nextSpriteIndexFront)); 
				Main.spriteDisplayed.get(Main.currentSprite).getCoords().adjustY(35);
				Main.nextSpriteIndexFront++;
				
				//Adjust the boundingBox
				Main.characterBox.adjustY1(35);
				Main.characterBox.adjustY2(35);
			}

			break;
		
		case 'd':		
			//IMPORTANT: FIND METHOD TO SOLVE WHEN BIRD WALKS RIGHT BUG
						//something to do with adjustX before nextspriteindexright is incremented
			
			boolean stopD;
			stopD = stop(Main.characterBox, Main.treeBoxEast);

			//Allows player to move back East if hit boundary
			if(Main.characterBox.getx2() < Main.treeBoxWest.getx1()) {
				stopD = false;
			}
			
			
			Main.nextSpriteIndexLeft = 0; //Resets left side sprite so that if 'a' is pressed, starts at frame 0

			//If reaches last frame while moving right, reset counter
			if(Main.nextSpriteIndexRight > 3) {
				Main.nextSpriteIndexRight = 0;
			}
			
			if(stopD == false) {
				Main.spriteDisplayed.get(Main.currentSprite) //When 'd' is pressed, tag of sprite changes to face right side
									.setTag("birdwalkR" + Integer.toString(Main.currentSprite + Main.nextSpriteIndexRight));
				Main.spriteDisplayed.get(Main.currentSprite).getCoords().adjustX(35); //Adjust x coords by 35 pixels
				Main.nextSpriteIndexRight++; //Next sprite incremented to be displayed
				
				//Adjust the boundingBox
				Main.characterBox.adjustX1(35);
				Main.characterBox.adjustX2(35);
			}
			
			break;
			
		case 'a':
			boolean stopA;
			stopA = stop(Main.characterBox, Main.treeBoxWest);
			
			//Allows player to move back West if hit boundary
			if(Main.characterBox.getx1() > Main.treeBoxEast.getx2()) {
				stopA = false;
			}
			
			Main.nextSpriteIndexRight = 0; //Resets left side sprite so that if 'd' is pressed, starts at frame 0
			
			if(Main.nextSpriteIndexLeft > 3) {
				Main.nextSpriteIndexLeft = 0;
			}
			
			
			if(stopA == false) {
				Main.spriteDisplayed.get(Main.currentSprite).setTag("birdwalkL" + Integer.toString(Main.currentSprite + Main.nextSpriteIndexLeft)); 
				Main.spriteDisplayed.get(Main.currentSprite).getCoords().adjustX(-35);
				Main.nextSpriteIndexLeft++;
				
				//Adjust the boundingBox
				Main.characterBox.adjustX1(-35);
				Main.characterBox.adjustX2(-35);
			}
				
			
			break;
		}
	}
	
	//Method for if a collision is true, make the character stop being able to move that direction
	public static boolean stop(boundingBox box1, boundingBox box2) {
		if(noCollision(box1, box2)) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean noCollision(boundingBox box1, boundingBox box2){
		if (((box1.getx1() > box2.getx2()) 
			|| (box1.getx2() < box2.getx1()) 
			|| (box1.gety1() > box2.gety2()) 
			|| (box1.gety2() < box2.gety1()))) {
			
			return false; //returns no collision
			
		}else{
				return true; //returns yes a collision occurs
			}
		}
}