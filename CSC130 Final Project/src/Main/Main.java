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
	
	public static ArrayList<spriteInfo> spriteDisplayed = new ArrayList<>(); //Placeholder sprite displayed on screen for left,right sprites
	public static int currentSprite = 0;
	
	public static int nextSpriteIndexRight = 0;
	public static int nextSpriteIndexLeft = 0;
	public static int nextSpriteIndexBack = 0;
	public static int nextSpriteIndexFront = 0;

	public static ArrayList<spriteInfo> collisionObjects = new ArrayList<>();
	
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
		
		
		grass = new spriteInfo(new Vector2D(0,0), "grass"); //Creates the grass background
		
		grapes = new spriteInfo(new Vector2D(500,350), "grapes"); //Creates the grapes
		birdFriend = new spriteInfo(new Vector2D(0,0), "birdFriend"); //Creates the other bird
		
		treesHorizontalTop = new spriteInfo(new Vector2D(0,0), "treesHorizontalT");
		treesHorizontalBot = new spriteInfo(new Vector2D(0,0), "treesHorizontalB");
		treesVerticalLeft = new spriteInfo(new Vector2D(0,0), "treesVerticalL");
		treesVerticalRight = new spriteInfo(new Vector2D(0,0), "treesVerticalR");


		
		//Add sprites to arraylist
		spriteDisplayed.add(new spriteInfo(new Vector2D(currentVec.getX(), currentVec.getY()), "birdwalkR" + Integer.toString(currentSprite)));
		
		collisionObjects.add(treesHorizontalTop);
		collisionObjects.add(treesHorizontalBot);
		collisionObjects.add(treesVerticalRight);
		collisionObjects.add(treesVerticalLeft);

		collisionObjects.add(grapes);
		collisionObjects.add(birdFriend);
		
		
		//Make the character a bounding box
		characterBox = new boundingBox(spriteDisplayed.get(0), 295,420,400,520); //Initial character position
		
		//Create the trees as bounding boxes													   
		treeBoxNorth = new boundingBox(treesHorizontalTop, 50,1250,60,200); //x1 = left of sprite, x2 = right of sprite, y1 = top, etc...
		treeBoxSouth = new boundingBox(treesHorizontalBot, 50,1250,550,700);
		treeBoxWest = new boundingBox(treesVerticalLeft, 20,185,190,530);
		treeBoxEast = new boundingBox(treesVerticalRight, 1115,1275,190,530);
		
		grapesBox = new boundingBox(grapes, 500,570,350,410);
		birdFriendBox = new boundingBox(birdFriend, 1000, 1100,430,550);
		
		
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
		
	
		
		//Clean this up with a for loop (maybe add the objects into a container?)
		 ctrl.addSpriteToFrontBuffer(0,0,grass.getTag()); //Adds the grass background to the screen
		 
		 ctrl.addSpriteToFrontBuffer(0, 0, collisionObjects.get(0).getTag());	    //Top
		 ctrl.addSpriteToFrontBuffer(0, 500, collisionObjects.get(1).getTag());     //Bot
		 ctrl.addSpriteToFrontBuffer(1100, 185, collisionObjects.get(2).getTag());  //Right
		 ctrl.addSpriteToFrontBuffer(0, 175, collisionObjects.get(3).getTag());     //Left
		 //ctrl.addSpriteToFrontBuffer(-300, -40, collisionObjects.get(4).getTag());   //Grapes
		 //ctrl.addSpriteToFrontBuffer(520, 300, collisionObjects.get(4).getTag());   //Grapes
		 ctrl.addSpriteToFrontBuffer(grapes.getCoords().getX(), grapes.getCoords().getY(), collisionObjects.get(4).getTag());   //Grapes
		 ctrl.addSpriteToFrontBuffer(1000, 425, collisionObjects.get(5).getTag());   //BirdFriend

		 System.out.println(grapes.getCoords().getX() + " " + grapes.getCoords().getY());
		 
		//Adding sprite to the screen with its corresponding parameters

		String tag = spriteDisplayed.get(currentSprite).getTag();
		int xCoord = spriteDisplayed.get(currentSprite).getCoords().getX();
		int yCoord = spriteDisplayed.get(currentSprite).getCoords().getY();
		
		ctrl.addSpriteToFrontBuffer(xCoord, yCoord, tag);
		
		//When timer is up, next line in script.txt is displayed from the hashmap
		if(textTimer.isTimeUp()) {
			textIndex++;
			if(textIndex > 5) {
				textIndex = 1;
			}
			textTimer.resetWatch();
		}
		
		ctrl.drawString(100, 250, map.get("string" + Integer.toString(textIndex)), c); //Displays text to the screen
	}
	
	// Additional Static methods below...(if needed)

}
