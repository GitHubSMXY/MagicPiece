package com.smxy.mygdx.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.smxy.mygdx.com.Assets;
import com.smxy.mygdx.game.GameScreen;

/**
 * 启动游戏，所有游戏界面都以这个启动
 * @author smxy
 *
 */
public class MagicPiece extends Game {
//	private MenuScreen mMenuScreen;
	private GameScreen mGameScreen;
	private Assets assets;
	private Music mBGMusic;
	public static Sound mBtnMusic = null;
	
	public static MagicPiece instance = null;
	public static MagicPiece getInstance() {
		if (instance == null) {
			instance = new MagicPiece();
		}
		return instance;
	}
	
	
	
	/**
	 * 初始化系统所需的参数
	 */
	private void initSys() {
		assets = new Assets();
		mBGMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/bg.mp3"));
		mBGMusic.setLooping(true);
		mBGMusic.play();
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		mBtnMusic = Gdx.audio.newSound(Gdx.files.internal("sound/Button7.mp3"));
	}
	
	@Override
	public void create () {
		instance = this;
		initSys();
		MyScreenManager.jumpScreen(MyScreenManager.SCREEN_START);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.dispose();
		if (assets != null) {
			assets = null;
		}
		if (mGameScreen != null) {
			mGameScreen.dispose();
			mGameScreen = null;
		}
		if (mBtnMusic != null) {
			mBtnMusic.dispose();
			mBtnMusic = null;
		}
		if (mBGMusic != null) {
			mBGMusic.dispose();
			mBGMusic = null;
		}
		instance = null;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	
}
