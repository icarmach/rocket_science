package iic3686.rocketscience;

import java.util.Random;

public class Order {
	//Order variables
	//The text that the order displays
	String orderText;
	//The button that must be pressed to complete the order
	Button buttonLinked;
	//How long the order lasts
	int time;
	//Value of the toggled variable of the button when the order was created (or refreshed)
	boolean buttonInitial;
	//Value of the button
	int buttonValue;
	//value of the order
	int orderValue;
	//Random
	private Random randomGenerator;
	
	public Order(Button buttonLinked, int time)
	{
		this.buttonLinked = buttonLinked;
		this.time = time;
		//Check button type
		if(this.buttonLinked.type == 1) //Normal toggle
		{
			//When we create it, we check the button's current status
			this.buttonInitial = this.buttonLinked.toggled;
			//Make the text automatic
			if(this.buttonInitial)
				this.orderText = "Turn off the "+this.buttonLinked.buttonText+" ("+this.buttonLinked.section+").";
			else
				this.orderText = "Turn on the "+this.buttonLinked.buttonText+" ("+this.buttonLinked.section+")";
		}
		else if(this.buttonLinked.type == 2)//Slider
		{
			//When we create it, we check the button's current status
			this.buttonValue = this.buttonLinked.value;
			randomGenerator = new Random();
			while(orderValue == buttonValue)
				orderValue = randomGenerator.nextInt(8)+1;
			//Make the text automatic
			this.orderText = "Set the "+this.buttonLinked.buttonText+" to  "+orderValue+"("+this.buttonLinked.section+")";
		}
		else //Spinner
		{
			//When we create it, we check the button's current status
			this.buttonValue = this.buttonLinked.value;
			randomGenerator = new Random();
			while(orderValue == buttonValue)
				orderValue = randomGenerator.nextInt(10);
			//Make the text automatic
			this.orderText = "Set the "+this.buttonLinked.buttonText+" to  "+orderValue+"("+this.buttonLinked.section+")";
		}

	}
	
	public Order(String orderText, Button buttonLinked, int time)
	{
		this.buttonLinked = buttonLinked;
		this.time = time;
		//Check button type
		if(this.buttonLinked.type == 1) //Normal toggle
		{
			//When we create it, we check the button's current status
			this.buttonInitial = this.buttonLinked.toggled;
		}
		else if(this.buttonLinked.type == 2)//Slider
		{
			//When we create it, we check the button's current status
			this.buttonValue = this.buttonLinked.value;
		}
		else //Spinner
		{
			//When we create it, we check the button's current status
			this.buttonValue = this.buttonLinked.value;
		}
		//Define text
		this.orderText = orderText;
	}
	
	//This method allows us to recycle the order
	public void refreshOrder()
	{
		if(this.buttonLinked.type == 1) //Normal toggle
		{
			this.buttonInitial = this.buttonLinked.toggled;
			//Redefine text
			if(this.buttonInitial)
				this.orderText = "Turn off the "+this.buttonLinked.buttonText+" ("+this.buttonLinked.section+")";
			else
				this.orderText = "Turn on the "+this.buttonLinked.buttonText+" ("+this.buttonLinked.section+")";
		}
		else if(this.buttonLinked.type == 2)
		{
			this.buttonValue = this.buttonLinked.value;
			randomGenerator = new Random();
			while(orderValue == buttonValue)
				orderValue = randomGenerator.nextInt(8)+1;
			//Make the text automatic
			this.orderText = "Set the "+this.buttonLinked.buttonText+" to  "+orderValue+"("+this.buttonLinked.section+")";
		}
		else
		{
			this.buttonValue = this.buttonLinked.value;
			randomGenerator = new Random();
			while(orderValue == buttonValue)
				orderValue = randomGenerator.nextInt(10);
			//Make the text automatic
			this.orderText = "Set the "+this.buttonLinked.buttonText+" to  "+orderValue+"("+this.buttonLinked.section+")";
		}
		
	}
	
	//Checks if the linked button has changed the status since the order was started
	public boolean isOrderDone()
	{
		if(this.buttonLinked.type == 1) //Normal toggle
		{
			if(buttonInitial == this.buttonLinked.toggled)
				return false;
			else
				return true;
		}
		else //Spinner and Slider
		{
			if(this.buttonLinked.value == orderValue)
				return true;
			else
				return false;
		}
		
		
	}
}
