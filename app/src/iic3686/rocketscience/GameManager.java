package iic3686.rocketscience;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {
	//Variables
	//List of buttons
	public ArrayList<Button> buttons;
	//List of orders
	public ArrayList<Order> orders;
	//Order currently in progress
	public Order currentOrder;
	//Number of orders per round
	int ordersRound;
	int currentOrderNumber;
	//Used to obtain randoms
	int index;
	private Random randomGenerator;
	public boolean changedRecently;
	
	public GameManager()
	{
		//Define lists
		buttons = new ArrayList<Button>();
		orders = new ArrayList<Order>();
		//Define buttons
		//Sections:
		// 1 - Armory
		// 2 - Communications
		// 3 - Kitchen
		// 4 - Navigation
		//Button newButton = new Button("b1", 1, "Button 1");
		//1 - Armory buttons
		buttons.add(new Button("a1", "Armory", "Pulse Cannon"));
		buttons.add(new Button("a2", "Armory", "Energy Shields"));
		buttons.add(new Button("a3", "Armory", "Plasma Missiles"));
		buttons.add(new Button("a4", "Armory", "Sword Shooter"));
		//2 - Communications buttons
		buttons.add(new Button("c1", "Comm." , "News Dispenser"));
		buttons.add(new Button("c2", "Comm." , "Journalizer"));
		buttons.add(new Button("c3", "Comm." , "Radio Blocker"));
		buttons.add(new Button("c4", "Comm" , "Messenger"));
		//3 - Kitchen
		buttons.add(new Button("k1", "Kitchen", "Egg Scrambler"));
		buttons.add(new Button("k2", "Kitchen", "Meat Masher"));
		buttons.add(new Button("k3", "Kitchen", "Veggie Destroyer"));
		buttons.add(new Button("k4", "Kitchen", "Brownie Mixer"));
		//4 - Navigation
		buttons.add(new Button("n1", "Nav.", "Autopilot"));
		buttons.add(new Button("n2", "Nav.", "Autoparking"));
		buttons.add(new Button("n3", "Nav.", "Lightspeed Drive"));
		buttons.add(new Button("n4", "Nav.", "Flux Capacitor"));
		//Define orders
		//Just iterate through buttons, creating one order per button
		for(Button e: buttons){
			//Debug
				//if(e.identifier.contains("a") || e.identifier.contains("c"))
    			orders.add(new Order(e, 100));
    		}
		//Random
		randomGenerator = new Random();
		//Reset counters
		ordersRound = 10;
		currentOrderNumber = 0;
		//Recently
		changedRecently = false;
		
	}
		
	//Gets a new random order
	public void getNewOrder()
	{
		index = randomGenerator.nextInt(orders.size());
		currentOrder = orders.get(index);
	}
	
	//Checks each time to see if the current number of orders passed is enough
	//If it is, returns true (round is over)
	//If it isn't, checks currentOrder to see if it is done.
	//If it is, add 1 to currentOrderNumber and get a new random order
	//It it isn't, do NOTHING
	public boolean isRoundOver()
	{
		if(currentOrder == null)
			getNewOrder();
		
		if(currentOrderNumber >= ordersRound)
			return true;
		else
		{
			if(this.currentOrder.isOrderDone())
			{
				currentOrderNumber++;
				//Refresh old order
				currentOrder.refreshOrder();
				getNewOrder();
				changedRecently = true;
			}
			return false;
		}			
	}
	
	//Searchs the button list for the button with the identifier given and changes its toggled status
	public void pressButton(String buttonIdentifier)
	{
		for(Button e: buttons){
    		if(e.identifier.equals(buttonIdentifier))
	    		{
	    			e.toggleButton();
	    		}
    			
    		}
	}
	
	//Searchs the button list for the button with the identifier given and changes its toggled status
		public void setButtonValue(String buttonIdentifier, int amount)
		{
			for(Button e: buttons){
	    		if(e.identifier.equals(buttonIdentifier))
		    		{
		    			e.toggleButton(amount);
		    		}
	    			
	    		}
		}
		
	//Get current order text
	public String getCurrentOrderText()
	{
		if(currentOrder != null)
			return currentOrder.orderText;
		else
			return "NO ORDER";
	}
	
	//Start new round, with more difficulty
	public void newRound()
	{
		//Each level add 5 more orders
		ordersRound += 5;
		currentOrderNumber = 0;
		getNewOrder(); 
	}
	
	//Start new round, with more difficulty
	public void resetGame()
	{
			//Each level add 5 more orders
		ordersRound = 10;
		currentOrderNumber = 0;
		getNewOrder(); 
	}
		
	//Get button text by identifier
	public String getButtonTextByIdentifier(String buttonIdentifier)
	{
		for(Button e: buttons){
    		if(e.identifier.equals(buttonIdentifier))
    			return e.buttonText;
    		}
		return "";
	}
	
	//Has been changed recently?
	public boolean hasBeenChangedRecently()
	{
		if(changedRecently)
		{
			changedRecently = false;
			return true;
		}
		else
		{
			return false;
		}
	}
}
