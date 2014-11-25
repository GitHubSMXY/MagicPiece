package com.smxy.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.smxy.mygdx.com.ActorObject;
import com.smxy.mygdx.com.Assets;
import com.smxy.mygdx.manager.MyScreenManager;

public class GameScreen implements Screen{

	public static GameScreen instance = null;
	public static GameScreen getInstance() {
		if (instance == null) {
			instance = new GameScreen();
		}
		return instance;
	}
	
	private Stage mGameStage;
	private PhotoGroup mPhotoGroup;
	private Actor mIcon;
	private ActorObject mBackBtn;
	private ActorObject mBG;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1, 1, 1, 1);	
		
		mGameStage.act(delta);
		mGameStage.draw();
		
//		mGameStage.act(delta);
//		mGameStage.draw();
//		
//		batch.begin();
//		mSmokeAnim.draw(batch, Gdx.graphics.getDeltaTime());
//		batch.end();
	}


	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		//设置虚拟世界跟显示的比例
		mGameStage = new Stage(new ScalingViewport(Scaling.stretch, Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT));
		
		mBG = new ActorObject(Assets.BG);
		mGameStage.addActor(mBG);
		
		mPhotoGroup = new PhotoGroup(Assets.mCurPiece);
		mGameStage.addActor(mPhotoGroup);
		
		if (Assets.mCurPiece%3 == 0) {
			mIcon = new PhotoPiece(Assets.BAR_BG_1);
		} else if (Assets.mCurPiece%3 == 1) {
			mIcon = new PhotoPiece(Assets.BAR_BG_2);
		} else {
			mIcon = new PhotoPiece(Assets.BAR_BG_3);
		}
		
		
		mIcon.setSize(Assets.SCREEN_WIDTH / 5, Assets.SCREEN_WIDTH / 5);
		mIcon.setPosition(Assets.SCREEN_WIDTH - mIcon.getWidth(), Assets.SCREEN_HEIGHT - mIcon.getHeight());
		mGameStage.addActor(mIcon);
		
		
		mBackBtn = new ActorObject(Assets.BTN_BACK);
		mBackBtn.setAnchor(mBackBtn.getWidth(), mBackBtn.getHeight());
		mBackBtn.setPosition(Assets.SCREEN_WIDTH, 0);
		mGameStage.addActor(mBackBtn);
		
		Gdx.input.setInputProcessor(mGameStage);
		
		mBackBtn.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				dispose();
				MyScreenManager.jumpScreen(MyScreenManager.SCREEN_START);
				return true;
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
		instance = null;
		if (mBackBtn != null) {
			mBackBtn = null;
		}
		if (mIcon != null) {
			mIcon.clear();
			mIcon = null;
		}
		if (mGameStage != null) {
			mGameStage.dispose();
			mGameStage = null;
		}
		if (mPhotoGroup != null) {
			mPhotoGroup.clear();
			mPhotoGroup = null;
		}
	}
}
