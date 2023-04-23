/* This class is used to give you a handy way to pass a pair of 2D coordinates as a single object. The behavior (methods) of the class should allow for robust options in the future. */

package Data;

public class Vector2D {
	// Fields
	private int xVal;
	private int yVal;
	
	// Constructor
	public Vector2D(int x, int y){
		xVal = x;
		yVal = y;
	}
	
	// Methods
	public int getX(){
		return xVal;
	}
	
	public int getY(){
		return yVal;
	}
	
	public void setX(int newX){
		xVal = newX;
	}
	
	public void setY(int newY){
		yVal = newY;
	}
	
	public void adjustX(int adjustment){
		// Backward adjustments can be made by passing a negative number as an adjustment
		xVal += adjustment;
	}
	
	public void adjustY(int adjustment){
		// Backward adjustments can be made by passing a negative number as an adjustment
		yVal += adjustment;
	}
}
