package iic3686.rocketscience;

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
	
	public Order(Button buttonLinked, int time)
	{
		this.buttonLinked = buttonLinked;
		this.time = time;
		//When we create it, we check the button's current status
		this.buttonInitial = this.buttonLinked.toggled;
		//Make the text automatic
		if(this.buttonInitial)
			this.orderText = "Turn off the "+this.buttonLinked.buttonText+" ("+this.buttonLinked.section+").";
		else
			this.orderText = "Turn on the "+this.buttonLinked.buttonText+" ("+this.buttonLinked.section+")";
	}
	
	public Order(String orderText, Button buttonLinked, int time)
	{
		this.buttonLinked = buttonLinked;
		this.time = time;
		//When we create it, we check the button's current status
		this.buttonInitial = this.buttonLinked.toggled;
		//Define text
		this.orderText = orderText;
	}
	
	//This method allows us to recycle the order
	public void refreshOrder()
	{
		this.buttonInitial = this.buttonLinked.toggled;
		//Redefine text
		if(this.buttonInitial)
			this.orderText = "Turn off the "+this.buttonLinked.buttonText+" ("+this.buttonLinked.section+")";
		else
			this.orderText = "Turn on the "+this.buttonLinked.buttonText+" ("+this.buttonLinked.section+")";
	}
	
	//Checks if the linked button has changed the status since the order was started
	public boolean isOrderDone()
	{
		if(buttonInitial == this.buttonLinked.toggled)
			return false;
		else
			return true;
	}
}
