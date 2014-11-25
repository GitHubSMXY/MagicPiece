package com.smxy.mygdx.start;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.smxy.mygdx.com.ActorObject;
import com.smxy.mygdx.com.Assets;

public class BarGroup extends Group {

	private ActorObject mLeftBtn;
	private ActorObject mRightBtn;
	private ActorObject mBarBG;
	private ActorObject mBar;
	
	private final int MIN_PROCESS = 3;
	private final int MAX_PROCESS = 10;
	
	private int mMaxProcess = 0;
	
	public BarGroup() {
		setBounds(0, 0, Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		
		 mMaxProcess = Assets.mCurPiece;
		
		mLeftBtn = new ActorObject(Assets.BTN_GREEN);
		mLeftBtn.setAnchor(mLeftBtn.getWidth() / 2, mLeftBtn.getHeight() / 2);
		mLeftBtn.setPosition(225, Assets.SCREEN_HEIGHT - 597);
		
		mRightBtn = new ActorObject(Assets.BTN_REG);
		mRightBtn.setAnchor(mRightBtn.getWidth() / 2, mRightBtn.getHeight() / 2);
		mRightBtn.setPosition(733, Assets.SCREEN_HEIGHT - 597);
		
		mBar = new ActorObject(Assets.BAR);
		mBar.setClip((mMaxProcess - 3) * 35,0,260,59);
		mBar.setAnchor(0, 0);
		mBar.setPosition(348, Assets.SCREEN_HEIGHT - 569);
		
		mBarBG = new ActorObject(Assets.BAR_BG);
		mBarBG.setAnchor(mBarBG.getWidth() / 2, mBarBG.getHeight() / 2);
		mBarBG.setPosition(Assets.SCREEN_WIDTH/2, Assets.SCREEN_HEIGHT - 599);
		
		addActor(mBar);
		addActor(mBarBG);
		addActor(mLeftBtn);
		addActor(mRightBtn);
		
		mLeftBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (mMaxProcess > MIN_PROCESS) {
					--mMaxProcess;
					updateBar(mMaxProcess);
					
					--Assets.mCurPiece;
				}
				return true;
			}
		});
		
		mRightBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (mMaxProcess < MAX_PROCESS) {
					++mMaxProcess;
					updateBar(mMaxProcess);
					++Assets.mCurPiece;
				}
				return true;
			}
		});
		updateBar(mMaxProcess);
	}
	
	private void updateBar(int num) {
		mBar.setClip((7 - (num - 3)) * 35,0,260,59);
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

	
	
}
