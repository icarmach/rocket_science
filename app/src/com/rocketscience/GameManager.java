package com.rocketscience;

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
		buttons.add(new Button("a1", 1, "Pulse Cannon"));
		buttons.add(new Button("a2", 1, "Energy Shields"));
		buttons.add(new Button("a3", 1, "Plasma Missiles"));
		buttons.add(new Button("a4", 1, "Sword Shooter"));
		//2 - Communications buttons
		buttons.add(new Button("c1", 2, "Newspaper Dispenser"));
		buttons.add(new Button("c2", 2, "Journalist Simulator"));
		buttons.add(new Button("c3", 2, "Radio Blocker"));
		buttons.add(new Button("c4", 2, "Message Printer"));
		//3 - Kitchen
		buttons.add(new Button("k1", 3, "Egg Dispenser"));
		buttons.add(new Button("k2", 3, "Meat Masher"));
		buttons.add(new Button("k3", 3, "Vegetable Destroyer"));
		buttons.add(new Button("k4", 3, "Brownie Mixer"));
		//4 - Navigation
		buttons.add(new Button("n1", 3, "Autopilot"));
		buttons.add(new Button("n2", 3, "Autoparking"));
		buttons.add(new Button("n3", 3, "Lightspeed Drive"));
		buttons.add(new Button("n4", 3, "Flux Capacitor"));
		//Define orders
		//Just iterate through buttons, creating one order per button
		for(Button e: buttons){
    			orders.add(new Order(e, 100));
    		}
		//Reset counters
		ordersRound = 0;
		currentOrderNumber = 0;
		
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
	public boolean roundOver()
	{
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
			}
			return false;
		}			
	}
	
	//Searchs the button list for the button with the identifier given and changes its toggled status
	public void pressButton(String buttonIdentifier)
	{
		for(Button e: buttons){
    		if(e.identifier.equals(buttonIdentifier))
    			e.toggleButton();
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
}
