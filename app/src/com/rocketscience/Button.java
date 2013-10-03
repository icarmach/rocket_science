package com.rocketscience;

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
	
	//Option 1: Don't set the toggled value, starts true
	public Button(String identifier,String section, String buttonText)
	{
		this.identifier = identifier;
		this.section = section;
		this.buttonText = buttonText;
		toggled = true;
	}
	
	//Option 2 : We define the toggled status
	public Button(String identifier, String section, String buttonText, boolean toggled)
	{
		this.identifier = identifier;
		this.section = section;
		this.buttonText = buttonText;
		this.toggled = toggled;
	}
	//Changes the toggled status of the button to the opposite one
	public void toggleButton()
	{
		toggled = !toggled;
	}
}
