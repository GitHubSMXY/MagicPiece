package com.smxy.mygdx.manager;


import com.badlogic.gdx.Screen;
import com.smxy.mygdx.game.GameScreen;
import com.smxy.mygdx.start.StartScreen;

public class MyScreenManager {
	public static final int SCREEN_START = 0;
	public static final int SCREEN_GAME = 1;
	
	public static Screen getScreen(int screenID) {
		Screen mScreen = null;
		switch(screenID) {
		case SCREEN_START:
			mScreen = StartScreen.getInstance();
			break;
		case SCREEN_GAME:
			mScreen = GameScreen.getInstance();
			break;
		}
		return mScreen;
	}
	
	public static void jumpScreen(int screenID) {
		MagicPiece.getInstance().setScreen(getScreen(screenID));
	}
	
}
