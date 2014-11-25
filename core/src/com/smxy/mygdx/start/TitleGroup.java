package com.smxy.mygdx.start;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.smxy.mygdx.com.ActorObject;
import com.smxy.mygdx.com.Assets;


public class TitleGroup extends Group {

	private ActorObject[] mTitleActor;
	private ActorObject mTitleBg;
	
	public TitleGroup() {
		setBounds(0, 0, Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		
		Assets.mCurPiece = 3;
		
		mTitleBg = new ActorObject(Assets.TITLE_BG);
		mTitleBg.setAnchor(mTitleBg.getWidth() / 2, mTitleBg.getHeight() / 2);
		mTitleBg.setOrigin(mTitleBg.getWidth() / 2, mTitleBg.getHeight() / 2);
		mTitleBg.setPosition(Assets.SCREEN_WIDTH / 2, Assets.SCREEN_HEIGHT - 555);
		addActor(mTitleBg);
		
		mTitleActor = new ActorObject[5];
		for (int i = mTitleActor.length - 1; i >= 0; --i) {
			mTitleActor[i] = new ActorObject(Assets.TITLE,i * 70,0,70,88);
			mTitleActor[i].setAnchor(mTitleActor[i].getWidth() / 2,mTitleActor[i].getHeight() / 2);
			mTitleActor[i].setOrigin(35, 35);
			mTitleActor[i].setPosition(331 + i * 70, Assets.SCREEN_HEIGHT - 520);
			addActor(mTitleActor[i]);
		}
		
		mTitleActor[2].addAction(
				Actions.forever(
							Actions.sequence(
									Actions.moveBy(-70, 0,0.5f,Interpolation.exp5),
									Actions.delay(0.2f),
									Actions.moveBy(140,0,0.5f,Interpolation.exp5),
									Actions.delay(0.2f),
									Actions.moveBy(70,0,0.5f,Interpolation.exp5),
									Actions.delay(0.2f),
									Actions.moveBy(-140,0,0.5f,Interpolation.exp5),
									Actions.delay(0.2f)
									)
						)
				);
		mTitleActor[1].addAction(
				Actions.forever(
						Actions.sequence(
								Actions.moveBy(70, 0,0.5f,Interpolation.exp5),
								Actions.delay(0.2f),
								Actions.moveBy(-70,0,0.5f,Interpolation.exp5),
								Actions.delay(0.2f),
								Actions.delay(0.5f),
								Actions.delay(0.2f),
								Actions.delay(0.5f),
								Actions.delay(0.2f)
								)
						)
						
				);
		mTitleActor[3].addAction(
				Actions.forever(
						Actions.sequence(
								Actions.delay(0.5f),
								Actions.delay(0.2f),
								Actions.moveBy(-70,0,0.5f,Interpolation.exp5),
								Actions.delay(0.2f),
								Actions.delay(0.5f),
								Actions.delay(0.2f),
								Actions.moveBy(70,0,0.5f,Interpolation.exp5),
								Actions.delay(0.2f)
								)
						)
						
				);
		mTitleActor[4].addAction(
				Actions.forever(
						Actions.sequence(
								Actions.delay(0.5f),
								Actions.delay(0.2f),
								Actions.delay(0.5f),
								Actions.delay(0.2f),
								Actions.moveBy(-70,0,0.5f,Interpolation.exp5),
								Actions.delay(0.2f),
								Actions.moveBy(70,0,0.5f,Interpolation.exp5),
								Actions.delay(0.2f)
								)
						)
				);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
}
