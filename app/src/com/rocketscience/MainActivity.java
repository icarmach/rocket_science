package com.rocketscience;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class MainActivity extends SimpleBaseGameActivity {

	private Camera camera;
	private static final int CAMERA_WIDTH = 1080;
	private static final int CAMERA_HEIGHT = 1920;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private BitmapTextureAtlas mBitmapTextureAtlas2;
	private BitmapTextureAtlas mBitmapTextureAtlas3;
	private BitmapTextureAtlas mBitmapTextureAtlas4;
	private BitmapTextureAtlas mBitmapTextureAtlas5;
	private ITextureRegion commandModuleTexture;
	private ITextureRegion fuelTankTexture;
	private ITextureRegion solidBoosterTexture;
	private ITextureRegion liquidFuelEngineTexture;
	private ITextureRegion stackDecouplerTexture;
	
	@Override
    public EngineOptions onCreateEngineOptions()
    {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    protected void onCreateResources()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		this.commandModuleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "commandModule.png", 0, 0);
		this.mBitmapTextureAtlas.load();
		
		this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		this.fuelTankTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "fuelTank.png", 0, 0);
		this.mBitmapTextureAtlas2.load();
		
		this.mBitmapTextureAtlas3 = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		this.solidBoosterTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas3, this, "solidBooster.png", 0, 0);
		this.mBitmapTextureAtlas3.load();
	
		this.mBitmapTextureAtlas4 = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		this.liquidFuelEngineTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas4, this, "liquidFuelEngine.png", 0, 0);
		this.mBitmapTextureAtlas4.load();
		
		this.mBitmapTextureAtlas5 = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		this.stackDecouplerTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas5, this, "stackDecoupler.png", 0, 0);
		this.mBitmapTextureAtlas5.load();
    }

    @Override
    protected Scene onCreateScene()
    {
    	this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.setBackground(new Background(0.0f, 0.0f, 0.0f));
		
		final Sprite commandModule = new Sprite(100, 100, this.commandModuleTexture, this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
				return true;
			}
		};
		
		final Sprite fuelTank = new Sprite(300, 100, this.fuelTankTexture, this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
				return true;
			}
		};
		
		final Sprite solidBooster = new Sprite(500, 100, this.solidBoosterTexture, this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
				return true;
			}
		};
		
		final Sprite liquidFuelEngine = new Sprite(700, 100, this.liquidFuelEngineTexture, this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
				return true;
			}
		};
		
		final Sprite stackDecoupler = new Sprite(900, 100, this.stackDecouplerTexture, this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
				return true;
			}
		};
		
		
		commandModule.setScale(4);
		scene.attachChild(commandModule);
		scene.registerTouchArea(commandModule);
		
		fuelTank.setScale(4);
		scene.attachChild(fuelTank);
		scene.registerTouchArea(fuelTank);
		
		solidBooster.setScale(4);
		scene.attachChild(solidBooster);
		scene.registerTouchArea(solidBooster);
		
		liquidFuelEngine.setScale(4);
		scene.attachChild(liquidFuelEngine);
		scene.registerTouchArea(liquidFuelEngine);
		
		stackDecoupler.setScale(4);
		scene.attachChild(stackDecoupler);
		scene.registerTouchArea(stackDecoupler);
		
		scene.setTouchAreaBindingOnActionDownEnabled(true);

		return scene;
    }

}
