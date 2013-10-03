package com.rocketscience;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
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
		splashScene.setBackground(new Background(1, 0, 0));
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
		//Create the Title Scene and set background colour to green
		titleScene = new Scene();
		titleScene.setBackground(new Background(0, 1, 0));

		//Create the Main Game Scene and set background colour to blue
		mainGameScene = new Scene();
		mainGameScene.setBackground(new Background(0, 0, 1));
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

