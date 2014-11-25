package com.smxy.mygdx.com;

import com.badlogic.gdx.graphics.Texture;


/**
 * 
 * @author User
 *
 */
public class Assets {
	//TODO 屏幕参数
	public static int SCREEN_WIDTH = 960;
	public static int SCREEN_HEIGHT = 640;
	
	public static int STONE_TYPE = 2;
	
	//TODO 存放所有资源加载
	public static Texture BAR_BG_1;//块背景
	public static Texture BAR_BG_2;//块背景
	public static Texture BAR_BG_3;//块背景
	public static Texture LOGO;//块背景
	public static Texture TITLE;//标题
	public static Texture TITLE_BG;//标题
	public static Texture BG;//标题
	public static Texture BAR;//难度条
	public static Texture BAR_BG;//难度条背景
	public static Texture BTN_REG;//红色按钮
	public static Texture BTN_GREEN;//绿色按钮
	public static Texture BTN_BACK;//绿色按钮
	public static Texture FINISH;//绿色按钮
	public static Texture START;//绿色按钮
	
	public static int mCurPiece = 3;
	
	
	/**
	 * 所有的纹理预先在此生成
	 */
	public Assets(){
		load();
	}

	public static void load() {
		BAR_BG_1 = new Texture("game/image/0.png");//块背景
		BAR_BG_2 = new Texture("game/image/1.png");//块背景
		BAR_BG_3 = new Texture("game/image/2.png");//块背景
		
		LOGO = new Texture("start/image/logo.png");//游戏的logo
		TITLE = new Texture("start/image/title.png");//游戏的logo
		TITLE_BG = new Texture("start/image/titleBG.png");//游戏的logo
		BG = new Texture("start/image/bg.png");//游戏的logo
		BAR = new Texture("start/image/choseBar.png");
		BAR_BG = new Texture("start/image/choseBarBG.png");
		BTN_REG = new Texture("start/image/btnRED.png");
		BTN_GREEN = new Texture("start/image/btnGREEN.png");
		BTN_BACK = new Texture("start/image/back.png");
		FINISH = new Texture("game/image/finish.png");
		START = new Texture("start/image/start.png");
	}
	
	public static void dispose() {
		//TODO 存放所有资源加载
//		BG.dispose();
//		M_BODY.dispose();
//		M_BACK.dispose();
//		M_HEAD.dispose();
//		M_NAIL.dispose();
//		GAME_BG.dispose();
//		GAME_BREAD.dispose();
//		NUM_BAR_BG.
//		mPlayerFrame1.dispose();
//		mPlayerFrame2.dispose();
//		
//		MENU_TITLE.dispose();
//		MENU_ROLE.dispose();
//		MENU_BUTTON.dispose();
//		
//		GAME_HAND_BLUE.dispose();
//		GAME_HAND_GREEN.dispose();
//		GAME_HAND_PURPLE.dispose();
//		GAME_HAND_YELLOW.dispose();
//		
//		GAME_STONE_1.dispose();
//		GAME_STONE_2.dispose();
		if (START != null) {
			START.dispose();
			START = null;
		}
		if (FINISH != null) {
			FINISH.dispose();
			FINISH = null;
		}
		if (BTN_BACK != null) {
			BTN_BACK.dispose();
			BTN_BACK = null;
		}
		if (BAR != null) {
			BAR.dispose();
			BAR = null;
		}
		if (BAR_BG != null) {
			BAR_BG.dispose();
			BAR_BG = null;
		}
		if (BTN_REG != null) {
			BTN_REG.dispose();
			BTN_REG = null;
		}
		if (BTN_GREEN != null) {
			BTN_GREEN.dispose();
			BTN_GREEN = null;
		}
		if (BG != null) {
			BG.dispose();
			BG = null;
		}
		if (LOGO != null) {
			LOGO.dispose();
			LOGO = null;
		}
		if (TITLE_BG != null) {
			TITLE_BG.dispose();
			TITLE_BG = null;
		}
		if (TITLE != null) {
			TITLE.dispose();
			TITLE = null;
		}
		
		if (BAR_BG_1 != null) {
			BAR_BG_1.dispose();
			BAR_BG_1 = null;
		}
		if (BAR_BG_2 != null) {
			BAR_BG_2.dispose();
			BAR_BG_2 = null;
		}
		if (BAR_BG_3 != null) {
			BAR_BG_3.dispose();
			BAR_BG_3 = null;
		}
		
	}
	
	//dispose
}
