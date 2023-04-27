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

	public int getx1() {
		return this.x1;
	}

	public int getx2() {
		return this.x2;
	}

	public int gety1() {
		return this.y1;
	}

	public int gety2() {
		return this.y2;
	}
    
	//Methods to allow for the adjustment of the boundingBox as it moves when keys are pressed
    public void adjustX1(int xAdjust) {
    	this.x1 += xAdjust;
    }
    
    public void adjustX2(int xAdjust) {
    	this.x2 += xAdjust;
    }
    
    public void adjustY1(int yAdjust) {
    	this.y1 += yAdjust;
    }
    
    public void adjustY2(int yAdjust) {
    	this.y2 += yAdjust;
    }

}
