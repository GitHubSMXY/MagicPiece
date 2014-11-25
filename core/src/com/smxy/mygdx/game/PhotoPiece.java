package com.smxy.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PhotoPiece extends Actor {
	
	private TextureRegion mPhotoPiece;
	
	public PhotoPiece(Texture photo) {
		mPhotoPiece = new TextureRegion(photo);
	}

	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(mPhotoPiece, getX(), getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(),
				getRotation());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

}
