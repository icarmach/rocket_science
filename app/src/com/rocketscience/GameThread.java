package com.rocketscience;

import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;

public class GameThread extends Thread{

	private Scene currentScene;
	private GameManager gm;
	//Game Logic Variables
	boolean gameIsRunning;
	int levelCounter;
	Rectangle orderRectangle;
	//Time counter
	int timeCounter;
	int errorCounter;
	
	public GameThread(Scene startScene, GameManager gm, Rectangle orderRectangle)
	{
		this.currentScene = startScene;
		this.gm = gm;
		this.gameIsRunning = true;
		this.levelCounter = 1;
		this.orderRectangle = orderRectangle;
		this.timeCounter = 0;
		this.errorCounter = 0;
	}
	
	public void run() {
		
		while(gameIsRunning)
		{
			timeCounter = 0;
			errorCounter = 0;
			while(!this.gm.isRoundOver())
			{
				//GAME LOOP EL LUP LUP PICO CON TODOS
				if(this.gm.currentOrder == null)
					this.gm.getNewOrder();
				//Create new round
				/*if(this.gm.isRoundOver())
				{
					//Start new round
					this.gm.newRound();
				}*/
				//Change the text of the buttons if needed
				//currentScene
				//Change the text of the instruction and check the status
				//Background progressive change
				if(timeCounter >= 3000 && timeCounter < 4000)
				{
					this.orderRectangle.setColor(0.7f, 0, 0);
				}
				else if(timeCounter >= 4000 && timeCounter < 5000)
				{
					this.orderRectangle.setColor(0.8f, 0, 0);
				}
					
				
				
				if(timeCounter >= 5000)
				{
					//5 seconds have passed, change mission, increase errorCounter
					this.gm.getNewOrder();
					errorCounter++;
					this.orderRectangle.setColor(1, 1, 1);
					timeCounter = 0;
				}
				Text orderTextLabel= (Text)currentScene.getChildByTag(1);
				orderTextLabel.setText(this.gm.getCurrentOrderText());
				//IEntity boton1 = currentScene.getChildByTag(10);
				//boton1.setVisible(!boton1.isVisible());
				//IEntity boton2 = currentScene.getChildByTag(11);
				//boton2.setVisible(!boton2.isVisible());
				try {
					//this.orderRectangle.setColor(1, 1, 1);
					this.sleep(1);
					timeCounter++;
					//this.orderRectangle.setColor(1, 0, 0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//END OF ROUND
			Text orderTextLabel= (Text)currentScene.getChildByTag(1);
			orderTextLabel.setText("Level Complete!");
			try {
				this.sleep(4000);
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			levelCounter++;
			orderTextLabel.setText("Get ready for Level "+levelCounter+"!");
			try {
				this.sleep(2000);
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			this.gm.newRound();
			
		}
    }
	
}
