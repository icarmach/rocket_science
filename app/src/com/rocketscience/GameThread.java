package com.rocketscience;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;

public class GameThread extends Thread{

	private Scene currentScene;
	private GameManager gm;
	
	public GameThread(Scene startScene, GameManager gm)
	{
		this.currentScene = startScene;
		this.gm = gm;
	}
	
	public void run() {
		while(true)
		{
			IEntity boton1 = currentScene.getChildByTag(10);
			boton1.setVisible(!boton1.isVisible());
			IEntity boton2 = currentScene.getChildByTag(11);
			boton2.setVisible(!boton2.isVisible());
			try {
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
}
