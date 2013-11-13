package iic3686.rocketscience;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.HorizontalAlign;


public class SceneManager {

	private SceneType currentScene;
	private BaseGameActivity activity;
	private Engine engine;
	private Camera camera;
	
	private TagHandler th;
	
	BitmapTextureAtlas splashTextureAtlas;
	ITextureRegion splashTextureRegion;
	
	BitmapTextureAtlas loseTextureAtlas;
	ITextureRegion loseTextureRegion;
	
	BitmapTextureAtlas victoryTextureAtlas;
	ITextureRegion victoryTextureRegion;
	
	Scene splashScene;
	Scene titleScene;
	Scene mainGameScene;
	//Text
	Font mFont;
	VertexBufferObjectManager vbom;
	//Game Logic
	GameManager gm;
	GameThread gt;
	int section;
	//Text
	Text button1Text;
	Text button2Text;
	Text button3Text;
	Text button4Text;
	Text orderText;
	//Default value for the texts, so that we don't have size problems
	String defaultText = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	//Buttons
	/*Button button1;
	Button button2;
	Button button3;
	Button button4;*/
	
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
		section = 1;
		th = TagHandler.getInstance();
	}

	//Method loads all of the splash scene resources
	public void loadSplashSceneResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
		splashTextureAtlas.load();
	}


	//Method loads all of the resources for the game scenes
	public void loadGameSceneResources(Font mFont, VertexBufferObjectManager vbom) {
		this.mFont = mFont;
		this.vbom = vbom;
		//Logic
		gm = new GameManager();
		
		//getEngine().getTextureManager().loadTexture(fontTextureAtlas);
		//this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		//this.mFont.load();
	}

	//Method creates the Splash Scene
	public Scene createSplashScene() {
		//Create the Splash Scene and set background colour to red and add the splash logo.
		splashScene = new Scene();
		splashScene.setBackground(new Background(0, 0, 0));
		Sprite splash = new Sprite(0, 0, splashTextureRegion, activity.getVertexBufferObjectManager())
		{
			
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
		//Loop?
		
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
				activity.finish();
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
		
		//Victory and defeat textures
		
		//Load all 4 on & off buttons
		BitmapTextureAtlas[] buttonOnTextureAtlases = new BitmapTextureAtlas[4];
		ITextureRegion[] buttonOnTextures = new ITextureRegion[4];
		
		BitmapTextureAtlas[] buttonOffTextureAtlases = new BitmapTextureAtlas[4];
		ITextureRegion[] buttonOffTextures = new ITextureRegion[4];
		
		for(int i = 0; i < 4; i ++)
		{
			buttonOnTextureAtlases[i] = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
			buttonOnTextures[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonOnTextureAtlases[i], activity, "button_on_" + (i + 1) +".png", 0, 0);
			buttonOnTextureAtlases[i].load();
			
			buttonOffTextureAtlases[i] = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
			buttonOffTextures[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonOffTextureAtlases[i], activity, "button_off_" + (i + 1) +".png", 0, 0);
			buttonOffTextureAtlases[i].load();
		}
		
		//HUGE-ASS LIST OF BUTTONS
		final Sprite[] buttonsOn = new Sprite[16];
		final Sprite[] buttonsOff = new Sprite[16];
		
		for(int i = 0;  i < 16; i++)
		{			
			buttonsOff[i] = new Sprite(0, 0, buttonOffTextures[i/4], activity.getVertexBufferObjectManager());
			buttonsOff[i].setVisible(false);
			
			buttonsOn[i] =  this.onButtonFactory(buttonOnTextures[i/4],i,buttonsOff);
		}
		//Buttons end
		
		//KNOB TEST
		BitmapTextureAtlas knobMap = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion knobTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(knobMap, activity, "knob.png", 0, 0);
		
		final Sprite knob = new Sprite(0, 0, knobTexture, activity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			
				pSceneTouchEvent.getX();
				
				return true;
			}
		};
		
		//Top options
		//Armory Button
		BitmapTextureAtlas armoryTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 240, 120, TextureOptions.DEFAULT);
		ITextureRegion armoryTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(armoryTextureAtlas, activity, "armory.png", 0, 0);
		armoryTextureAtlas.load();

		Sprite armory = new Sprite(0, 0, armoryTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//Do something
				
				button1Text.setText(gm.getButtonTextByIdentifier("a1"));
				button2Text.setText(gm.getButtonTextByIdentifier("a2"));
				button3Text.setText(gm.getButtonTextByIdentifier("a3"));
				button4Text.setText(gm.getButtonTextByIdentifier("a4"));
				
				
				
				/////
				
				for(int i = 0; i < 16; i++)
				{
					int posX = 2000;
					int posY = 2000;
					
					if(i < 4)//So only the first 4 buttons show
					{
						if(i%2 == 0) posX = 72;
						else posX = 300;
						
						if(i <= 1) posY = 420;
						else posY = 590;
					}
					
					buttonsOn[i].setPosition(posX,posY);
					buttonsOff[i].setPosition(posX,posY);
				}
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
				//Toggle section
				button1Text.setText(gm.getButtonTextByIdentifier("c1"));
				button2Text.setText(gm.getButtonTextByIdentifier("c2"));
				button3Text.setText(gm.getButtonTextByIdentifier("c3"));
				button4Text.setText(gm.getButtonTextByIdentifier("c4"));
				
				for(int i = 0; i < 16; i++)
				{
					int posX = 2000;
					int posY = 2000;
					
					if(i >= 4 && i < 8)//So only the first 4 buttons show
					{
						if(i%2 == 0) posX = 72;
						else posX = 300;
						
						if(i <= 5) posY = 420;
						else posY = 590;
					}
					
					buttonsOn[i].setPosition(posX,posY);
					buttonsOff[i].setPosition(posX,posY);
					
				}
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
				button1Text.setText(gm.getButtonTextByIdentifier("k1"));
				button2Text.setText(gm.getButtonTextByIdentifier("k2"));
				button3Text.setText(gm.getButtonTextByIdentifier("k3"));
				button4Text.setText(gm.getButtonTextByIdentifier("k4"));
				
				for(int i = 0; i < 16; i++)
				{
					int posX = 2000;
					int posY = 2000;
					
					if(i >= 8 && i < 12)//So only the first 4 buttons show
					{
						if(i%2 == 0) posX = 72;
						else posX = 300;
						
						if(i <= 9) posY = 420;
						else posY = 590;
					}
					
					buttonsOn[i].setPosition(posX,posY);
					buttonsOff[i].setPosition(posX,posY);
					
				}
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
				button1Text.setText(gm.getButtonTextByIdentifier("n1"));
				button2Text.setText(gm.getButtonTextByIdentifier("n2"));
				button3Text.setText(gm.getButtonTextByIdentifier("n3"));
				button4Text.setText(gm.getButtonTextByIdentifier("n4"));
				
				for(int i = 0; i < 16; i++)
				{
					int posX = 2000;
					int posY = 2000;
					
					if(i >= 12)//So only the first 4 buttons show
					{
						if(i%2 == 0) posX = 72;
						else posX = 300;
						
						if(i <= 13) posY = 420;
						else posY = 590;
					}
					
					buttonsOn[i].setPosition(posX,posY);
					buttonsOff[i].setPosition(posX,posY);
					
				}
				return true;
			}
		};
		
		//Load victory and defeat texture
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		loseTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 480, 480, TextureOptions.DEFAULT);
		loseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loseTextureAtlas, activity, "gameover.png", 0, 0);
		
				
		victoryTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 480, 480, TextureOptions.DEFAULT);
		victoryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(victoryTextureAtlas, activity, "levelcomplete.png", 0, 0);
		
		
		//
		BitmapTextureAtlas buttonsPanelTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 480, 480, TextureOptions.DEFAULT);
		ITextureRegion buttonsPanelTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonsPanelTextureAtlas, activity, "buttons_panel.JPG", 0, 0);
		buttonsPanelTextureAtlas.load();
		
		ITextureRegion loseTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loseTextureAtlas, activity, "gameover.png", 0, 0);
		loseTextureAtlas.load();
		
		ITextureRegion victoryTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(victoryTextureAtlas, activity, "levelcomplete.png", 0, 0);
		victoryTextureAtlas.load();
		
		Sprite buttonsPanel = new Sprite(0, 0, buttonsPanelTexture, activity.getVertexBufferObjectManager());
		
		Sprite loseSplash = new Sprite(0, 0, loseTexture , activity.getVertexBufferObjectManager());
		
		Sprite victorySplash = new Sprite(0, 0, victoryTexture , activity.getVertexBufferObjectManager());
		
		//SEXY TEXT
		//Before that, display text
		//final VertexBufferObjectManager vertexBufferObjectManager = new VertexBufferObjectManager();
		//Order text
		orderText = new Text(20,320, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		orderText.setText("Instruccion 1");
		orderText.setTag(1);
		//orderText.setTag(100);
	
		//Button text
		button1Text = new Text(45, 40+350, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		button2Text = new Text(280, 40+350, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		button3Text = new Text(45, 40+520, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		button4Text = new Text(280, 40+520, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		
		button1Text.setText(gm.getButtonTextByIdentifier("a1"));
		button2Text.setText(gm.getButtonTextByIdentifier("a2"));
		button3Text.setText(gm.getButtonTextByIdentifier("a3"));
		button4Text.setText(gm.getButtonTextByIdentifier("a4"));
		
		armory.setPosition(0, 0);
		communication.setPosition(240, 0);
		kitchen.setPosition(0, 120);
		navigation.setPosition(240, 120);
		
		buttonsPanel.setPosition(0,380);
		
		//Victory and defeat
		loseSplash.setPosition(2000,2000);
		victorySplash.setPosition(2000,2000);
		
		for(int i = 0; i < 16; i++)
		{
			int posX = 2000;
			int posY = 2000;
			
			if(i < 4)//So only the first 4 buttons show
			{
				if(i%2 == 0) posX = 72;
				else posX = 300;
				
				if(i <= 1) posY = 420;
				else posY = 590;
			}
			
			buttonsOn[i].setPosition(posX,posY);
			buttonsOff[i].setPosition(posX,posY);
		}
		
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
		
		//Buttons are attached here
		
		for(int i = 0; i<16; i++)
		{
			mainGameScene.registerTouchArea(buttonsOn[i]);
			mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
			mainGameScene.attachChild(buttonsOn[i]);
			
			mainGameScene.attachChild(buttonsOff[i]);
		}
		
		//Print the texts and the background
		//We create the rectangles
		final Rectangle orderRectangle = this.makeColoredRectangle(0,300,1, 1, 1, this.vbom, (int)this.camera.getWidth(), 80);
		final Rectangle timeRectangle = this.makeColoredRectangle(0,295,1, 0, 0, this.vbom, (int)this.camera.getWidth(), 5);
		
		final Rectangle button1Rectangle = this.makeColoredRectangle(30, 40 + 350,1, 1, 1, this.vbom, 180, 30);
		final Rectangle button2Rectangle = this.makeColoredRectangle(270, 40+350,1, 1, 1, this.vbom, 180, 30);
		final Rectangle button3Rectangle = this.makeColoredRectangle(25, 40+520,1, 1, 1, this.vbom, 220, 30);
		final Rectangle button4Rectangle = this.makeColoredRectangle(270, 40+520,1, 1, 1, this.vbom, 180, 30);
		
		//Orders rectangle
		mainGameScene.attachChild(orderRectangle);
		
		//Time Rectangle
		timeRectangle.setTag(500);
		mainGameScene.attachChild(timeRectangle);
		//Button rectangles
		mainGameScene.attachChild(button1Rectangle);
		mainGameScene.attachChild(button2Rectangle);
		mainGameScene.attachChild(button3Rectangle);
		mainGameScene.attachChild(button4Rectangle);
		
		mainGameScene.attachChild(button1Text);
		mainGameScene.attachChild(button2Text);
		mainGameScene.attachChild(button3Text);
		mainGameScene.attachChild(button4Text);
		
		mainGameScene.attachChild(orderText);
		
		//Victory and defeat
		mainGameScene.registerTouchArea(loseSplash);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(loseSplash);
		
		mainGameScene.registerTouchArea(victorySplash);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(victorySplash);
		
		gt = new GameThread(mainGameScene, gm, orderRectangle, loseSplash, victorySplash);
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
			gt.start();
			break;
		}
	}
	//Rectangle drawing
	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue, VertexBufferObjectManager vbom, int width, int height) {
		final Rectangle coloredRect = new Rectangle(pX, pY, width, height, vbom);
		coloredRect.setColor(pRed, pGreen, pBlue);
		return coloredRect;
	}
	
	public void setToggled(String buttonIdentifier)
	{
		//this.button1Text.setText(this.button1Text.getText() + "a");
		this.gm.pressButton(buttonIdentifier);
	}
	
	public Sprite onButtonFactory(ITextureRegion texture, final int i, final Sprite[] buttonsOff)
	{
		final String identifier;
		
		if(i < 4) identifier = "a" + (i%4 + 1);
		else if(i < 8) identifier = "c" + (i%4 + 1);
		else if(i < 12) identifier = "k" + (i%4 + 1);
		else identifier = "n" + (i%4 + 1);
		
		Sprite button = new Sprite(0, 0, texture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonsOff[i].setVisible(!this.isVisible());
					setToggled(identifier);
				}
				return true;
			}
		};
		
		return button;
	}


}

