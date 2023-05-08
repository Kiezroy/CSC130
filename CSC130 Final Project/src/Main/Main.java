package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import Data.Vector2D;
import Data.boundingBox;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	public static Color c = new Color(0, 0, 0);
	public static boolean isImageDrawn = false;
	public static stopWatchX timer = new stopWatchX(100);
	public static stopWatchX textTimer = new stopWatchX(3500); //Timer for length of displaying Bluey's text
	
	public static ArrayList<spriteInfo> spriteDisplayed = new ArrayList<>(); //Character sprite displayed on screen for left,right sprites
																		     //Fulfills Image Data Java Collection Requirement
	public static int currentSprite = 0;
	
	public static int nextSpriteIndexRight = 0;
	public static int nextSpriteIndexLeft = 0;
	public static int nextSpriteIndexBack = 0;
	public static int nextSpriteIndexFront = 0;
	
	//Activates text if player hits spacebar near the items
	public static boolean birdFriendText = false;
	public static boolean foundGrapes = false;	//Tracks if grapes have been found by the player
	public static boolean askBird = false;	//Tracks if player talked to birdFriend yet
	public static boolean finished = false;	//Tracks if player has finished their task

	public static ArrayList<boundingBox> collisionObjects = new ArrayList<>();
	
	//Object and background sprites
	public static spriteInfo grass;
	public static spriteInfo grapes;
	public static spriteInfo birdFriend;

	//Tree sprites
	public static spriteInfo treesHorizontalTop;
	public static spriteInfo treesHorizontalBot;
	public static spriteInfo treesVerticalLeft;
	public static spriteInfo treesVerticalRight;

	//Bounding boxes
	public static boundingBox characterBox;
	public static boundingBox treeBoxNorth;
	public static boundingBox treeBoxSouth;
	public static boundingBox treeBoxEast;
	public static boundingBox treeBoxWest;
	public static boundingBox grapesBox;
	public static boundingBox birdFriendBox;
	
	public static HashMap<String, String> map = new HashMap<>(); //Map to store key and values of script.txt
	public static int textIndex = 1; //Index to store line of script.txt

	
	public static Vector2D currentVec = new Vector2D(300, 400);
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
		
		
		grass = new spriteInfo(new Vector2D(0,0), "grass"); //Creates the grass background with coordinates	
		grapes = new spriteInfo(new Vector2D(500,350), "grapes"); //Creates the grapes
		birdFriend = new spriteInfo(new Vector2D(1000, 425), "birdFriend"); //Creates the other bird
		
		treesHorizontalTop = new spriteInfo(new Vector2D(0,0), "treesHorizontalT");
		treesHorizontalBot = new spriteInfo(new Vector2D(0,500), "treesHorizontalB");
		treesVerticalRight = new spriteInfo(new Vector2D(1100, 185), "treesVerticalR");
		treesVerticalLeft = new spriteInfo(new Vector2D(0, 175), "treesVerticalL");
		
		//Add character sprites to arraylist
		spriteDisplayed.add(new spriteInfo(new Vector2D(currentVec.getX(), currentVec.getY()), "birdwalkR" + Integer.toString(currentSprite)));
		
		
		//Make the character a bounding box with box coordinates
		characterBox = new boundingBox(spriteDisplayed.get(0), 295,400,420,500); //Initial character position
		
		//Create the trees as bounding boxes													   
		treeBoxNorth = new boundingBox(treesHorizontalTop, 50,1250,60,200); //x1 = left of sprite, x2 = right of sprite, y1 = top, etc...
		treeBoxSouth = new boundingBox(treesHorizontalBot, 50,1250,550,700);
		treeBoxEast = new boundingBox(treesVerticalRight, 1115,1275,190,530);
		treeBoxWest = new boundingBox(treesVerticalLeft, 20,185,190,530);
		
		grapesBox = new boundingBox(grapes, 500,540,330,410);
		birdFriendBox = new boundingBox(birdFriend, 980, 1100,430,550);
		
		collisionObjects.add(treeBoxNorth);
		collisionObjects.add(treeBoxSouth);
		collisionObjects.add(treeBoxEast);
		collisionObjects.add(treeBoxWest);
		collisionObjects.add(grapesBox);
		collisionObjects.add(birdFriendBox);
		
		
		
		EZFileRead ezr = new EZFileRead("script.txt"); 		//File reader opens script text to read from
				
		//Iterates through file and stores keys and values into map
		for (int i = 0; i < ezr.getNumLines(); i++){
			StringTokenizer st = new StringTokenizer(ezr.getLine(i), "*"); //Splits text into tokens at "*" at line i
			
			String key = st.nextToken();
			String value = st.nextToken();
			
			map.put(key, value);
		}
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
		
		 ctrl.addSpriteToFrontBuffer(0,0,grass.getTag()); //Adds the grass background to the screen

		 //Loops through objects that can be collided with and adds them to the screen
		 for(int i = 0; i < collisionObjects.size(); i++) {
			 ctrl.addSpriteToFrontBuffer(collisionObjects.get(i).getSpriteData().getCoords().getX(), 
					 				     collisionObjects.get(i).getSpriteData().getCoords().getY(), 
					 				     collisionObjects.get(i).getSpriteData().getTag());
		 }
		 
		 
		//Adding character sprite to the screen with its corresponding parameters
		String tag = spriteDisplayed.get(currentSprite).getTag();
		int xCoord = spriteDisplayed.get(currentSprite).getCoords().getX();
		int yCoord = spriteDisplayed.get(currentSprite).getCoords().getY();
		
		ctrl.addSpriteToFrontBuffer(xCoord, yCoord, tag);
		

		if (askBird == false && foundGrapes) {		//If the player has not talked to the bird and interacts with the grapes
		    if (textTimer.isTimeUp()) {
		        foundGrapes = false;
		    } else {
		    	ctrl.drawString(300, 300, map.get("string" + Integer.toString(textIndex)), c);   
		    }
		}else if(askBird && foundGrapes) {	//If the player already talked to the bird and interacts with the grapes
			if (textTimer.isTimeUp()) {
		        foundGrapes = false;
		        finished = true;
		    } else {
		    	ctrl.drawString(300, 300, map.get("string" + Integer.toString(2)), c);  
		    }
		}

		
		if (birdFriendText) {		//If player talks to the bird
		    if (textTimer.isTimeUp()) {
		        birdFriendText = false;
		    }else if(finished) {
		        ctrl.drawString(300, 300, map.get("string" + Integer.toString(4)), c); 
		    } 
		    else {
		        ctrl.drawString(300, 300, map.get("string" + Integer.toString(3)), c); 
		    }
		}
		

	}
	
	// Additional Static methods below...(if needed)

}
