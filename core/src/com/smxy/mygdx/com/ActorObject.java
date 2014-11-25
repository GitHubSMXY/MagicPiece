package com.smxy.mygdx.com;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorObject extends Actor{

	private TextureRegion mObjectTextureRegion;
	private Texture tmpTexture;
	//默认左底对齐
	private float anchorX = 0;
	private float anchorY = 0;
	
	public ActorObject(Texture texture) {
		tmpTexture = texture;
		mObjectTextureRegion = new TextureRegion(texture);
		setSize(mObjectTextureRegion.getRegionWidth(), mObjectTextureRegion.getRegionHeight());
	}
	
	public ActorObject(Texture texture,int srcX, int scrY, int width,int height) {
		mObjectTextureRegion = new TextureRegion(texture, srcX, scrY, width,height);
		setSize(mObjectTextureRegion.getRegionWidth(), mObjectTextureRegion.getRegionHeight());
	}

	public void setClip(int x, int y, int width,int height) {
		mObjectTextureRegion = new TextureRegion(tmpTexture, x, y, width,height);
		setSize(mObjectTextureRegion.getRegionWidth(), mObjectTextureRegion.getRegionHeight());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
//		clipBegin();
		batch.draw(mObjectTextureRegion, getX() , getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(),
				getRotation());
//		clipEnd();
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}
	
	//设置对齐的点
	//因坐标从左底开始算，进行转换
	/**
	 * 图片坐标从左顶开始算
	 * @param x
	 * @param y
	 */
	public void setAnchor(float x, float y) {
		anchorX = x;
		anchorY = this.getHeight() - y;
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x - anchorX, y - anchorY);
	}
}
