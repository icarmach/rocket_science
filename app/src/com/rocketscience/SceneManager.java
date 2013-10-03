package com.rocketscience;

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
	BitmapTextureAtlas splashTextureAtlas;
	ITextureRegion splashTextureRegion;
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
		
		BitmapTextureAtlas buttonOnTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion buttonOnTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonOnTextureAtlas, activity, "button_on.png", 0, 0);
		buttonOnTextureAtlas.load();
		
		BitmapTextureAtlas buttonOffTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 90, 90, TextureOptions.DEFAULT);
		ITextureRegion buttonOffTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonOffTextureAtlas, activity, "button_off.png", 0, 0);
		buttonOffTextureAtlas.load();
		
		//HUGE-ASS LIST OF BUTTONS
		// -- ARMORY BUTTONS --
		//Button 1 : Identifier "a1"
		final Sprite buttonOff1 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//this.setVisible(!this.isVisible());
				//button1.buttonText = "WHAT";
				return true;
			}
		};
		
		final Sprite buttonOn1 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff1.setVisible(!buttonOff1.isVisible());
					setToggled("a1");
				}
				return true;
			}
		};
		//Button 2 - "a2"
		final Sprite buttonOff2 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				return true;
			}
		};
		
		final Sprite buttonOn2 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff2.setVisible(!buttonOff2.isVisible());
					setToggled("a2");
				}
				return true;
			}
		};
		//Button 3 - "a3"
		final Sprite buttonOff3 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						return true;
					}
		};
				
		final Sprite buttonOn3 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff3.setVisible(!buttonOff3.isVisible());
					setToggled("a3");
				}
			return true;
					}
		};
		//Button 4 - "a4"
		final Sprite buttonOff4 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						return true;
					}
		};
						
		final Sprite buttonOn4 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
							this.setVisible(!this.isVisible());
							buttonOff4.setVisible(!buttonOff4.isVisible());
							setToggled("a4");
				}
			return true;
			}
		};
		
		
		// -- COMMUNICATIONS BUTTONS --
		final Sprite buttonOff5 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				return true;
			}
		};
		
		final Sprite buttonOn5 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff5.setVisible(!buttonOff5.isVisible());
					setToggled("c1");
				}
				return true;
			}
		};
		//Button 6 - "c2"
		final Sprite buttonOff6 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				return true;
			}
		};
		
		final Sprite buttonOn6 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff6.setVisible(!buttonOff6.isVisible());
					setToggled("c2");
				}
				return true;
			}
		};
		//Button 7 - "c3"
		final Sprite buttonOff7 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						return true;
					}
		};
				
		final Sprite buttonOn7 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff7.setVisible(!buttonOff7.isVisible());
					setToggled("c3");
				}
			return true;
					}
		};
		//Button 8 - "c4"
		final Sprite buttonOff8 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						return true;
					}
		};
						
		final Sprite buttonOn8 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
							this.setVisible(!this.isVisible());
							buttonOff8.setVisible(!buttonOff8.isVisible());
							setToggled("c4");
				}
			return true;
			}
		};
		
		// -- KITCHEN --
		final Sprite buttonOff9 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				return true;
			}
		};
		
		final Sprite buttonOn9 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff9.setVisible(!buttonOff9.isVisible());
					setToggled("k1");
				}
				return true;
			}
		};
		//Button 10 - "k2"
		final Sprite buttonOff10 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				return true;
			}
		};
		
		final Sprite buttonOn10 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff10.setVisible(!buttonOff10.isVisible());
					setToggled("k2");
				}
				return true;
			}
		};
		//Button 11 - "k3"
		final Sprite buttonOff11 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						return true;
					}
		};
				
		final Sprite buttonOn11 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff11.setVisible(!buttonOff11.isVisible());
					setToggled("k3");
				}
			return true;
					}
		};
		//Button 12 - "k4"
		final Sprite buttonOff12 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						return true;
					}
		};
						
		final Sprite buttonOn12 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
							this.setVisible(!this.isVisible());
							buttonOff12.setVisible(!buttonOff12.isVisible());
							setToggled("k4");
				}
			return true;
			}
		};
		
		// -- NAVIGATION --
		final Sprite buttonOff13 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				return true;
			}
		};
		
		final Sprite buttonOn13 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff13.setVisible(!buttonOff13.isVisible());
					setToggled("n1");
				}
				return true;
			}
		};
		//Button 14 - "n2"
		final Sprite buttonOff14 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				return true;
			}
		};
		
		final Sprite buttonOn14 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff14.setVisible(!buttonOff14.isVisible());
					setToggled("n2");
				}
				return true;
			}
		};
		//Button 15 - "n3"
		final Sprite buttonOff15 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						return true;
					}
		};
				
		final Sprite buttonOn15 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					this.setVisible(!this.isVisible());
					buttonOff15.setVisible(!buttonOff15.isVisible());
					setToggled("n3");
				}
			return true;
					}
		};
		//Button 16 - "n4"
		final Sprite buttonOff16 = new Sprite(0, 0, buttonOffTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						return true;
					}
		};
						
		final Sprite buttonOn16 = new Sprite(0, 0, buttonOnTexture, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
							this.setVisible(!this.isVisible());
							buttonOff16.setVisible(!buttonOff16.isVisible());
							setToggled("n4");
				}
			return true;
			}
		};
		
		//Buttons end
		
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
				//Button 1
				buttonOn1.setPosition(72, 40 + 380);
				buttonOff1.setPosition(72, 40 + 380);
				//Button 2
				buttonOn2.setPosition(300, 40 + 380);
				buttonOff2.setPosition(300, 40 + 380);
				//Button 3
				buttonOn3.setPosition(72, 40 + 550);
				buttonOff3.setPosition(72, 40 + 550);
				//Button 4
				buttonOn4.setPosition(300, 40 + 550);
				buttonOff4.setPosition(300, 40 + 550);
				
				//Hide all others
				buttonOn5.setPosition(2000, 40 + 380);
				buttonOff5.setPosition(2000, 40 + 380);
				//Button 6
				buttonOn6.setPosition(2000, 40 + 380);
				buttonOff6.setPosition(2000, 40 + 380);
				//Button7
				buttonOn7.setPosition(2000, 40 + 550);
				buttonOff7.setPosition(2000, 40 + 550);
				//Button 8
				buttonOn8.setPosition(2000, 40 + 550);
				buttonOff8.setPosition(2000, 40 + 550);
				
				buttonOn9.setPosition(2000, 40 + 380);
				buttonOff9.setPosition(2000, 40 + 380);
				//Button 10
				buttonOn10.setPosition(2000, 40 + 380);
				buttonOff10.setPosition(2000, 40 + 380);
				//Button11
				buttonOn11.setPosition(2000, 40 + 550);
				buttonOff11.setPosition(2000, 40 + 550);
				//Button 12
				buttonOn12.setPosition(2000, 40 + 550);
				buttonOff12.setPosition(2000, 40 + 550);
				//
				buttonOn13.setPosition(2000, 40 + 550);
				buttonOff13.setPosition(2000, 40 + 550);
				
				buttonOn14.setPosition(2000, 40 + 380);
				buttonOff14.setPosition(2000, 40 + 380);
				//Button 15
				buttonOn15.setPosition(2000, 40 + 380);
				buttonOff15.setPosition(2000, 40 + 380);
				//Button16
				buttonOn16.setPosition(2000, 40 + 550);
				buttonOff16.setPosition(2000, 40 + 550);
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
				
				//Hide all the other buttons
				//Button 1
				buttonOn1.setPosition(2000, 40 + 380);
				buttonOff1.setPosition(2000, 40 + 380);
				//Button 2
				buttonOn2.setPosition(2000, 40 + 380);
				buttonOff2.setPosition(2000, 40 + 380);
				//Button 3
				buttonOn3.setPosition(2000, 40 + 550);
				buttonOff3.setPosition(2000, 40 + 550);
				//Button 4
				buttonOn4.setPosition(2000, 40 + 550);
				buttonOff4.setPosition(2000, 40 + 550);
				
				//Hide all others
				buttonOn5.setPosition(72, 40 + 380);
				buttonOff5.setPosition(72, 40 + 380);
				//Button 6
				buttonOn6.setPosition(300, 40 + 380);
				buttonOff6.setPosition(300, 40 + 380);
				//Button7
				buttonOn7.setPosition(72, 40 + 550);
				buttonOff7.setPosition(72, 40 + 550);
				//Button 8
				buttonOn8.setPosition(300, 40 + 550);
				buttonOff8.setPosition(300, 40 + 550);
				
				
				buttonOn9.setPosition(2000, 40 + 380);
				buttonOff9.setPosition(2000, 40 + 380);
				//Button 10
				buttonOn10.setPosition(2000, 40 + 380);
				buttonOff10.setPosition(2000, 40 + 380);
				//Button11
				buttonOn11.setPosition(2000, 40 + 550);
				buttonOff11.setPosition(2000, 40 + 550);
				//Button 12
				buttonOn12.setPosition(2000, 40 + 550);
				buttonOff12.setPosition(2000, 40 + 550);
				//
				buttonOn13.setPosition(2000, 40 + 550);
				buttonOff13.setPosition(2000, 40 + 550);
				
				buttonOn14.setPosition(2000, 40 + 380);
				buttonOff14.setPosition(2000, 40 + 380);
				//Button 15
				buttonOn15.setPosition(2000, 40 + 380);
				buttonOff15.setPosition(2000, 40 + 380);
				//Button16
				buttonOn16.setPosition(2000, 40 + 550);
				buttonOff16.setPosition(2000, 40 + 550);
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
				
				//Button 1
				buttonOn1.setPosition(2000, 40 + 380);
				buttonOff1.setPosition(2000, 40 + 380);
				//Button 2
				buttonOn2.setPosition(2000, 40 + 380);
				buttonOff2.setPosition(2000, 40 + 380);
				//Button 3
				buttonOn3.setPosition(2000, 40 + 550);
				buttonOff3.setPosition(2000, 40 + 550);
				//Button 4
				buttonOn4.setPosition(2000, 40 + 550);
				buttonOff4.setPosition(2000, 40 + 550);
				
				//Hide all others
				buttonOn5.setPosition(2000, 40 + 380);
				buttonOff5.setPosition(2000, 40 + 380);
				//Button 6
				buttonOn6.setPosition(2000, 40 + 380);
				buttonOff6.setPosition(2000, 40 + 380);
				//Button7
				buttonOn7.setPosition(2000, 40 + 550);
				buttonOff7.setPosition(2000, 40 + 550);
				//Button 8
				buttonOn8.setPosition(2000, 40 + 550);
				buttonOff8.setPosition(2000, 40 + 550);
				
				//////
				buttonOn9.setPosition(72, 40 + 380);
				buttonOff9.setPosition(72, 40 + 380);
				//Button 10
				buttonOn10.setPosition(300, 40 + 380);
				buttonOff10.setPosition(300, 40 + 380);
				//Button11
				buttonOn11.setPosition(72, 40 + 550);
				buttonOff11.setPosition(72, 40 + 550);
				//Button 12
				buttonOn12.setPosition(300, 40 + 550);
				buttonOff12.setPosition(300, 40 + 550);
				///////
				
				buttonOn13.setPosition(2000, 40 + 550);
				buttonOff13.setPosition(2000, 40 + 550);
				
				buttonOn14.setPosition(2000, 40 + 380);
				buttonOff14.setPosition(2000, 40 + 380);
				//Button 15
				buttonOn15.setPosition(2000, 40 + 380);
				buttonOff15.setPosition(2000, 40 + 380);
				//Button16
				buttonOn16.setPosition(2000, 40 + 550);
				buttonOff16.setPosition(2000, 40 + 550);
				
				
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
				
				//Button 1
				buttonOn1.setPosition(2000, 40 + 380);
				buttonOff1.setPosition(2000, 40 + 380);
				//Button 2
				buttonOn2.setPosition(2000, 40 + 380);
				buttonOff2.setPosition(2000, 40 + 380);
				//Button 3
				buttonOn3.setPosition(2000, 40 + 550);
				buttonOff3.setPosition(2000, 40 + 550);
				//Button 4
				buttonOn4.setPosition(2000, 40 + 550);
				buttonOff4.setPosition(2000, 40 + 550);
				
				//Hide all others
				buttonOn5.setPosition(2000, 40 + 380);
				buttonOff5.setPosition(2000, 40 + 380);
				//Button 6
				buttonOn6.setPosition(2000, 40 + 380);
				buttonOff6.setPosition(2000, 40 + 380);
				//Button7
				buttonOn7.setPosition(2000, 40 + 550);
				buttonOff7.setPosition(2000, 40 + 550);
				//Button 8
				buttonOn8.setPosition(2000, 40 + 550);
				buttonOff8.setPosition(2000, 40 + 550);
				
				//////
				buttonOn9.setPosition(2000, 40 + 380);
				buttonOff9.setPosition(2000, 40 + 380);
				//Button 10
				buttonOn10.setPosition(2000, 40 + 380);
				buttonOff10.setPosition(2000, 40 + 380);
				//Button11
				buttonOn11.setPosition(2000, 40 + 550);
				buttonOff11.setPosition(2000, 40 + 550);
				//Button 12
				buttonOn12.setPosition(2000, 40 + 550);
				buttonOff12.setPosition(2000, 40 + 550);
				///////
				
				buttonOn13.setPosition(72, 40 + 380);
				buttonOff13.setPosition(72, 40 + 380);
				
				buttonOn14.setPosition(300, 40 + 380);
				buttonOff14.setPosition(300, 40 + 380);
				//Button 15
				buttonOn15.setPosition(72, 40 + 550);
				buttonOff15.setPosition(72, 40 + 550);
				//Button16
				buttonOn16.setPosition(300, 40 + 550);
				buttonOff16.setPosition(300, 40 + 550);
				
				
				
				
				return true;
			}
		};
		
		//Button example
		
		//
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
		
		//
		//button1Text.setWidth(100);
		//Buttons tags
		//buttonOn1.setTag(10);
		buttonOff1.setVisible(false);
		//buttonOff1.setTag(11);
		
		//buttonOn2.setTag(12);
		buttonOff2.setVisible(false);
		//buttonOff2.setTag(13);
		
		//buttonOn3.setTag(14);
		buttonOff3.setVisible(false);
		//buttonOff3.setTag(15);
		
		//buttonOn4.setTag(16);
		buttonOff4.setVisible(false);
		//buttonOff4.setTag(17);
		
		buttonOff5.setVisible(false);
		buttonOff6.setVisible(false);
		buttonOff7.setVisible(false);
		buttonOff8.setVisible(false);
		
		buttonOff9.setVisible(false);
		buttonOff10.setVisible(false);
		buttonOff11.setVisible(false);
		buttonOff12.setVisible(false);
		
		buttonOff13.setVisible(false);
		buttonOff14.setVisible(false);
		buttonOff15.setVisible(false);
		buttonOff16.setVisible(false);
		
		armory.setPosition(0, 0);
		communication.setPosition(240, 0);
		kitchen.setPosition(0, 120);
		navigation.setPosition(240, 120);
		
		buttonsPanel.setPosition(0,380);
		//Buttons positions - initials. Only from 1 to 4 are shown at the start, everything else is anywhere
		//Button 1
		buttonOn1.setPosition(72, 40 + 380);
		buttonOff1.setPosition(72, 40 + 380);
		//Button 2
		buttonOn2.setPosition(300, 40 + 380);
		buttonOff2.setPosition(300, 40 + 380);
		//Button 3
		buttonOn3.setPosition(72, 40 + 550);
		buttonOff3.setPosition(72, 40 + 550);
		//Button 4
		buttonOn4.setPosition(300, 40 + 550);
		buttonOff4.setPosition(300, 40 + 550);
		
		//Hide all others
		buttonOn5.setPosition(2000, 40 + 380);
		buttonOff5.setPosition(2000, 40 + 380);
		//Button 6
		buttonOn6.setPosition(2000, 40 + 380);
		buttonOff6.setPosition(2000, 40 + 380);
		//Button7
		buttonOn7.setPosition(2000, 40 + 550);
		buttonOff7.setPosition(2000, 40 + 550);
		//Button 8
		buttonOn8.setPosition(2000, 40 + 550);
		buttonOff8.setPosition(2000, 40 + 550);
		
		buttonOn9.setPosition(2000, 40 + 380);
		buttonOff9.setPosition(2000, 40 + 380);
		//Button 10
		buttonOn10.setPosition(2000, 40 + 380);
		buttonOff10.setPosition(2000, 40 + 380);
		//Button11
		buttonOn11.setPosition(2000, 40 + 550);
		buttonOff11.setPosition(2000, 40 + 550);
		//Button 12
		buttonOn12.setPosition(2000, 40 + 550);
		buttonOff12.setPosition(2000, 40 + 550);
		//
		buttonOn13.setPosition(2000, 40 + 550);
		buttonOff13.setPosition(2000, 40 + 550);
		
		buttonOn14.setPosition(2000, 40 + 380);
		buttonOff14.setPosition(2000, 40 + 380);
		//Button 15
		buttonOn15.setPosition(2000, 40 + 380);
		buttonOff15.setPosition(2000, 40 + 380);
		//Button16
		buttonOn16.setPosition(2000, 40 + 550);
		buttonOff16.setPosition(2000, 40 + 550);
		
		
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
		//Button 1
		mainGameScene.registerTouchArea(buttonOn1);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn1);

		mainGameScene.registerTouchArea(buttonOff1);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff1);
		//Button 2
		mainGameScene.registerTouchArea(buttonOn2);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn2);
		
		mainGameScene.registerTouchArea(buttonOff2);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff2);
		//Button 3
		mainGameScene.registerTouchArea(buttonOn3);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn3);
				
		mainGameScene.registerTouchArea(buttonOff3);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff3);
		//Button 4
		mainGameScene.registerTouchArea(buttonOn4);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn4);
						
		mainGameScene.registerTouchArea(buttonOff4);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff4);
		
		//Comm
		//Button 5
		mainGameScene.registerTouchArea(buttonOn5);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn5);

		mainGameScene.registerTouchArea(buttonOff5);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff5);
		//Button 6
		mainGameScene.registerTouchArea(buttonOn6);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn6);
				
		mainGameScene.registerTouchArea(buttonOff6);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff6);
		//Button 7
		mainGameScene.registerTouchArea(buttonOn7);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn7);
						
		mainGameScene.registerTouchArea(buttonOff7);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff7);
		//Button 8
		mainGameScene.registerTouchArea(buttonOn8);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn8);
								
		mainGameScene.registerTouchArea(buttonOff8);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff8);
		
		//Kitchen
		//Button 9
		mainGameScene.registerTouchArea(buttonOn9);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn9);

		mainGameScene.registerTouchArea(buttonOff9);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff9);
		//Button 10
		mainGameScene.registerTouchArea(buttonOn10);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn10);
						
		mainGameScene.registerTouchArea(buttonOff10);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff10);
		//Button 11 
		mainGameScene.registerTouchArea(buttonOn11);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn11);
								
		mainGameScene.registerTouchArea(buttonOff11);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff11);
		//Button 12
		mainGameScene.registerTouchArea(buttonOn12);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn12);
										
		mainGameScene.registerTouchArea(buttonOff12);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff12);
		
		//Navigation
		//Button 13
		mainGameScene.registerTouchArea(buttonOn13);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn13);

		mainGameScene.registerTouchArea(buttonOff13);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff13);
		//Button 14
		mainGameScene.registerTouchArea(buttonOn14);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn14);
						
		mainGameScene.registerTouchArea(buttonOff14);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff14);
		//Button 15
		mainGameScene.registerTouchArea(buttonOn15);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn15);
								
		mainGameScene.registerTouchArea(buttonOff15);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff15);
		//Button 16
		mainGameScene.registerTouchArea(buttonOn16);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOn16);
										
		mainGameScene.registerTouchArea(buttonOff16);
		mainGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mainGameScene.attachChild(buttonOff16);
		
		//Print the texts and the background
		//We create the rectangles
		final Rectangle orderRectangle = this.makeColoredRectangle(0,300,1, 1, 1, this.vbom, (int)this.camera.getWidth(), 80);
		
		final Rectangle button1Rectangle = this.makeColoredRectangle(30, 40 + 350,1, 1, 1, this.vbom, 180, 30);
		final Rectangle button2Rectangle = this.makeColoredRectangle(270, 40+350,1, 1, 1, this.vbom, 180, 30);
		final Rectangle button3Rectangle = this.makeColoredRectangle(25, 40+520,1, 1, 1, this.vbom, 220, 30);
		final Rectangle button4Rectangle = this.makeColoredRectangle(270, 40+520,1, 1, 1, this.vbom, 180, 30);
		
		//Orders rectangle
		mainGameScene.attachChild(orderRectangle);
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
		
		gt = new GameThread(mainGameScene, gm, orderRectangle);
		gt.start();
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
			//Loop goes here
			/*while(true)
			{
				button1Text.setText(button1.buttonText);
			}*/			
			break;
			//button1Text.setText(button1.buttonText); 180, 30
		}
	}
	//Rectangle drawing
	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue, VertexBufferObjectManager vbom, int width, int height) {
		final Rectangle coloredRect = new Rectangle(pX, pY, width, height, vbom);
		coloredRect.setColor(pRed, pGreen, pBlue);
		return coloredRect;
	}
	
	/*@Override
	public void onUpdate()
	{
		
	}*/
	
	public void setToggled(String buttonIdentifier)
	{
		//this.button1Text.setText(this.button1Text.getText() + "a");
		this.gm.pressButton(buttonIdentifier);
	}


}

