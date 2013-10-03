package com.rocketscience;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.BaseGameActivity;

public class SceneManager {

	private SceneType currentScene;
	private BaseGameActivity activity;
	private Engine engine;
	private Camera camera;
	BitmapTextureAtlas splashTextureAtlas;
	ITextureRegion splashTextureRegion;
	Scene splashScene;
	Scene titleScene;
	Scene mainGameScene;

	public enum SceneType
	{
		SPLASH,
		TITLE,
		MAINGAME
	}

	public SceneManager(BaseGameActivity activity, Engine engine, Camera camera) {
		this.activity = activity;
		this.engine = engine;
		this.camera = camera;
	}

	//Method loads all of the splash scene resources
	public void loadSplashSceneResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
		splashTextureAtlas.load();
	}


	//Method loads all of the resources for the game scenes
	public void loadGameSceneResources() {

	}

	//Method creates the Splash Scene
	public Scene createSplashScene() {
		//Create the Splash Scene and set background colour to red and add the splash logo.
		splashScene = new Scene();
		splashScene.setBackground(new Background(0, 0, 0));
		Sprite splash = new Sprite(0, 0, splashTextureRegion, activity.getVertexBufferObjectManager())
		{
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera)
			{
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		splash.setScale(1.5f);
		splash.setPosition((camera.getWidth() - splash.getWidth()) * 0.5f, (camera.getHeight() - splash.getHeight()) * 0.5f);
		splashScene.attachChild(splash);

		return splashScene;
	}



	//Method creates all of the Game Scenes
	public void createGameScenes() {
		//Create the Title Scene
		titleScene = new Scene();
		createTitleScene();


		//Create the Main Game Scene
		mainGameScene = new Scene();
		createMainGameScene();
	}

	//Method create the Title Scene
	public void createTitleScene() {
		titleScene.setBackground(new Background(0, 0, 0));

		BitmapTextureAtlas gameBannerTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 475, 334, TextureOptions.DEFAULT);
		ITextureRegion gameBannerTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBannerTextureAtlas, activity, "banner.png", 0, 0);
		gameBannerTextureAtlas.load();

		Sprite gameBanner = new Sprite(0, 0, gameBannerTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//Do nothing
				return true;
			}
		};

		BitmapTextureAtlas playButtonTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		ITextureRegion playButtonTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playButtonTextureAtlas, activity, "menu_play.png", 0, 0);
		playButtonTextureAtlas.load();

		Sprite playButton = new Sprite(0, 0, playButtonTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				setCurrentScene(SceneType.MAINGAME);
				return true;
			}
		};

		BitmapTextureAtlas quitButtonTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		ITextureRegion quitButtonTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(quitButtonTextureAtlas, activity, "menu_quit.png", 0, 0);
		quitButtonTextureAtlas.load();

		Sprite quitButton = new Sprite(0, 0, quitButtonTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//Quit app
				return true;
			}
		};

		gameBanner.setPosition((camera.getWidth() - gameBanner.getWidth()) * 0.5f, 30);
		playButton.setPosition((camera.getWidth() - playButton.getWidth()) * 0.5f, (camera.getHeight() - playButton.getHeight()) * 0.5f + 100);
		quitButton.setPosition((camera.getWidth() - quitButton.getWidth()) * 0.5f, (camera.getHeight() - quitButton.getHeight()) * 0.5f + 200);

		titleScene.registerTouchArea(gameBanner);
		titleScene.setTouchAreaBindingOnActionDownEnabled(true);
		titleScene.attachChild(gameBanner);

		titleScene.registerTouchArea(playButton);
		titleScene.setTouchAreaBindingOnActionDownEnabled(true);
		titleScene.attachChild(playButton);

		titleScene.registerTouchArea(quitButton);
		titleScene.setTouchAreaBindingOnActionDownEnabled(true);
		titleScene.attachChild(quitButton);
	}

	//Method create the Main Game Scene
	public void createMainGameScene() {
		mainGameScene.setBackground(new Background(0, 0, 0));

		//Armory Button
		BitmapTextureAtlas armoryTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 240, 120, TextureOptions.DEFAULT);
		ITextureRegion armoryTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(armoryTextureAtlas, activity, "armory.png", 0, 0);
		armoryTextureAtlas.load();

		Sprite armory = new Sprite(0, 0, armoryTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//Do something
				return true;
			}
		};

		//Communication Button
		BitmapTextureAtlas communicationTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 240, 120, TextureOptions.DEFAULT);
		ITextureRegion communicationTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(communicationTextureAtlas, activity, "communication.png", 0, 0);
		communicationTextureAtlas.load();

		Sprite communication = new Sprite(0, 0, communicationTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//Do something
				return true;
			}
		};

		//Kitchen Button
		BitmapTextureAtlas kitchenTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 240, 120, TextureOptions.DEFAULT);
		ITextureRegion kitchenTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(kitchenTextureAtlas, activity, "kitchen.png", 0, 0);
		kitchenTextureAtlas.load();

		Sprite kitchen = new Sprite(0, 0, kitchenTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//Do something
				return true;
			}
		};

		//Navigation Button
		BitmapTextureAtlas navigationTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 240, 120, TextureOptions.DEFAULT);
		ITextureRegion navigationTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(navigationTextureAtlas, activity, "navigation.png", 0, 0);
		navigationTextureAtlas.load();

		Sprite navigation = new Sprite(0, 0, navigationTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//Do something
				return true;
			}
		};
		
		BitmapTextureAtlas buttonsPanelTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 480, 480, TextureOptions.DEFAULT);
		ITextureRegion buttonsPanelTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonsPanelTextureAtlas, activity, "buttons_panel.JPG", 0, 0);
		buttonsPanelTextureAtlas.load();
		
		Sprite buttonsPanel = new Sprite(0, 0, buttonsPanelTexture, activity.getVertexBufferObjectManager()) {
			//@Override
			//public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			//	int h = 10/0;
			//	return true;
			//}
		};
		
		BitmapTextureAtlas buttonOnTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion buttonOnTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonOnTextureAtlas, activity, "button_on.png", 0, 0);
		buttonOnTextureAtlas.load();
		
		BitmapTextureAtlas buttonOffTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion buttonOffTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonOffTextureAtlas, activity, "button_off.png", 0, 0);
		buttonOffTextureAtlas.load();
		
		final Sprite buttonOff1 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				this.setVisible(!this.isVisible());
				
				return true;
			}
		};
		
		Sprite buttonOn1 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff1.setVisible(!buttonOff1.isVisible());
				}
				return true;
			}
		};
		
		
		
		buttonOff1.setVisible(false);
		
		armory.setPosition(0, 0);
		communication.setPosition(240, 0);
		kitchen.setPosition(0, 120);
		navigation.setPosition(240, 120);
		
		buttonsPanel.setPosition(0,380);
		buttonOn1.setPosition(72, 40 + 380);
		buttonOff1.setPosition(72, 40 + 380);

		mainGameScene.registerTouchArea(armory);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(armory);
		
		mainGameScene.registerTouchArea(communication);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(communication);
		
		mainGameScene.registerTouchArea(kitchen);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(kitchen);
		
		mainGameScene.registerTouchArea(navigation);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(navigation);
		
		mainGameScene.registerTouchArea(buttonsPanel);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonsPanel);
		
		mainGameScene.registerTouchArea(buttonOn1);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn1);
		
		mainGameScene.registerTouchArea(buttonOff1);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff1);
		
		
	}

	//Method allows you to get the currently active scene
	public SceneType getCurrentScene() {
		return currentScene;
	}

	//Method allows you to set the currently active scene
	public void setCurrentScene(SceneType scene) {
		currentScene = scene;
		switch (scene)
		{
		case SPLASH:
			break;
		case TITLE:
			engine.setScene(titleScene);
			break;
		case MAINGAME:
			engine.setScene(mainGameScene);
			break;
		}
	}


}

