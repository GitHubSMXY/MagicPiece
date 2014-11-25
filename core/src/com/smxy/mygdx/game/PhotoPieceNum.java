package com.smxy.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.smxy.mygdx.com.Assets;

public class PhotoPieceNum extends Actor {

	private int mPosX;// 所在的横
	private int mPosY;// 所在的竖
	private int mNum;
	private Sprite mBarBG;
	
	private ShapeRenderer mShape;
	
	private float mX;
	private float mY;
	private int mColNum;
	private int mWidth;
	private int mHeight;
	

	/**
	 * 初始化一个图片
	 * @param colNum
	 * @param num
	 * @param x
	 * @param y
	 */
	public PhotoPieceNum(int colNum,int num, float x, float y) {
		mColNum = colNum;
		mNum = num;
		mX = x;
		mY = y;
		setPosition(mX, mY);
		
		mWidth = Assets.BAR_BG_1.getWidth()/colNum;
		mHeight = Assets.BAR_BG_1.getHeight()/colNum;
		
		Texture tmp = null;
		if (Assets.mCurPiece%3 == 0) {
			tmp = Assets.BAR_BG_1;
		} else if (Assets.mCurPiece%3 == 1) {
			tmp = Assets.BAR_BG_2;
		} else {
			tmp = Assets.BAR_BG_3;
		}
		mBarBG = new Sprite(
				tmp, 
				(mNum % mColNum)*mWidth,
				(mColNum - 1 - (mNum/mColNum))*mHeight, 
				mWidth, 
				mHeight);

		mShape = new ShapeRenderer();
		
		tmp = null;
	}

	@Override
	public void setSize(float width, float height) {
		// TODO Auto-generated method stub
		super.setSize(width, height);
		mBarBG.setSize(width, height);
	}

	/**
	 * 设置屏幕中的位置
	 * 
	 * @param x
	 * @param y
	 */
	public void setRealPosition(int x, int y) {

	}

	@Override
	public void setPosition(float x, float y) {
		mX = x;
		mY = y;
		super.setPosition(x, y);
	}

	public int getMarkX() {
		return mPosX;
	}

	public float getMarkY() {
		return mPosY;
	}

	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Color old = batch.getColor();
		batch.setColor(getColor());
		batch.draw(mBarBG, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 
				getScaleX(), getScaleY(), getRotation());
		batch.end();
		
		
		mShape.setProjectionMatrix(batch.getProjectionMatrix());
		mShape.begin(ShapeType.Line);
		mShape.setColor(1, 1, 1, old.a);
		mShape.rect(getX(), getY(),mBarBG.getWidth(), mBarBG.getHeight());
		mShape.end();
		batch.begin();
		batch.setColor(old);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public boolean isTouchMe(float x, float y) {
		if (x >= this.getX() && x <= this.getX() + this.getWidth()
				&& y >= this.getY() && y <= this.getY() + this.getHeight()) {
			return true;
		}
		return false;
	}

	public int getNum() {
		return mNum;
	}

	public void setNum(int num) {
		mNum = num;
		mBarBG.setRegion(
				(mNum % mColNum)*mWidth,
				(mColNum - 1 - (mNum/mColNum))*mHeight, 
				mWidth, 
				mHeight);
	}

}
