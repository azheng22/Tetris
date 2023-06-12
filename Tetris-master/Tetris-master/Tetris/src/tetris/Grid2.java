package tetris;

import java.util.ArrayList;

import processing.core.PApplet;

public class Grid2 extends PApplet
{
	private ArrayList<ArrayList<Square>> squares; //square added to arry list according to row num
	private Shape newShape;
	private boolean colliding;
	private int timeOfNextChange;
	private ArrayList<Integer> rowsToRemove; //array list of rows to remove
	
	private final int CHANGE_DELAY = 500;
	private final int GRID_WIDTH = 300;
	private final int GRID_LENGTH = 600;
	private final int SQUARE_WIDTH = 30;
	
	public static void main(String[] args)
	{
		PApplet.main("tetris.Grid");
	}
	
	public void settings()
	{
		size(420, 600);
	}
	
	public void setup()
	{
		squares = new ArrayList<ArrayList<Square>>();
		newShape = new Shape();
		colliding = false;
		timeOfNextChange = CHANGE_DELAY;
		rowsToRemove = new ArrayList<Integer>();
		
		for(int i = 0; i < 20; i++)
		{
			ArrayList<Square> temp = new ArrayList<Square>();
			squares.add(temp);
		}
	}
	
	public void draw()
	{
		background(0);
		drawGrid();	
		
		drawSquares();
		
		if(millis() >= timeOfNextChange && !colliding)
		{
			newShape.moveDown();
			timeOfNextChange = millis() + CHANGE_DELAY;
		}	
			
		drawShape();

		areColliding(newShape.squaresOfThisShape);
		
		if(colliding || newShape.getMostBottomSquare(newShape.squaresOfThisShape).y >= GRID_LENGTH - 30)
		{
			
			//System.out.println(colliding);
			
			for(Square s : newShape.squaresOfThisShape)
			{
				squares.get(determineRow(s.y)).add(s);
				//System.out.println("x:" + s.x + " y:" + s.y);
				
				s.colorsRGB.add(newShape.colorsRGB.get(0));
				s.colorsRGB.add(newShape.colorsRGB.get(1));
				s.colorsRGB.add(newShape.colorsRGB.get(2));
				
				//System.out.println("b" + rows[determineRow(s.y)]);
				//System.out.println(rows[determineRow(s.y)]);
			
				if(squares.get(determineRow(s.y)).size() == 10)
					rowsToRemove.add(determineRow(s.y));
			}
			
			newShape = new Shape();
			//System.out.println("\n-------------");
			colliding = false;
		}
		
		if(!rowsToRemove.isEmpty())
		{
			removeRows();
		}
	}
	
	public void keyPressed()
	{
		if(key == CODED)
		{
			if(keyCode == RIGHT) //move to the right
			{
				if(newShape.getMostRightSquare(newShape.squaresOfThisShape).x < GRID_WIDTH - 30)
				{
					for(Square s : newShape.squaresOfThisShape)	
						s.x += SQUARE_WIDTH;
					
					newShape.centerX += SQUARE_WIDTH;
				}
			}
			else if(keyCode == LEFT) //move to the left
			{
				if(newShape.getMostLeftSquare(newShape.squaresOfThisShape).x >= 30)
				{
					for(Square s : newShape.squaresOfThisShape)
						s.x -= SQUARE_WIDTH;
					
					newShape.centerX -= SQUARE_WIDTH;
				}	
			}
			else if(keyCode == UP)
			{
				rotate();
			}
		}
	}

	public boolean areColliding(ArrayList<Square> shape)
	{
		boolean temp = false;
		
		for(Square sq : shape)
		{
			for(int i = 0; i < squares.size(); i++)
			{
				for(Square s : squares.get(i))
				{
					if((sq.y + 30 == s.y) && (sq.x == s.x))
					{
						colliding = true;
						temp = true;
					}
				}
			}
		}
		
		return temp;
	
	}
	
	private void drawGrid()
	{
		fill(30, 40, 90);
		
		int y = 0;
		//horizontal lines
		for(int h = 0; h <= 20; h++)
		{
			rect(0, y, 300, 3);
			y += 30;
		}
		
		int x = 0;
		//vertical lines
		for(int h = 0; h <= 10; h++)
		{
			rect(x, 0, 3, 600);
			x += 30;
		}
	}

	private void drawShape()
	{
		fill(newShape.colorsRGB.get(0), newShape.colorsRGB.get(1), newShape.colorsRGB.get(2));
		
		for(Square s : newShape.squaresOfThisShape)
			rect(s.x, s.y, 30, 30);
	}
	
	private void drawSquares()
	{
		for(int i = 0; i < squares.size(); i++)
		{
			for(Square s : squares.get(i))
			{
				fill(s.colorsRGB.get(0), s.colorsRGB.get(1), s.colorsRGB.get(2));
				rect(s.x, s.y, 30, 30);
			}
		}
	}
	
	private void rotate()
	{
		ArrayList<Square> rotated = newShape.returnRotatedShape();
	
		if(canRotate(rotated))
		{
			if(!areColliding(rotated))
			{
				//System.out.println(newShape.centerX + " --- " + newShape.centerY);
				newShape.squaresOfThisShape = rotated;
			}
		}
	}
	
	public boolean canRotate(ArrayList<Square> s)
	{
		return (newShape.getMostLeftSquare(s).x >= 30)
		&& (newShape.getMostRightSquare(s).x <= 270);
	}
	
	private int determineRow(int y) // rows 0-19 (20 in all)
	{
		return (int)(y / 30);
	}
	
	private void removeRows()
	{
		for(int r : rowsToRemove)
		{
			removeRow(r);
			moveSquareDown(r);
		}		
		
		rowsToRemove.clear();
	}
	
	private void removeRow(int rowNum)
	{
		squares.get(rowNum).clear();
	}
	
	private void moveSquareDown(int rowRemoved)
	{
		for(int i = rowRemoved + 1; i < squares.size(); i++)
		{
			for(int s = 0; s < squares.get(i).size(); s++)
			{
				System.out.println("row" + i);
				squares.get(i).get(s).y += 30;
			}
		}
		
		System.out.println("help");
	}
	
	private void moveSquaresDown(int rowNumRemoved)
	{
		for(int i = rowNumRemoved + 1; i < squares.size(); i++)
		{
			for(int c = rowNumRemoved; c >= 0; c++)
			{
				ArrayList<Square> temp = squares.get(i);
				
				for(int z = 0; z < squares.get(i).size(); z++)
				{
					if(temp.get(z).y > (GRID_LENGTH - 30))
					{
						for(int h = 0; h < squares.get(c).size(); h++)
						{
							if(!((temp.get(z).y + 30 == squares.get(c).get(h).y) && (temp.get(z).x == squares.get(c).get(h).x)))
							{
								temp.get(z).y += 30;
							}
						}
					}
				}
			}
		}
	}
}

