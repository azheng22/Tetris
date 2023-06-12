package tetris;

import java.util.ArrayList;

public class Shape 
{
	public ArrayList<Square> squaresOfThisShape;
	public ArrayList<Integer> colorsRGB; 
	public int centerX;
	public int centerY;
	private int[] clockwise;
	
	public Shape() //square1 = 'middle'
	{
		squaresOfThisShape = new ArrayList<Square>();
		int random = (int)(Math.random() * 7 + 1); 

		centerX = 0;
		centerY = 0;
		
		if(random == 1)
			createShape1();
		else if(random == 2)
			createShape2();
		else if(random == 3)
			createShape3();
		else if(random == 4)
			createShape4();
		else if(random == 5)
			createShape5();
		else if(random == 6)
			createShape6();
		else
			createShape7();
		
		colorsRGB = new ArrayList<Integer>();
		randomColor();
		
		int[] clockwise = {0, 1, -1, 0};
		this.clockwise = clockwise;
	}
	
	private void createShape1()
	{
		centerX = 30;
		centerY = 0;
		
		Square square1 = new Square(30, 0);
		Square square2 = new Square(0, 0);
		Square square3 = new Square(60, 0);
		Square square4 = new Square(90, 0);
		
		squaresOfThisShape.add(square1);
		squaresOfThisShape.add(square2);
		squaresOfThisShape.add(square3);
		squaresOfThisShape.add(square4);
	}
	
	private void createShape2()
	{
		centerX = 30;
		centerY = 30;
		
		Square square1 = new Square(30, 30);
		Square square2 = new Square(0, 0);
		Square square3 = new Square(0, 30);
		Square square4 = new Square(60, 30);
		
		squaresOfThisShape.add(square1);
		squaresOfThisShape.add(square2);
		squaresOfThisShape.add(square3);
		squaresOfThisShape.add(square4);
	}
	
	private void createShape3()
	{
		centerX = 30;
		centerY = 30;
		
		Square square1 = new Square(30, 30);
		Square square2 = new Square(0, 30);
		Square square3 = new Square(60, 30);
		Square square4 = new Square(60, 0);
		
		squaresOfThisShape.add(square1);
		squaresOfThisShape.add(square2);
		squaresOfThisShape.add(square3);
		squaresOfThisShape.add(square4);
	}
	
	private void createShape4()
	{
		centerX = 30;
		centerY = 30;
		
		Square square1 = new Square(30, 30);
		Square square2 = new Square(0, 0);
		Square square3 = new Square(30, 0);
		Square square4 = new Square(0, 30);
		
		squaresOfThisShape.add(square1);
		squaresOfThisShape.add(square2);
		squaresOfThisShape.add(square3);
		squaresOfThisShape.add(square4);

	}
	
	private void createShape5()
	{
		centerX = 30;
		centerY = 30;
		
		Square square1 = new Square(30, 30);
		Square square2 = new Square(60, 0);
		Square square3 = new Square(30, 0);
		Square square4 = new Square(0, 30);
		
		squaresOfThisShape.add(square1);
		squaresOfThisShape.add(square2);
		squaresOfThisShape.add(square3);
		squaresOfThisShape.add(square4);
	}
	
	private void createShape6()
	{
		centerX = 30;
		centerY = 30;
		
		Square square1 = new Square(30, 30);
		Square square2 = new Square(0, 30);
		Square square3 = new Square(30, 0);
		Square square4 = new Square(60, 30);
		
		squaresOfThisShape.add(square1);
		squaresOfThisShape.add(square2);
		squaresOfThisShape.add(square3);
		squaresOfThisShape.add(square4);
	}
	
	private void createShape7()
	{
		centerX = 30;
		centerY = 30;
		
		Square square1 = new Square(30, 30);
		Square square2 = new Square(30, 0);
		Square square3 = new Square(0, 0);
		Square square4 = new Square(60, 30);
		
		squaresOfThisShape.add(square1);
		squaresOfThisShape.add(square2);
		squaresOfThisShape.add(square3);
		squaresOfThisShape.add(square4);
	}
	
	public void moveDown()
	{
		for(Square s : squaresOfThisShape)
			s.y += 30;
		
		centerY += 30;
	}
	
	private void randomColor()
	{
		int r = (int)(Math.random() * 255);
		int g = (int)(Math.random() * 255);
		int b = (int)(Math.random() * 255);
		
		colorsRGB.add(r);
		colorsRGB.add(g);
		colorsRGB.add(b);
	}
	
	public Square getMostRightSquare(ArrayList<Square> shape)
	{
		Square maxSquare = shape.get(0);
		
		for(Square s: shape)
		{
			if(maxSquare.x < s.x)
				maxSquare = s;
		}
		
		return maxSquare;
	}
	
	public Square getMostLeftSquare(ArrayList<Square> shape)
	{
		Square minSquare = shape.get(0);
		
		for(Square s: shape)
		{
			if(minSquare.x > s.x)
				minSquare = s;
		}
		
		return minSquare;
	}
	
	public Square getMostBottomSquare(ArrayList<Square> shape)
	{
		Square botmSquare = shape.get(0);
		
		for(Square s: shape)
		{
			if(botmSquare.y < s.y)
				botmSquare = s;
		}
		
		return botmSquare;
	}
	
	public boolean areColliding(Square s) //delete?
	{
		boolean areColliding = false;
		Square bottom = squaresOfThisShape.get(0);
		
		System.out.println("s.x--" + s.x + "  bot.x--" + bottom.x);
		
		if(s.x - bottom.x <= 30 && bottom.y - s.y <= 30)
		{
			System.out.println("s.x--" + s.x + "  bot.x--" + bottom.x);
			areColliding = true;
		}
		
		return areColliding;
	}
	
	private int[] returnClockwiseCords(Square s) //(x, y)
	{	
		int tempX = s.x - centerX;
		int tempY = s.y - centerY;
		
		int x = (clockwise[0] * tempX) + (clockwise[1] * tempY);
		int y = (clockwise[2] * tempX) + (clockwise[3] * tempY);
		
		x += centerX;
		y += centerY;
		
		int[] coordinates = {x, y};
		
		return coordinates;
	}
	
	public ArrayList<Square> returnRotatedShape()
	{
		ArrayList<Square> rotated = new ArrayList<Square>();
		
		for(Square s : squaresOfThisShape)
		{
			int[] cords = returnClockwiseCords(s);
			Square newSquare = new Square(cords[0], cords[1]);
			
			rotated.add(newSquare);
		}
		
		return rotated;
	}
}
