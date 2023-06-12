package buttons;

import processing.core.PApplet;

public class Button
{
	public static final int WIDTH = 75, HEIGHT = 25;
	
	private PApplet parent;
	private int topLeftX, topLeftY;
	
	public Button(PApplet parent, int tlx, int tly)
	{
		this.parent = parent;
		topLeftX = tlx;
		topLeftY = tly;
	}
	
	public void drawSelf()
	{
		parent.rect(topLeftX, topLeftY, WIDTH, HEIGHT);
	}
	
	public boolean isInside(int x, int y)
	{
		return x >= topLeftX && x <= topLeftX + WIDTH &&
				y >= topLeftY && y <= topLeftY + HEIGHT;
	}
}
