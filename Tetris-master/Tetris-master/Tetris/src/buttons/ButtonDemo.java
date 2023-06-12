package buttons;

import java.util.ArrayList;

import processing.core.PApplet;

public class ButtonDemo extends PApplet
{
	private ArrayList<Button> buttons;
	private int clickedButton;
	
	public static void main(String[] args)
	{
		PApplet.main("buttons.ButtonDemo");
	}
	
	public void settings()
	{
		size(500, 300);
	}
	
	public void setup()
	{
		buttons = new ArrayList<Button>();
		final int OFFSET = 5;
		for(int b = 1; b <= 5; b++)
		{
			Button button = new Button(this, b * (Button.WIDTH + OFFSET), 50);
			buttons.add(button);
		}
		
		clickedButton = -1;
	}
	
	public void draw()
	{
		background(0);
		
		for(Button button : buttons)
			button.drawSelf();
		
		textSize(100);
		text("" + clickedButton, 25, 175);
	}
	
	public void mouseClicked()
	{
		for(int i = 0; i < buttons.size(); i++)
			if(buttons.get(i).isInside(mouseX, mouseY))
				clickedButton = i;
	}
}
