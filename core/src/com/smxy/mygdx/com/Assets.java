package com.smxy.mygdx.com;

import com.badlogic.gdx.graphics.Texture;


/**
 * 
 * @author User
 *
 */
public class Assets {
	//TODO ��Ļ����
	public static int SCREEN_WIDTH = 960;
	public static int SCREEN_HEIGHT = 640;
	
	public static int STONE_TYPE = 2;
	
	//TODO ���������Դ����
	public static Texture BAR_BG_1;//�鱳��
	public static Texture BAR_BG_2;//�鱳��
	public static Texture BAR_BG_3;//�鱳��
	public static Texture LOGO;//�鱳��
	public static Texture TITLE;//����
	public static Texture TITLE_BG;//����
	public static Texture BG;//����
	public static Texture BAR;//�Ѷ���
	public static Texture BAR_BG;//�Ѷ�������
	public static Texture BTN_REG;//��ɫ��ť
	public static Texture BTN_GREEN;//��ɫ��ť
	public static Texture BTN_BACK;//��ɫ��ť
	public static Texture FINISH;//��ɫ��ť
	public static Texture START;//��ɫ��ť
	
	public static int mCurPiece = 3;
	
	
	/**
	 * ���е�����Ԥ���ڴ�����
	 */
	public Assets(){
		load();
	}

	public static void load() {
		BAR_BG_1 = new Texture("game/image/0.png");//�鱳��
		BAR_BG_2 = new Texture("game/image/1.png");//�鱳��
		BAR_BG_3 = new Texture("game/image/2.png");//�鱳��
		
		LOGO = new Texture("start/image/logo.png");//��Ϸ��logo
		TITLE = new Texture("start/image/title.png");//��Ϸ��logo
		TITLE_BG = new Texture("start/image/titleBG.png");//��Ϸ��logo
		BG = new Texture("start/image/bg.png");//��Ϸ��logo
		BAR = new Texture("start/image/choseBar.png");
		BAR_BG = new Texture("start/image/choseBarBG.png");
		BTN_REG = new Texture("start/image/btnRED.png");
		BTN_GREEN = new Texture("start/image/btnGREEN.png");
		BTN_BACK = new Texture("start/image/back.png");
		FINISH = new Texture("game/image/finish.png");
		START = new Texture("start/image/start.png");
	}
	
	public static void dispose() {
		//TODO ���������Դ����
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
