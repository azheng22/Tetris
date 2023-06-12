package tetris;

import java.util.ArrayList;

public class Square 
{
	public int x;
	public int y;
	public ArrayList<Integer> colorsRGB;
	
	public Square(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		colorsRGB = new ArrayList<Integer>();
	}
	
}
