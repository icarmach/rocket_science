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

	BitmapTextureAtlas storyTextureAtlas;
	ITextureRegion storyTextureRegion;

	BitmapTextureAtlas instructionTextureAtlas;
	ITextureRegion instructionTextureRegion;

	BitmapTextureAtlas loseTextureAtlas;
	ITextureRegion loseTextureRegion;
	//D:
	BitmapTextureAtlas victoryTextureAtlas;
	ITextureRegion victoryTextureRegion;

	Scene splashScene;
	Scene titleScene;
	Scene tutorialScene;
	Scene mainGameScene;
	Scene storyScene;
	Scene instructionScene;
	//Text
	Font mFont;
	VertexBufferObjectManager vbom;
	//Game Logic
	GameManager gm;
	GameThread gt;
	int section;
	//Text

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
		MAINGAME,
		STORY,
		INSTRUCTION,
		TUTORIAL
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

	//Method loads all of the story scene resources
	public void loadStorySceneResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		storyTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 480, 720, TextureOptions.DEFAULT);
		storyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(storyTextureAtlas, activity, "story.png", 0, 0);
		storyTextureAtlas.load();
	}


	//Method loads all of the instruction scene resources
	public void loadInstructionSceneResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		instructionTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 480, 720, TextureOptions.DEFAULT);
		instructionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(instructionTextureAtlas, activity, "instruction.png", 0, 0);
		instructionTextureAtlas.load();
	}


	//Method creates the Splash Scene
	public Scene createSplashScene() {
		//Create the Splash Scene and set background color to red and add the splash logo.
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

	//Method creates the Story Scene
	public Scene createStoryScene() {
		loadStorySceneResources();

		//Create the Story Scene and set background color to black and add the Story picture.
		storyScene = new Scene();
		storyScene.setBackground(new Background(0, 0, 0));
		Sprite story = new Sprite(0, 0, storyTextureRegion, activity.getVertexBufferObjectManager())
		{

			protected void preDraw(GLState pGLState, Camera pCamera)
			{
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		BitmapTextureAtlas nextButtonTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		ITextureRegion nextButtonTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(nextButtonTextureAtlas, activity, "next.png", 0, 0);
		nextButtonTextureAtlas.load();

		Sprite nextButton = new Sprite(0, 0, nextButtonTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionUp()) {
					setCurrentScene(SceneType.INSTRUCTION);
				}
				return true;
			}
		};

		story.setPosition(0,0);
		storyScene.attachChild(story);

		nextButton.setPosition((camera.getWidth() - nextButton.getWidth()) * 0.5f + 150, (camera.getHeight() - nextButton.getHeight()) * 0.5f + 200);
		storyScene.setTouchAreaBindingOnActionDownEnabled(true);

		storyScene.registerTouchArea(nextButton);
		storyScene.setTouchAreaBindingOnActionDownEnabled(true);
		storyScene.attachChild(nextButton);




		return storyScene;
	}

	//Method creates the Instruction Scene
	public Scene createInstructionScene() {
		loadInstructionSceneResources();

		//Create the Instruction Scene and set background color to black and add the Instruction picture.
		instructionScene = new Scene();
		instructionScene.setBackground(new Background(0, 0, 0));
		Sprite instruction = new Sprite(0, 0, instructionTextureRegion, activity.getVertexBufferObjectManager())
		{

			protected void preDraw(GLState pGLState, Camera pCamera)
			{
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		instruction.setPosition(0,0);
		instructionScene.attachChild(instruction);

		BitmapTextureAtlas okButtonTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		ITextureRegion okButtonTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(okButtonTextureAtlas, activity, "ok.png", 0, 0);
		okButtonTextureAtlas.load();

		Sprite okButton = new Sprite(0, 0, okButtonTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				setCurrentScene(SceneType.MAINGAME);

				return true;
			}
		};

		okButton.setPosition((camera.getWidth() - okButton.getWidth()) * 0.5f + 100, (camera.getHeight() - okButton.getHeight()) * 0.5f + 200);
		instructionScene.setTouchAreaBindingOnActionDownEnabled(true);

		instructionScene.registerTouchArea(okButton);
		instructionScene.setTouchAreaBindingOnActionDownEnabled(true);
		instructionScene.attachChild(okButton);

		return instructionScene;
	}



	//Method creates all of the Game Scenes
	public void createGameScenes() {
		//Create the Title Scene
		titleScene = new Scene();
		createTitleScene();

		//Create the Tutorial Scene
		tutorialScene = new Scene();
		createTutorialScene();

		//Create the Main Game Scene
		mainGameScene = new Scene();
		createMainGameScene();

		//Create the Main Game Scene
		storyScene = new Scene();
		createStoryScene();

		//Create the Main Game Scene
		instructionScene = new Scene();
		createInstructionScene();
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
		
		BitmapTextureAtlas tutorialButtonTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		ITextureRegion tutorialButtonTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tutorialButtonTextureAtlas, activity, "menu_tutorial.png", 0, 0);
		tutorialButtonTextureAtlas.load();

		Sprite tutorialButton = new Sprite(0, 0, tutorialButtonTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				setCurrentScene(SceneType.TUTORIAL);

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
		tutorialButton.setPosition((camera.getWidth() - playButton.getWidth()) * 0.5f, (camera.getHeight() - playButton.getHeight()) * 0.5f + 50);
		playButton.setPosition((camera.getWidth() - playButton.getWidth()) * 0.5f, (camera.getHeight() - playButton.getHeight()) * 0.5f + 150);
		quitButton.setPosition((camera.getWidth() - quitButton.getWidth()) * 0.5f, (camera.getHeight() - quitButton.getHeight()) * 0.5f + 250);

		titleScene.registerTouchArea(gameBanner);
		titleScene.setTouchAreaBindingOnActionDownEnabled(true);
		titleScene.attachChild(gameBanner);
		
		titleScene.registerTouchArea(tutorialButton);
		titleScene.setTouchAreaBindingOnActionDownEnabled(true);
		titleScene.attachChild(tutorialButton);

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
		
		//Top options
		//Armory Button
		BitmapTextureAtlas armoryTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 240, 120, TextureOptions.DEFAULT);
		ITextureRegion armoryTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(armoryTextureAtlas, activity, "armory.png", 0, 0);
		armoryTextureAtlas.load();

		Sprite armory = new Sprite(0, 0, armoryTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				armoryIn(mainGameScene);
				communicationOut(mainGameScene);
				kitchenOut(mainGameScene);
				navigationOut(mainGameScene);
				
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
				armoryOut(mainGameScene);
				communicationIn(mainGameScene);
				kitchenOut(mainGameScene);
				navigationOut(mainGameScene);
				
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
				armoryOut(mainGameScene);
				communicationOut(mainGameScene);
				kitchenIn(mainGameScene);
				navigationOut(mainGameScene);
				
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
				armoryOut(mainGameScene);
				communicationOut(mainGameScene);
				kitchenOut(mainGameScene);
				navigationIn(mainGameScene);
				
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
		orderText = new Text(20,10, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		orderText.setText("Instruccion 1");
		th.addTag("orderText");
		orderText.setTag(th.getTag("orderText"));
		//orderText.setTag(100);

		//Button text

		armory.setPosition(0, 140);
		communication.setPosition(240, 140);
		kitchen.setPosition(0, 260);
		navigation.setPosition(240, 260);

		buttonsPanel.setPosition(0,380);

		//Victory and defeat
		loseSplash.setPosition(2000,2000);
		victorySplash.setPosition(2000,2000);

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


		this.createArmoryControls(mainGameScene);
		this.createCommunicationControls(mainGameScene);
		this.createKitchenControls(mainGameScene);
		this.createNavigationControls(mainGameScene);

		//Print the texts and the background
		//We create the rectangles
		final Rectangle orderRectangle = this.makeColoredRectangle(0,0,1, 1, 1, this.vbom, (int)this.camera.getWidth(), 80);
		final Rectangle timeRectangle = this.makeColoredRectangle(0,75,1, 0, 0, this.vbom, (int)this.camera.getWidth(), 5);

		final Rectangle advancedRectangle = this.makeColoredRectangle(10,100,0, 1, 0, this.vbom, (int)this.camera.getWidth() - 20, 5);

		BitmapTextureAtlas NaveChicaAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 40, 20, TextureOptions.DEFAULT);
		ITextureRegion NaveChicaTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(NaveChicaAtlas, activity, "navechica.png", 0, 0);
		NaveChicaAtlas.load();
		final Sprite NaveChica = new Sprite(0, 93, NaveChicaTexture, activity.getVertexBufferObjectManager());
		th.addTag("NaveChica");
		NaveChica.setTag(th.getTag("NaveChica"));
		
		
		//Orders rectangle
		mainGameScene.attachChild(orderRectangle);
		
		mainGameScene.attachChild(advancedRectangle);
		
		mainGameScene.attachChild(NaveChica);

		//Time Rectangle
		th.addTag("timeRectangle");
		timeRectangle.setTag(th.getTag("timeRectangle"));
		mainGameScene.attachChild(timeRectangle);

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
	
	//Method create the Main Game Scene
	public void createTutorialScene() {
		tutorialScene.setBackground(new Background(0, 0, 0));
		
		//Top options
		//Armory Button
		BitmapTextureAtlas armoryTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 240, 120, TextureOptions.DEFAULT);
		ITextureRegion armoryTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(armoryTextureAtlas, activity, "armory.png", 0, 0);
		armoryTextureAtlas.load();

		Sprite armory = new Sprite(0, 0, armoryTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				armoryIn(tutorialScene);
				communicationOut(tutorialScene);
				kitchenOut(tutorialScene);
				navigationOut(tutorialScene);
				
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
				armoryOut(tutorialScene);
				communicationIn(tutorialScene);
				kitchenOut(tutorialScene);
				navigationOut(tutorialScene);
				
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
				armoryOut(tutorialScene);
				communicationOut(tutorialScene);
				kitchenIn(tutorialScene);
				navigationOut(tutorialScene);
				
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
				armoryOut(tutorialScene);
				communicationOut(tutorialScene);
				kitchenOut(tutorialScene);
				navigationIn(tutorialScene);
				
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
		orderText = new Text(20,10, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		orderText.setText("Instruccion 1");
		th.addTag("orderText");
		orderText.setTag(th.getTag("orderText"));
		//orderText.setTag(100);

		//Button text

		armory.setPosition(0, 140);
		communication.setPosition(240, 140);
		kitchen.setPosition(0, 260);
		navigation.setPosition(240, 260);

		buttonsPanel.setPosition(0,380);

		//Victory and defeat
		loseSplash.setPosition(2000,2000);
		victorySplash.setPosition(2000,2000);

		tutorialScene.registerTouchArea(armory);
		tutorialScene.setTouchAreaBindingOnActionDownEnabled(true);
		tutorialScene.attachChild(armory);

		tutorialScene.registerTouchArea(communication);
		tutorialScene.setTouchAreaBindingOnActionDownEnabled(true);
		tutorialScene.attachChild(communication);

		tutorialScene.registerTouchArea(kitchen);
		tutorialScene.setTouchAreaBindingOnActionDownEnabled(true);
		tutorialScene.attachChild(kitchen);

		tutorialScene.registerTouchArea(navigation);
		tutorialScene.setTouchAreaBindingOnActionDownEnabled(true);
		tutorialScene.attachChild(navigation);


		this.createArmoryControls(tutorialScene);
		this.createCommunicationControls(tutorialScene);
		this.createKitchenControls(tutorialScene);
		this.createNavigationControls(tutorialScene);

		//Print the texts and the background
		//We create the rectangles
		final Rectangle orderRectangle = this.makeColoredRectangle(0,0,1, 1, 1, this.vbom, (int)this.camera.getWidth(), 80);
		final Rectangle timeRectangle = this.makeColoredRectangle(0,75,1, 0, 0, this.vbom, (int)this.camera.getWidth(), 5);

		final Rectangle advancedRectangle = this.makeColoredRectangle(10,100,0, 1, 0, this.vbom, (int)this.camera.getWidth() - 20, 5);

		BitmapTextureAtlas NaveChicaAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 40, 20, TextureOptions.DEFAULT);
		ITextureRegion NaveChicaTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(NaveChicaAtlas, activity, "navechica.png", 0, 0);
		NaveChicaAtlas.load();
		final Sprite NaveChica = new Sprite(0, 93, NaveChicaTexture, activity.getVertexBufferObjectManager());
		th.addTag("NaveChica");
		NaveChica.setTag(th.getTag("NaveChica"));
		
		
		//Orders rectangle
		tutorialScene.attachChild(orderRectangle);
		
		tutorialScene.attachChild(advancedRectangle);
		
		tutorialScene.attachChild(NaveChica);

		//Time Rectangle
		th.addTag("timeRectangle");
		timeRectangle.setTag(th.getTag("timeRectangle"));
		tutorialScene.attachChild(timeRectangle);

		tutorialScene.attachChild(orderText);

		//Victory and defeat
		tutorialScene.registerTouchArea(loseSplash);
		tutorialScene.setTouchAreaBindingOnActionDownEnabled(true);
		tutorialScene.attachChild(loseSplash);

		tutorialScene.registerTouchArea(victorySplash);
		tutorialScene.setTouchAreaBindingOnActionDownEnabled(true);
		tutorialScene.attachChild(victorySplash);

		gt = new GameThread(tutorialScene, gm, orderRectangle, loseSplash, victorySplash);
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
		case TUTORIAL:
			engine.setScene(tutorialScene);
			break;
		case MAINGAME:
			engine.setScene(mainGameScene);
			gt.start();
			break;
		case STORY:
			engine.setScene(storyScene);
			break;
		case INSTRUCTION:
			engine.setScene(instructionScene);
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

	public void setToggled(String buttonIdentifier, int amount)
	{
		//this.button1Text.setText(this.button1Text.getText() + "a");
		this.gm.setButtonValue(buttonIdentifier, amount);
	}


	public void createArmoryControls(Scene mainGameScene)
	{
		//Fondo
		BitmapTextureAtlas ArmoryPanelTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 480, 480, TextureOptions.DEFAULT);
		ITextureRegion ArmoryPanelTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ArmoryPanelTextureAtlas, activity, "buttons_panel.JPG", 0, 0);
		ArmoryPanelTextureAtlas.load();
		Sprite ArmoryPanel = new Sprite(0, 0, ArmoryPanelTexture, activity.getVertexBufferObjectManager());
		ArmoryPanel.setPosition(0,380);
		th.addTag("ArmoryPanel");
		ArmoryPanel.setTag(th.getTag("ArmoryPanel"));
		mainGameScene.attachChild(ArmoryPanel);

		//Rectangulos para el texto
		final Rectangle LaserBlasterRectangle = this.makeColoredRectangle(30, 40 + 350,1, 1, 1, this.vbom, 180, 30);
		th.addTag("LaserBlasterRectangle");
		LaserBlasterRectangle.setTag(th.getTag("LaserBlasterRectangle"));

		final Rectangle MachinegunRectangle = this.makeColoredRectangle(270, 40+350,1, 1, 1, this.vbom, 180, 30);
		th.addTag("MachinegunRectangle");
		MachinegunRectangle.setTag(th.getTag("MachinegunRectangle"));

		final Rectangle MissileCountRectangle = this.makeColoredRectangle(25, 40+520,1, 1, 1, this.vbom, 220, 30);
		th.addTag("MissileCountRectangle");
		MissileCountRectangle.setTag(th.getTag("MissileCountRectangle"));
		
		mainGameScene.attachChild(LaserBlasterRectangle);
		mainGameScene.attachChild(MachinegunRectangle);
		mainGameScene.attachChild(MissileCountRectangle);

		//Texto para los botones
		Text LaserBlasterText = new Text(45, 40+350, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		LaserBlasterText.setText("Laser Blaster");
		th.addTag("LaserBlasterText");
		LaserBlasterText.setTag(th.getTag("LaserBlasterText"));

		Text MachinegunText = new Text(280, 40+350, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		MachinegunText.setText("Machinegun");
		th.addTag("MachinegunText");
		MachinegunText.setTag(th.getTag("MachinegunText"));
		
		final Text MissileCountText = new Text(25, 560, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		MissileCountText.setText("Missile Count: 0");
		th.addTag("MissileCountText");
		MissileCountText.setTag(th.getTag("MissileCountText"));

		mainGameScene.attachChild(LaserBlasterText);
		mainGameScene.attachChild(MachinegunText);
		mainGameScene.attachChild(MissileCountText);

		//Laser Blaster Off
		BitmapTextureAtlas LaserBlasterAtlasOff = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion LaserBlasterTextureOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(LaserBlasterAtlasOff, activity, "button_laser_off.png", 0, 0);
		LaserBlasterAtlasOff.load();

		final Sprite LaserBlasterOff = new Sprite(0, 0, LaserBlasterTextureOff, activity.getVertexBufferObjectManager());
		LaserBlasterOff.setVisible(false);
		th.addTag("LaserBlasterOff");
		LaserBlasterOff.setTag(th.getTag("LaserBlasterOff"));
		LaserBlasterOff.setPosition(72, 420);
		mainGameScene.attachChild(LaserBlasterOff);

		//Laser Blaster On
		BitmapTextureAtlas LaserBlasterAtlasOn = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion LaserBlasterTexturaOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(LaserBlasterAtlasOn, activity, "button_laser_on.png", 0, 0);
		LaserBlasterAtlasOn.load();

		final Sprite LaserBlasterOn = new Sprite(0, 0, LaserBlasterTexturaOn, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					LaserBlasterOff.setVisible(!this.isVisible());
					setToggled("LaserBlaster");
				}
				return true;
			}
		};
		th.addTag("LaserBlasterOn");
		LaserBlasterOn.setTag(th.getTag("LaserBlasterOn"));
		LaserBlasterOn.setPosition(72,420);
		mainGameScene.registerTouchArea(LaserBlasterOn);
		mainGameScene.attachChild(LaserBlasterOn);

		//Machinegun Off
		BitmapTextureAtlas MachinegunrAtlasOff = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion MachinegunTextureOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MachinegunrAtlasOff, activity, "button_machinegun_off.png", 0, 0);
		MachinegunrAtlasOff.load();

		final Sprite MachinegunOff = new Sprite(0, 0, MachinegunTextureOff, activity.getVertexBufferObjectManager());
		MachinegunOff.setVisible(false);
		th.addTag("MachinegunOff");
		MachinegunOff.setTag(th.getTag("MachinegunOff"));
		MachinegunOff.setPosition(300, 420);
		mainGameScene.attachChild(MachinegunOff);


		//Machinegun On
		BitmapTextureAtlas MachinegunAtlasOn = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion MachinegunTexturaOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MachinegunAtlasOn, activity, "button_machinegun_on.png", 0, 0);
		MachinegunAtlasOn.load();

		final Sprite MachinegunOn = new Sprite(0, 0, MachinegunTexturaOn, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					MachinegunOff.setVisible(!this.isVisible());
					setToggled("Machinegun");
				}
				return true;
			}
		};
		th.addTag("MachinegunOn");
		MachinegunOn.setTag(th.getTag("MachinegunOn"));
		MachinegunOn.setPosition(300,420);
		mainGameScene.registerTouchArea(MachinegunOn);
		mainGameScene.attachChild(MachinegunOn);

		//Missile Count Slider
		BitmapTextureAtlas MissileCountAtlas1 = new BitmapTextureAtlas(activity.getTextureManager(), 250, 25, TextureOptions.DEFAULT);
		ITextureRegion MissileCountTexture1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MissileCountAtlas1, activity, "slider1.png", 0, 0);
		MissileCountAtlas1.load();
		final Sprite MissileCount1 = new Sprite(0, 0, MissileCountTexture1, activity.getVertexBufferObjectManager());
		MissileCount1.setPosition(25,600);
		th.addTag("MissileCount1");
		MissileCount1.setTag(th.getTag("MissileCount1"));
		mainGameScene.attachChild(MissileCount1);
		
		BitmapTextureAtlas MissileCountAtlas2 = new BitmapTextureAtlas(activity.getTextureManager(), 40, 40, TextureOptions.DEFAULT);
		ITextureRegion MissileCountTexture2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MissileCountAtlas2, activity, "missile_slider.png", 0, 0);
		MissileCountAtlas2.load();
		final Sprite MissileCount2 = new Sprite(0, 0, MissileCountTexture2, activity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.getX() < MissileCount1.getX() + MissileCount1.getWidth() - this.getWidth() && pSceneTouchEvent.getX() > MissileCount1.getX())
				{
					this.setPosition(pSceneTouchEvent.getX(), this.getY());

					float value = (pSceneTouchEvent.getX() - MissileCount1.getX()) / MissileCount1.getWidth() * 11;
					MissileCountText.setText("Missile Count: " + (int) value);
					if(pSceneTouchEvent.isActionUp())
					{
						setToggled("MissileCount", (int)value);
					}
				}
				return true;
			}
		};
		MissileCount2.setPosition(30,600 + MissileCountAtlas1.getHeight()/2 - MissileCount2.getHeight() / 2);
		th.addTag("MissileCount2");
		MissileCount2.setTag(th.getTag("MissileCount2"));
		mainGameScene.registerTouchArea(MissileCount2);
		mainGameScene.attachChild(MissileCount2);
	}

	public void armoryOut(Scene mainGameScene)
	{
		mainGameScene.getChildByTag(th.getTag("ArmoryPanel")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("LaserBlasterRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MachinegunRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MissileCountRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("LaserBlasterText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MachinegunText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MissileCountText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("LaserBlasterOff")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("LaserBlasterOn")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MachinegunOff")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MachinegunOn")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MissileCount1")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MissileCount2")).setPosition(2000, 2000);
	}
	
	public void armoryIn(Scene mainGameScene)
	{
		mainGameScene.getChildByTag(th.getTag("ArmoryPanel")).setPosition(0,380);
		mainGameScene.getChildByTag(th.getTag("LaserBlasterRectangle")).setPosition(30, 390);
		mainGameScene.getChildByTag(th.getTag("MachinegunRectangle")).setPosition(270, 390);
		mainGameScene.getChildByTag(th.getTag("MissileCountRectangle")).setPosition(25, 560);
		mainGameScene.getChildByTag(th.getTag("LaserBlasterText")).setPosition(45, 390);
		mainGameScene.getChildByTag(th.getTag("MachinegunText")).setPosition(280, 390);
		mainGameScene.getChildByTag(th.getTag("MissileCountText")).setPosition(25, 560);
		mainGameScene.getChildByTag(th.getTag("LaserBlasterOff")).setPosition(72, 420);
		mainGameScene.getChildByTag(th.getTag("LaserBlasterOn")).setPosition(72,420);
		mainGameScene.getChildByTag(th.getTag("MachinegunOff")).setPosition(300, 420);
		mainGameScene.getChildByTag(th.getTag("MachinegunOn")).setPosition(300,420);
		mainGameScene.getChildByTag(th.getTag("MissileCount1")).setPosition(25,600);
		String current = (String) ((Text) mainGameScene.getChildByTag(th.getTag("MissileCountText"))).getText();
		int count = Integer.parseInt("" + current.charAt(current.length() - 1));
		
		mainGameScene.getChildByTag(th.getTag("MissileCount2")).setPosition(30 + ((Sprite)mainGameScene.getChildByTag(th.getTag("MissileCount1"))).getWidth()/10 * count, 600 - 8);
	}

	public void createCommunicationControls(Scene mainGameScene){
		//Fondo
		BitmapTextureAtlas CommunicationPanelTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 480, 480, TextureOptions.DEFAULT);
		ITextureRegion CommunicationPanelTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(CommunicationPanelTextureAtlas, activity, "communication.jpg", 0, 0);
		CommunicationPanelTextureAtlas.load();
		Sprite CommunicationPanel = new Sprite(0, 0, CommunicationPanelTexture, activity.getVertexBufferObjectManager());
		CommunicationPanel.setPosition(2000,2000);
		th.addTag("CommunicationPanel");
		CommunicationPanel.setTag(th.getTag("CommunicationPanel"));
		mainGameScene.attachChild(CommunicationPanel);
		
		//Rectangulos para el texto
		final Rectangle SignalJammerRectangle = this.makeColoredRectangle(2000, 2000,1, 1, 1, this.vbom, 200, 30);
		th.addTag("SignalJammerRectangle");
		SignalJammerRectangle.setTag(th.getTag("SignalJammerRectangle"));

		final Rectangle WifiRectangle = this.makeColoredRectangle(2000, 2000,1, 1, 1, this.vbom, 180, 30);
		th.addTag("WifiRectangle");
		WifiRectangle.setTag(th.getTag("WifiRectangle"));
		
		final Rectangle SignalStrengthRectangle = this.makeColoredRectangle(2000, 2000,1, 1, 1, this.vbom, 220, 30);
		th.addTag("SignalStrengthRectangle");
		SignalStrengthRectangle.setTag(th.getTag("SignalStrengthRectangle"));
		
		mainGameScene.attachChild(SignalJammerRectangle);
		mainGameScene.attachChild(WifiRectangle);
		mainGameScene.attachChild(SignalStrengthRectangle);
		
		//Texto para los botones
		Text SignalJammerText = new Text(2000, 2000, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		SignalJammerText.setText("Signal Jammer");
		th.addTag("SignalJammerText");
		SignalJammerText.setTag(th.getTag("SignalJammerText"));
		
		Text WifiText = new Text(2000, 2000, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		WifiText.setText("Wifi");
		th.addTag("WifiText");
		WifiText.setTag(th.getTag("WifiText"));
		
		final Text SignalStrengthText = new Text(2000, 2000, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		SignalStrengthText.setText("Signal Strength: 0");
		th.addTag("SignalStrengthText");
		SignalStrengthText.setTag(th.getTag("SignalStrengthText"));
		
		mainGameScene.attachChild(SignalJammerText);
		mainGameScene.attachChild(WifiText);
		mainGameScene.attachChild(SignalStrengthText);
		
		//Signal Jammer Off
		BitmapTextureAtlas SignalJammerAtlasOff = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion SignalJammerTextureOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(SignalJammerAtlasOff, activity, "button_signaljammer_off.png", 0, 0);
		SignalJammerAtlasOff.load();
		
		final Sprite SignalJammerOff = new Sprite(0, 0, SignalJammerTextureOff, activity.getVertexBufferObjectManager());
		SignalJammerOff.setVisible(false);
		th.addTag("SignalJammerOff");
		SignalJammerOff.setTag(th.getTag("SignalJammerOff"));
		SignalJammerOff.setPosition(2000, 2000);
		mainGameScene.attachChild(SignalJammerOff);
		
		//Signal Jammer On
		BitmapTextureAtlas SignalJammerAtlasOn = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion SignalJammerTexturaOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(SignalJammerAtlasOn, activity, "button_signaljammer_on.png", 0, 0);
		SignalJammerAtlasOn.load();

		final Sprite SignalJammerOn = new Sprite(0, 0, SignalJammerTexturaOn, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					SignalJammerOff.setVisible(!this.isVisible());
					setToggled("SignalJammer");
				}
				return true;
			}
		};
		
		th.addTag("SignalJammerOn");
		SignalJammerOn.setTag(th.getTag("SignalJammerOn"));
		SignalJammerOn.setPosition(2000,2000);
		mainGameScene.registerTouchArea(SignalJammerOn);
		mainGameScene.attachChild(SignalJammerOn);
		
		//Wifi Off
		BitmapTextureAtlas WifiAtlasOff = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion WifiTextureOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(WifiAtlasOff, activity, "button_wifi_off.png", 0, 0);
		WifiAtlasOff.load();
		
		final Sprite WifiOff = new Sprite(0, 0, WifiTextureOff, activity.getVertexBufferObjectManager());
		WifiOff.setVisible(false);
		th.addTag("WifiOff");
		WifiOff.setTag(th.getTag("WifiOff"));
		WifiOff.setPosition(2000, 2000);
		mainGameScene.attachChild(WifiOff);
		
		//Wifi On
		BitmapTextureAtlas WifiAtlasOn = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion WifiTexturaOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(WifiAtlasOn, activity, "button_wifi_on.png", 0, 0);
		WifiAtlasOn.load();

		final Sprite WifiOn = new Sprite(0, 0, WifiTexturaOn, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					WifiOff.setVisible(!this.isVisible());
					setToggled("Wifi");
				}
				return true;
			}
		};
		th.addTag("WifiOn");
		WifiOn.setTag(th.getTag("WifiOn"));
		WifiOn.setPosition(2000,2000);
		mainGameScene.registerTouchArea(WifiOn);
		mainGameScene.attachChild(WifiOn);
		
		//Signal Strength Slider
		BitmapTextureAtlas SignalStrengthAtlas1 = new BitmapTextureAtlas(activity.getTextureManager(), 250, 25, TextureOptions.DEFAULT);
		ITextureRegion SignalStrengthTexture1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(SignalStrengthAtlas1, activity, "slider1.png", 0, 0);
		SignalStrengthAtlas1.load();
		final Sprite SignalStrength1 = new Sprite(0, 0, SignalStrengthTexture1, activity.getVertexBufferObjectManager());
		SignalStrength1.setPosition(2000,2000);
		th.addTag("SignalStrength1");
		SignalStrength1.setTag(th.getTag("SignalStrength1"));
		mainGameScene.attachChild(SignalStrength1);
		
		BitmapTextureAtlas SignalStrengthAtlas2 = new BitmapTextureAtlas(activity.getTextureManager(), 40, 40, TextureOptions.DEFAULT);
		ITextureRegion SignalStrengthTexture2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(SignalStrengthAtlas2, activity, "signal_slider.png", 0, 0);
		SignalStrengthAtlas2.load();
		final Sprite SignalStrength2 = new Sprite(0, 0, SignalStrengthTexture2, activity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.getX() < SignalStrength1.getX() + SignalStrength1.getWidth() - this.getWidth() && pSceneTouchEvent.getX() > SignalStrength1.getX())
				{
					this.setPosition(pSceneTouchEvent.getX(), this.getY());

					float value = (pSceneTouchEvent.getX() - SignalStrength1.getX()) / SignalStrength1.getWidth() * 11;
					SignalStrengthText.setText("Signal Strength: " + (int) value);
					if(pSceneTouchEvent.isActionUp())
					{
						setToggled("SignalStrength", (int)value);
					}
				}
				return true;
			}
		};
		
		SignalStrength2.setPosition(2000,2000 + SignalStrengthAtlas1.getHeight()/2 - SignalStrength2.getHeight() / 2);
		th.addTag("SignalStrength2");
		SignalStrength2.setTag(th.getTag("SignalStrength2"));
		mainGameScene.registerTouchArea(SignalStrength2);
		mainGameScene.attachChild(SignalStrength2);
	}
	
	public void communicationOut(Scene mainGameScene)
	{
		mainGameScene.getChildByTag(th.getTag("CommunicationPanel")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("SignalJammerRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("WifiRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("SignalStrengthRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("SignalJammerText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("WifiText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("SignalStrengthText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("SignalJammerOff")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("SignalJammerOn")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("WifiOff")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("WifiOn")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("SignalStrength1")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("SignalStrength2")).setPosition(2000, 2000);
	}
	
	public void communicationIn(Scene mainGameScene)
	{
		mainGameScene.getChildByTag(th.getTag("CommunicationPanel")).setPosition(0,380);
		mainGameScene.getChildByTag(th.getTag("SignalJammerRectangle")).setPosition(30, 390);
		mainGameScene.getChildByTag(th.getTag("WifiRectangle")).setPosition(270, 390);
		mainGameScene.getChildByTag(th.getTag("SignalStrengthRectangle")).setPosition(25, 560);
		mainGameScene.getChildByTag(th.getTag("SignalJammerText")).setPosition(45, 390);
		mainGameScene.getChildByTag(th.getTag("WifiText")).setPosition(280, 390);
		mainGameScene.getChildByTag(th.getTag("SignalStrengthText")).setPosition(25, 560);
		mainGameScene.getChildByTag(th.getTag("SignalJammerOff")).setPosition(72, 420);
		mainGameScene.getChildByTag(th.getTag("SignalJammerOn")).setPosition(72,420);
		mainGameScene.getChildByTag(th.getTag("WifiOff")).setPosition(300, 420);
		mainGameScene.getChildByTag(th.getTag("WifiOn")).setPosition(300,420);
		mainGameScene.getChildByTag(th.getTag("SignalStrength1")).setPosition(25,600);
		String current = (String) ((Text) mainGameScene.getChildByTag(th.getTag("SignalStrengthText"))).getText();
		int count = Integer.parseInt("" + current.charAt(current.length() - 1));
		
		mainGameScene.getChildByTag(th.getTag("SignalStrength2")).setPosition(30 + ((Sprite)mainGameScene.getChildByTag(th.getTag("SignalStrength1"))).getWidth()/10 * count, 600 - 8);
	}

	public void createKitchenControls(Scene mainGameScene)
	{
		//Fondo
		BitmapTextureAtlas KitchenPanelTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 480, 480, TextureOptions.DEFAULT);
		ITextureRegion KitchenPanelTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(KitchenPanelTextureAtlas, activity, "kitchen.jpg", 0, 0);
		KitchenPanelTextureAtlas.load();
		Sprite KitchenPanel = new Sprite(0, 0, KitchenPanelTexture, activity.getVertexBufferObjectManager());
		KitchenPanel.setPosition(2000,2000);
		th.addTag("KitchenPanel");
		KitchenPanel.setTag(th.getTag("KitchenPanel"));
		mainGameScene.attachChild(KitchenPanel);
		
		//Rectangulos para el texto
		final Rectangle OvenRectangle = this.makeColoredRectangle(2000, 2000,1, 1, 1, this.vbom, 180, 30);
		th.addTag("OvenRectangle");
		OvenRectangle.setTag(th.getTag("OvenRectangle"));
		
		final Rectangle FridgeRectangle = this.makeColoredRectangle(2000, 2000,1, 1, 1, this.vbom, 180, 30);
		th.addTag("FridgeRectangle");
		FridgeRectangle.setTag(th.getTag("FridgeRectangle"));
		
		final Rectangle MicrowaveRectangle = this.makeColoredRectangle(2000, 2000,1, 1, 1, this.vbom, 180, 30);
		th.addTag("MicrowaveRectangle");
		MicrowaveRectangle.setTag(th.getTag("MicrowaveRectangle"));
		
		mainGameScene.attachChild(OvenRectangle);
		mainGameScene.attachChild(FridgeRectangle);
		mainGameScene.attachChild(MicrowaveRectangle);
		
		//Texto para los botones
		final Text OvenText = new Text(2000, 2000, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		OvenText.setText("Oven: 5");
		th.addTag("OvenText");
		OvenText.setTag(th.getTag("OvenText"));
		
		Text FridgeText = new Text(2000, 2000, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		FridgeText.setText("Fridge");
		th.addTag("FridgeText");
		FridgeText.setTag(th.getTag("FridgeText"));
		
		Text MicrowaveText = new Text(2000, 2000, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		MicrowaveText.setText("Microwave");
		th.addTag("MicrowaveText");
		MicrowaveText.setTag(th.getTag("MicrowaveText"));
		
		mainGameScene.attachChild(OvenText);
		mainGameScene.attachChild(FridgeText);
		mainGameScene.attachChild(MicrowaveText);
		
		//Oven
		BitmapTextureAtlas OvenMap = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion OvenTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(OvenMap, activity, "knob_oven.png", 0, 0);
		OvenMap.load();
		final Sprite Oven = new Sprite(2000, 2000, OvenTexture, activity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				float x0 = this.getX() + this.getWidth() / 2;
				float y0 = this.getY() + this.getHeight() / 2;
				float x1 = pSceneTouchEvent.getX();
				float y1 = pSceneTouchEvent.getY();

				float angle = 0;

				if(y0 != y1)
				{
					angle = - (float)Math.atan((double) (x1 - x0)/(y1 - y0));
				}
				angle = (float) ((angle*360 / Math.PI));

				this.setRotation(angle);

				OvenText.setText("Oven: " + (int)((180 + angle) / 36));
				//Button crap
				if(pSceneTouchEvent.isActionUp())
				{
					setToggled("Oven", (int)((180 + angle) / 36));
				}
				return true;
			}
		};
		th.addTag("Oven");
		Oven.setTag(th.getTag("Oven"));
		mainGameScene.registerTouchArea(Oven);
		mainGameScene.attachChild(Oven);
		
		//Fridge Off
		BitmapTextureAtlas FridgeAtlasOff = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion FridgeTextureOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(FridgeAtlasOff, activity, "button_fridge_off.png", 0, 0);
		FridgeAtlasOff.load();

		final Sprite FridgeOff = new Sprite(2000, 2000, FridgeTextureOff, activity.getVertexBufferObjectManager());
		FridgeOff.setVisible(false);
		th.addTag("FridgeOff");
		FridgeOff.setTag(th.getTag("FridgeOff"));
		mainGameScene.attachChild(FridgeOff);
		
		//Fridge On
		BitmapTextureAtlas FridgeAtlasOn = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion FridgeTexturaOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(FridgeAtlasOn, activity, "button_fridge_on.png", 0, 0);
		FridgeAtlasOn.load();

		final Sprite FridgeOn = new Sprite(2000, 2000, FridgeTexturaOn, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					FridgeOff.setVisible(!this.isVisible());
					setToggled("Fridge");
				}
				return true;
			}
		};
		th.addTag("FridgeOn");
		FridgeOn.setTag(th.getTag("FridgeOn"));
		mainGameScene.registerTouchArea(FridgeOn);
		mainGameScene.attachChild(FridgeOn);
		
		//Microwave Off
		BitmapTextureAtlas MicrowaveAtlasOff = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion MicrowaveTextureOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MicrowaveAtlasOff, activity, "button_microwave_off.png", 0, 0);
		MicrowaveAtlasOff.load();

		final Sprite MicrowaveOff = new Sprite(2000, 2000, MicrowaveTextureOff, activity.getVertexBufferObjectManager());
		MicrowaveOff.setVisible(false);
		th.addTag("MicrowaveOff");
		MicrowaveOff.setTag(th.getTag("MicrowaveOff"));
		mainGameScene.attachChild(MicrowaveOff);

		//Microwave On
		BitmapTextureAtlas MicrowaveAtlasOn = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion MicrowaveTexturaOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MicrowaveAtlasOn, activity, "button_microwave_on.png", 0, 0);
		MicrowaveAtlasOn.load();

		final Sprite MicrowaveOn = new Sprite(2000, 2000, MicrowaveTexturaOn, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					MicrowaveOff.setVisible(!this.isVisible());
					setToggled("Microwave");
				}
				return true;
			}
		};
		th.addTag("MicrowaveOn");
		MicrowaveOn.setTag(th.getTag("MicrowaveOn"));
		mainGameScene.registerTouchArea(MicrowaveOn);
		mainGameScene.attachChild(MicrowaveOn);
	}

	public void kitchenOut(Scene mainGameScene)
	{
		mainGameScene.getChildByTag(th.getTag("KitchenPanel")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("OvenRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("FridgeRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MicrowaveRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("OvenText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("FridgeText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MicrowaveText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("Oven")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("FridgeOff")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("FridgeOn")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MicrowaveOff")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("MicrowaveOn")).setPosition(2000, 2000);
	}
	
	public void kitchenIn(Scene mainGameScene){
		mainGameScene.getChildByTag(th.getTag("KitchenPanel")).setPosition(0, 380);
		mainGameScene.getChildByTag(th.getTag("OvenRectangle")).setPosition(30, 390);
		mainGameScene.getChildByTag(th.getTag("FridgeRectangle")).setPosition(270, 390);
		mainGameScene.getChildByTag(th.getTag("MicrowaveRectangle")).setPosition(25, 560);
		mainGameScene.getChildByTag(th.getTag("OvenText")).setPosition(45, 390);
		mainGameScene.getChildByTag(th.getTag("FridgeText")).setPosition(280, 390);
		mainGameScene.getChildByTag(th.getTag("MicrowaveText")).setPosition(25, 560);
		mainGameScene.getChildByTag(th.getTag("Oven")).setPosition(72, 420);
		mainGameScene.getChildByTag(th.getTag("FridgeOff")).setPosition(300, 420);
		mainGameScene.getChildByTag(th.getTag("FridgeOn")).setPosition(300, 420);
		mainGameScene.getChildByTag(th.getTag("MicrowaveOff")).setPosition(25, 600);
		mainGameScene.getChildByTag(th.getTag("MicrowaveOn")).setPosition(25, 600);
	}

	public void createNavigationControls(Scene mainGameScene)
	{
		//Fondo
		BitmapTextureAtlas NavigationPanelTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 480, 480, TextureOptions.DEFAULT);
		ITextureRegion NavigationPanelTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(NavigationPanelTextureAtlas, activity, "navigation.jpg", 0, 0);
		NavigationPanelTextureAtlas.load();
		Sprite NavigationPanel = new Sprite(2000, 2000, NavigationPanelTexture, activity.getVertexBufferObjectManager());
		th.addTag("NavigationPanel");
		NavigationPanel.setTag(th.getTag("NavigationPanel"));
		mainGameScene.attachChild(NavigationPanel);
		
		//Rectangulos para el texto
		final Rectangle SpeedRectangle = this.makeColoredRectangle(2000, 2000,1, 1, 1, this.vbom, 180, 30);
		th.addTag("SpeedRectangle");
		SpeedRectangle.setTag(th.getTag("SpeedRectangle"));

		final Rectangle EnginesRectangle = this.makeColoredRectangle(2000, 2000,1, 1, 1, this.vbom, 180, 30);
		th.addTag("EnginesRectangle");
		EnginesRectangle.setTag(th.getTag("EnginesRectangle"));

		final Rectangle AutopilotRectangle = this.makeColoredRectangle(2000, 2000,1, 1, 1, this.vbom, 180, 30);
		th.addTag("AutopilotRectangle");
		AutopilotRectangle.setTag(th.getTag("AutopilotRectangle"));

		mainGameScene.attachChild(SpeedRectangle);
		mainGameScene.attachChild(EnginesRectangle);
		mainGameScene.attachChild(AutopilotRectangle);
		
		//Texto para los botones
		final Text SpeedText = new Text(2000, 2000, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		SpeedText.setText("Speed: 5");
		th.addTag("SpeedText");
		SpeedText.setTag(th.getTag("SpeedText"));
		
		Text EnginesText = new Text(2000, 2000, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		EnginesText.setText("Engines");
		th.addTag("EnginesText");
		EnginesText.setTag(th.getTag("EnginesText"));
		
		Text AutopilotText = new Text(2000, 2000, this.mFont, defaultText, new TextOptions(HorizontalAlign.CENTER),this.vbom);
		AutopilotText.setText("Autopilot");
		th.addTag("AutopilotText");
		AutopilotText.setTag(th.getTag("AutopilotText"));
		
		mainGameScene.attachChild(SpeedText);
		mainGameScene.attachChild(EnginesText);
		mainGameScene.attachChild(AutopilotText);
		
		//Speed
		BitmapTextureAtlas SpeedMap = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion SpeedTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(SpeedMap, activity, "speed_knob.png", 0, 0);
		SpeedMap.load();
		final Sprite Speed = new Sprite(2000, 2000, SpeedTexture, activity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				float x0 = this.getX() + this.getWidth() / 2;
				float y0 = this.getY() + this.getHeight() / 2;
				float x1 = pSceneTouchEvent.getX();
				float y1 = pSceneTouchEvent.getY();

				float angle = 0;

				if(y0 != y1)
				{
					angle = - (float)Math.atan((double) (x1 - x0)/(y1 - y0));
				}
				angle = (float) ((angle*360 / Math.PI));

				this.setRotation(angle);

				SpeedText.setText("Speed: " + (int)((180 + angle) / 36));
				if(pSceneTouchEvent.isActionUp())
				{
					setToggled("Speed", (int)((180 + angle) / 36));
				}
				return true;
			}
		};
		th.addTag("Speed");
		Speed.setTag(th.getTag("Speed"));
		mainGameScene.registerTouchArea(Speed);
		mainGameScene.attachChild(Speed);
		
		//Engines Off
		BitmapTextureAtlas EnginesAtlasOff = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion EnginesTextureOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(EnginesAtlasOff, activity, "button_engines_off.png", 0, 0);
		EnginesAtlasOff.load();

		final Sprite EnginesOff = new Sprite(2000, 2000, EnginesTextureOff, activity.getVertexBufferObjectManager());
		EnginesOff.setVisible(false);
		th.addTag("EnginesOff");
		EnginesOff.setTag(th.getTag("EnginesOff"));
		mainGameScene.attachChild(EnginesOff);
		
		//Engines On
		BitmapTextureAtlas EnginesAtlasOn = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion EnginesTexturaOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(EnginesAtlasOn, activity, "button_engines_on.png", 0, 0);
		EnginesAtlasOn.load();

		final Sprite EnginesOn = new Sprite(2000, 2000, EnginesTexturaOn, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					EnginesOff.setVisible(!this.isVisible());
					setToggled("Engines");
				}
				return true;
			}
		};
		th.addTag("EnginesOn");
		EnginesOn.setTag(th.getTag("EnginesOn"));
		mainGameScene.registerTouchArea(EnginesOn);
		mainGameScene.attachChild(EnginesOn);

		//Autopilot Off
		BitmapTextureAtlas AutopilotAtlasOff = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion AutopilotTextureOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(AutopilotAtlasOff, activity, "button_autopilot_off.png", 0, 0);
		AutopilotAtlasOff.load();

		final Sprite AutopilotOff = new Sprite(2000, 2000, AutopilotTextureOff, activity.getVertexBufferObjectManager());
		AutopilotOff.setVisible(false);
		th.addTag("AutopilotOff");
		AutopilotOff.setTag(th.getTag("AutopilotOff"));
		mainGameScene.attachChild(AutopilotOff);

		//Autopilot On
		BitmapTextureAtlas AutopilotAtlasOn = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion AutopilotTexturaOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(AutopilotAtlasOn, activity, "button_autopilot_on.png", 0, 0);
		AutopilotAtlasOn.load();

		final Sprite AutopilotOn = new Sprite(2000, 2000, AutopilotTexturaOn, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					AutopilotOff.setVisible(!this.isVisible());
					setToggled("Autopilot");
				}
				return true;
			}
		};
		th.addTag("AutopilotOn");
		AutopilotOn.setTag(th.getTag("AutopilotOn"));
		mainGameScene.registerTouchArea(AutopilotOn);
		mainGameScene.attachChild(AutopilotOn);
	}

	public void navigationOut(Scene mainGameScene)
	{
		mainGameScene.getChildByTag(th.getTag("NavigationPanel")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("SpeedRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("EnginesRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("AutopilotRectangle")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("SpeedText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("EnginesText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("AutopilotText")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("Speed")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("EnginesOff")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("EnginesOn")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("AutopilotOff")).setPosition(2000, 2000);
		mainGameScene.getChildByTag(th.getTag("AutopilotOn")).setPosition(2000, 2000);
	}

	public void navigationIn(Scene mainGameScene){
		mainGameScene.getChildByTag(th.getTag("NavigationPanel")).setPosition(0, 380);
		mainGameScene.getChildByTag(th.getTag("SpeedRectangle")).setPosition(30, 390);
		mainGameScene.getChildByTag(th.getTag("EnginesRectangle")).setPosition(270, 390);
		mainGameScene.getChildByTag(th.getTag("AutopilotRectangle")).setPosition(25, 560);
		mainGameScene.getChildByTag(th.getTag("SpeedText")).setPosition(45, 390);
		mainGameScene.getChildByTag(th.getTag("EnginesText")).setPosition(280, 390);
		mainGameScene.getChildByTag(th.getTag("AutopilotText")).setPosition(25, 560);
		mainGameScene.getChildByTag(th.getTag("Speed")).setPosition(72, 420);
		mainGameScene.getChildByTag(th.getTag("EnginesOff")).setPosition(300, 420);
		mainGameScene.getChildByTag(th.getTag("EnginesOn")).setPosition(300, 420);
		mainGameScene.getChildByTag(th.getTag("AutopilotOff")).setPosition(25, 600);
		mainGameScene.getChildByTag(th.getTag("AutopilotOn")).setPosition(25, 600);
	}
}

