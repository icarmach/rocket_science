package iic3686.rocketscience;

public class Button {
	//Variables
	//Identifier
	String identifier;
	//What area of the ship the button belongs to
	String section;
	//Text that will be displayed on top of the button
	String buttonText;
	//Is the button pressed or not. True = pressed, false = not.
	boolean toggled;
	//Value for sliders and that other crap 
	int value;
	//What type of button this is
	int type;
	
	//Option 1: Don't set the toggled value, starts true
	public Button(String identifier,String section, String buttonText)
	{
		this.identifier = identifier;
		this.section = section;
		this.buttonText = buttonText;
		toggled = true;
		type = 1;
	}
	
	//Option 2 : We define the toggled status
	public Button(String identifier, String section, String buttonText, boolean toggled)
	{
		this.identifier = identifier;
		this.section = section;
		this.buttonText = buttonText;
		this.toggled = toggled;
		type = 1;
	}
	//Option 1: Don't set the toggled value, starts true
	public Button(String identifier,String section, String buttonText, int buttonType)
	{
		this.identifier = identifier;
		this.section = section;
		this.buttonText = buttonText;
		if(buttonType == 1) //Normal toggle
		{
			type = 1;
			toggled = true;
		}
		else if(buttonType == 2) //Slider
		{
			type = 2;
			value = 1;
		}
		else //Spinner
		{
			type = 3;
			value = 0;
		}
	
	}
	//Changes the toggled status of the button to the opposite one
	public void toggleButton()
	{
		toggled = !toggled;
	}
	
	//Changes the value of the button to the given one
	public void toggleButton(int amount)
	{
		if(type == 2)
		{
			if(value+amount > 9)
				value = 9;
			else
				value += amount;
		}
		else if(type == 3)
		{
			if(value+amount > 10)
				value = 10;
			else
				value += amount;
		}
		else//Impossible, just in case
		{
			toggled = !toggled;
		}
		
	}
}
