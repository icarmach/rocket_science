package iic3686.rocketscience;

import iic3686.rocketscience.SceneManager.SceneType;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.Typeface;


public class MainActivity extends BaseGameActivity {
	private final int CAMERA_WIDTH = 480;
	private final int CAMERA_HEIGHT = 720;
	private Camera mCamera;
	private SceneManager sceneManager;
	private FontManager fm;
	private VertexBufferObjectManager vbom;
	private Font mFont;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), mCamera);
		return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {

		sceneManager = new SceneManager(this, mEngine, mCamera);
		sceneManager.loadSplashSceneResources();
		
		fm = this.getFontManager();
		vbom = this.getVertexBufferObjectManager();
		//Create the font
		this.mFont = FontFactory.create(fm, this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD),25);
		this.mFont.load();
		
		//
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}


	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		pOnCreateSceneCallback.onCreateSceneFinished(sceneManager.createSplashScene());
	}
	
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback()
		{
			public void onTimePassed(final TimerHandler pTimerHandler)
			{
				mEngine.unregisterUpdateHandler(pTimerHandler);
				sceneManager.loadGameSceneResources(mFont, vbom);
				
				sceneManager.createGameScenes();
				sceneManager.setCurrentScene(SceneType.TITLE);
			}
		}));
			pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	
}

