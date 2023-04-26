package Data;

public class boundingBox {
	private spriteInfo spriteData;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
    //private int height;
    //private int width;
    
    public boundingBox(spriteInfo spriteData, int x1, int x2, int y1, int y2) {
    	this.spriteData = spriteData;
    	this.x1 = x1;
    	this.x2 = x2;
    	this.y1 = y1;
    	this.y2 = y2;
    }
    
    

}
