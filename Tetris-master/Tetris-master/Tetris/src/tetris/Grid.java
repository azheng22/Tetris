package tetris;

import java.util.ArrayList;
import java.util.TreeSet;

import processing.core.PApplet;

public class Grid extends PApplet
{
	private ArrayList<Square> squares;
	private int[] rows;
	private Shape newShape;
	private boolean colliding;
	private int timeOfNextChange;
	private TreeSet<Integer> rowsToRemove; //array list of rows to remove
	private int score;
	private boolean running;
	
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
		squares = new ArrayList<Square>();
		newShape = new Shape();
		colliding = false;
		timeOfNextChange = CHANGE_DELAY;
		rows = new int[20]; //20 rows in all (0-19)
		rowsToRemove = new TreeSet<Integer>();
		score = 0;
		running = true;
		
		for(int i = 0; i < rows.length; i++)
			rows[i] = 0;
	}
	
	public void draw()
	{
		if(running)
		{
		
			background(0);
			drawGrid();	
			
			textSize(20);
			fill(255);
			text("Score:\n" + score, 320, 30);
			
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
				
				System.out.println(colliding);
				
				for(Square s : newShape.squaresOfThisShape)
				{
					squares.add(s);
					//System.out.println("x:" + s.x + " y:" + s.y);
					
					s.colorsRGB.add(newShape.colorsRGB.get(0));
					s.colorsRGB.add(newShape.colorsRGB.get(1));
					s.colorsRGB.add(newShape.colorsRGB.get(2));
					

					if(determineRow(s.y) == 0)
						running = false;
					
					rows[determineRow(s.y)] = rows[determineRow(s.y)] + 1;
					//System.out.println(determineRow(s.y));
					
					//System.out.println(rows[determineRow(s.y)]);
				
					if(rows[determineRow(s.y)] == 10)
						rowsToRemove.add(determineRow(s.y));
					
				}
				
				newShape = new Shape();
				System.out.println("\n-------------");
				colliding = false;
			}
			
			//System.out.println("HELP" + !rowsToRemove.isEmpty());
			
			if(!rowsToRemove.isEmpty())
			{
				for(int i : rowsToRemove)
					System.out.println("row" + i);
	
				removeRows();
				rowsToRemove.clear();
			}
		}
		else
		{
			endScreen();
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
			for(Square s : squares)
			{
				if((sq.y + 30 == s.y) && (sq.x == s.x))
				{
					colliding = true;
					temp = true;
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
		for(Square s : squares)
		{
			fill(s.colorsRGB.get(0), s.colorsRGB.get(1), s.colorsRGB.get(2));
			rect(s.x, s.y, 30, 30);
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
			System.out.println(r);
			removeRow(r);
			
			rows[r] = 0;
			
			moveSquaresDown(r);
		}
	}
	
	private void removeRow(int rowNum)
	{
		for(int i = 0; i < squares.size(); i++)
		{
			int rowOfSquare = determineRow(squares.get(i).y);
			
			if(rowOfSquare == rowNum)
			{
				System.out.println(squares.get(i).y + "<--- sq y");
				squares.remove(squares.get(i));
				score += 5;
				i--;
			}
		}
	}
	
	private void moveSquaresDown(int rowRemoved)
	{
		for(int i = 0; i < squares.size(); i++)
		{
			if(determineRow(squares.get(i).y) < rowRemoved)
			{
				squares.get(i).y += 30;
				rows[determineRow(squares.get(i).y)] = rows[determineRow(squares.get(i).y)] + 1;
			}
			
			
		}
	}
	
	private void endScreen()
	{
		background(127, 3, 252);
		
		textSize(50);
		fill(255);
		text("End game", 80, 200);
		text("Score: " + score, 80, 300);
	}
}
