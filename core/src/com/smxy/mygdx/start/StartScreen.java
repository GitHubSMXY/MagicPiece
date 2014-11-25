package com.smxy.mygdx.start;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.smxy.mygdx.com.ActorObject;
import com.smxy.mygdx.com.Assets;
import com.smxy.mygdx.manager.MyScreenManager;

public class StartScreen implements Screen {
	public static StartScreen instance = null;
	public static StartScreen getInstance() {
		if (instance == null) {
			instance = new StartScreen();
		}
		return instance;
	}
	private Stage mStage;
	private ActorObject mLogo;
	private TitleGroup mTitleGroup;
	private ActorObject mBG;
	private BarGroup mBarGroup;
	private ActorObject mStartBtn;
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1, 1, 1, 1);	
		
		mStage.act(delta);
		mStage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		mStage = new Stage(new ScalingViewport(Scaling.stretch, Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT));
		
		mBG = new ActorObject(Assets.BG);
		mStage.addActor(mBG);
		
		mTitleGroup = new TitleGroup();
		mStage.addActor(mTitleGroup);
		
		mBarGroup = new BarGroup();
		mStage.addActor(mBarGroup);
	
		mLogo = new ActorObject(Assets.LOGO);
		mLogo.setAnchor(mLogo.getWidth() / 2,mLogo.getHeight() / 2);
		mLogo.setOrigin(mLogo.getWidth() / 2,mLogo.getHeight() / 2);
		mLogo.setPosition(Assets.SCREEN_WIDTH / 2, Assets.SCREEN_HEIGHT / 2);
		mStage.addActor(mLogo);
		
		mLogo.addAction(
				Actions.sequence(
						Actions.parallel(Actions.alpha(0, 0), Actions.scaleTo(0, 0, 0)),
						Actions.parallel(Actions.alpha(1, 1f, Interpolation.swingOut), Actions.scaleTo(1, 1, 1f, Interpolation.swingOut))
						)
				);
		
		mStartBtn = new ActorObject(Assets.START);
		mStartBtn.setAnchor(mStartBtn.getWidth() / 2,mStartBtn.getHeight() / 2);
		mStartBtn.setOrigin(mStartBtn.getWidth() / 2,mStartBtn.getHeight() / 2);
		mStartBtn.setPosition(100, 40);
		mStage.addActor(mStartBtn);
		
		Gdx.input.setInputProcessor(mStage);
		
		mStartBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				MyScreenManager.jumpScreen(MyScreenManager.SCREEN_GAME);
				return false;
			}
		});
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		if (mBarGroup != null) {
			mBarGroup = null;
		}
		if (mTitleGroup != null) {
			mTitleGroup = null;
		}
		if (mLogo != null) {
			mLogo = null;
		}
		if (mStage != null) {
			mStage.dispose();
			mStage = null;
		}
		instance = null;
	}

}
