package com.smxy.mygdx.game;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.smxy.mygdx.com.ActorObject;
import com.smxy.mygdx.com.Assets;
import com.smxy.mygdx.manager.MagicPiece;
import com.smxy.mygdx.manager.MyScreenManager;

public class PhotoGroup extends Group{
	
	private PhotoPieceNum[][] mPiece;
	private ActorObject mEnd;
	private int[] mTmpArray;
	
	private final int DISP_WIDTH = 480;
	
	private int mRow = 0;//行
	private int mCol = 0;//列
	
	private boolean mIsCatchBar = false;
	
	private float mOldCatchX = 0;
	private float mOldCatchY = 0;
	private float mNewCatchX = 0;
	private float mNewCatchY = 0;
	
	private float[] mTmpPos;//记录最开始多少个块的位置
	
	private PhotoPieceNum mfixPhotoPiece;//修补透明的
	private static int mColNum = 0;
	private float mPieceWidth = 0;
	
	private static boolean isMoveEnd = false;
	
	private final byte DIRECTION_NULL = 0x0;
	private final byte DIRECTION_U = 0x1;
	private final byte DIRECTION_D = 0x2;
	private final byte DIRECTION_L = 0x4;
	private final byte DIRECTION_R = 0x8;
	private final static float DURATION = 0.3f;
	
	private final static byte FADE_NULL = 0;
	private final static byte FADE_IN = 0x1;
	private final static byte FADE_OUT = 0x2;
	
	private static boolean isEndMoveAction = false;
	private boolean isCanTouchDragged = true;
	
	private byte mDirection = DIRECTION_NULL;
	
	//out of order position record;
	private float[][] positions;
	private float[] tmpPosition;
	
	public PhotoGroup(int num) {
		setBounds(0, 0, Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		isEndMoveAction = false;
		mColNum = num;
		mPiece = new PhotoPieceNum[num][num];
		
		mTmpPos = new float[num];
		
		//生成乱序数组
		mTmpArray = new int[num*num];
		for (int i = mTmpArray.length - 1; i >= 0;--i) {
			mTmpArray[i] = i;
		}
		
		positions = new float[mColNum][mColNum * 3];
		
		//排好位置
		for (int y = mColNum - 1; y >= 0; --y) {
			for (int x = mColNum - 1; x >= 0; --x) {
				mPiece[y][x] = new PhotoPieceNum(mColNum,
						mTmpArray[y* mColNum + x], 
						Assets.SCREEN_WIDTH/2 - (DISP_WIDTH/(mColNum*2))*(mColNum-2*x) - (Assets.SCREEN_WIDTH/2 - DISP_WIDTH/2) + DISP_WIDTH/mColNum, 
						Assets.SCREEN_HEIGHT/2 - (DISP_WIDTH/(mColNum*2))*(mColNum-2*y));
				mPiece[y][x].setSize(DISP_WIDTH/mColNum, DISP_WIDTH/mColNum);
				addActor(mPiece[y][x]);
				
				//record position
				positions[y][x * 3] = mPiece[y][x].getNum();
				positions[y][x * 3 + 1] = mPiece[y][x].getX();
				positions[y][x * 3 + 2] = mPiece[y][x].getY();
			}
		}
		
		mPieceWidth = mPiece[0][0].getWidth();
		mfixPhotoPiece = new PhotoPieceNum(mColNum,0, -2000, -2000);
		mfixPhotoPiece.setSize(DISP_WIDTH/mColNum, DISP_WIDTH/mColNum);
		addActor(mfixPhotoPiece);
		
		addToListener();
		
		outOfOrder();
		
		barMoveAction();
	}
	
	private boolean isTouch(float x, float y) {
		for (int row = mColNum - 1; row >= 0; --row) {
			for (int col = mColNum - 1; col >= 0; --col) {
				if (mPiece[row][col].isTouchMe(x, y)) {
					
					mRow = col;
					mCol = row;
					
					mOldCatchX = x;
					mOldCatchY = y;
					
					mIsCatchBar = true;
					return mIsCatchBar;
				}
			}
		}
		return mIsCatchBar;
	}
	
	/**
	 * init main listener
	 */
	private void addToListener() {
		addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x,
					float y, int pointer, int button) {
				
				if (!isEndMoveAction) {
					return false;
				}
				if (mDirection != DIRECTION_NULL) {
					return false;
				}
				//TODO 检测按中哪一个块
				//排好位置
				//因为图层是先生成在最上面
				isTouch(x, y);
				return super.touchDown(event, x, y, pointer, button);	
			}
			
			//拖拽
			@Override
			public void touchDragged(InputEvent event, float x,
					float y, int pointer) {
				// TODO Auto-generated method stub
				touchDraggedMethod1(event, x, y, pointer);
				
				super.touchDragged(event, x, y, pointer);
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (!isEndMoveAction) {
					return;
				}
				
				for (int i = mTmpPos.length - 1; i >= 0; --i) {
					mTmpPos[i] = 0.0f;
				}
				
				isCanTouchDragged = true;
				
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
	
	
	
	private void touchDraggedMethod2(InputEvent event, float x, float y, int pointer) {
		if (mIsCatchBar) {
			if (Math.abs(mOldCatchX - mNewCatchX) > Math.abs(mOldCatchY - mNewCatchY)) {
				// TODO 左右划
				if (mNewCatchX - mOldCatchX > 0) {
					//往右划（）
					mDirection = DIRECTION_R;
					
//					for (int i = mColNum - 1; i >= 0; --i) {
//						if (i == mColNum - 1) {
//							movePiece(mPiece[mCol][i], mPieceWidth, 0, FADE_OUT);
//						} else {
//							movePiece(mPiece[mCol][i], mPieceWidth, 0, FADE_NULL);
//						}
//					}
//					mfixPhotoPiece.setNum(mPiece[mCol][mColNum - 1].getNum());
//					mfixPhotoPiece.setPosition(mPiece[mCol][0].getX() - mPieceWidth, mPiece[mCol][0].getY());
//					movePiece(mfixPhotoPiece, mPieceWidth, 0, FADE_IN);
					
				} else if (mNewCatchX - mOldCatchX < 0 ) {
					mDirection = DIRECTION_L;
//					for (int i = mColNum - 1; i >= 0; --i) {
//						if (i == 0) {
//							movePiece(mPiece[mCol][i], -mPieceWidth, 0, FADE_OUT);
//						} else {
//							movePiece(mPiece[mCol][i], -mPieceWidth, 0, FADE_NULL);
//						}
//					}
//					mfixPhotoPiece.setNum(mPiece[mCol][0].getNum());
//					mfixPhotoPiece.setPosition(mPiece[mCol][mColNum - 1].getX() + mPieceWidth, mPiece[mCol][mColNum - 1].getY());
//					movePiece(mfixPhotoPiece, -mPieceWidth, 0, FADE_IN);
				}
			} else if (Math.abs(mOldCatchX - mNewCatchX) < Math.abs(mOldCatchY - mNewCatchY)) {
				// TODO 上下划
				if (mNewCatchY - mOldCatchY > 0) {
					mDirection = DIRECTION_U;
//					for (int i = mColNum - 1; i >= 0; --i) {
//						if (i == mColNum - 1) {
//							movePiece(mPiece[i][mRow], 0,mPieceWidth,  FADE_OUT);
//						} else {
//							movePiece(mPiece[i][mRow], 0,mPieceWidth, FADE_NULL);
//						}
//					}
//					mfixPhotoPiece.setNum(mPiece[mColNum - 1][mRow].getNum());
//					mfixPhotoPiece.setPosition(mPiece[0][mRow].getX(), mPiece[0][mRow].getY() - mPieceWidth);
//					movePiece(mfixPhotoPiece, 0, mPieceWidth, FADE_IN);
//					
				} else if (mNewCatchY - mOldCatchY < 0 ) {
					mDirection = DIRECTION_D;
//					for (int i = mColNum - 1; i >= 0; --i) {
//						if (i == 0) {
//							movePiece(mPiece[i][mRow], 0, -mPieceWidth,  FADE_OUT);
//						} else {
//							movePiece(mPiece[i][mRow], 0, -mPieceWidth, FADE_NULL);
//						}
//					}
//					mfixPhotoPiece.setNum(mPiece[0][mRow].getNum());
//					mfixPhotoPiece.setPosition(mPiece[mColNum - 1][mRow].getX(), mPiece[mColNum - 1][mRow].getY() + mPieceWidth);
//					movePiece(mfixPhotoPiece, 0, -mPieceWidth, FADE_IN);
				}
			}
			
			
			switch(mDirection) {
			case DIRECTION_R:
				System.out.println("右拐");
				if (x - mOldCatchX >= mPieceWidth) {
					//置换整行
					System.out.println("换位置了");
					swapPosition();
					//重新设定旧的捕捉点和新的捕捉点
					mNewCatchX = x;
					mOldCatchX = x;
					
					isTouch(x, y);
				}
				for (int i = mColNum - 1; i >= 0; --i) {
					mPiece[mCol][i].setPosition(x - (i - mRow) * mPieceWidth, mPiece[mCol][i].getY());
				}
				mfixPhotoPiece.setNum(mPiece[mCol][mColNum - 1].getNum());
				mfixPhotoPiece.setPosition(mPiece[mCol][0].getX() - mPieceWidth, mPiece[mCol][0].getY());
				
				break;
			case DIRECTION_L:
				
				break;
			case DIRECTION_U:
				
				break;
			case DIRECTION_D:
				
				break;
			}
			
			
//			if (mNewCatchX == 0) {
//				if (Math.abs(mOldCatchX - mNewCatchX) > 100
//						|| Math.abs(mOldCatchY - mNewCatchY) > 100) {
//					mNewCatchX = x;
//					mNewCatchY = y;
//				}
//			}
			MagicPiece.mBtnMusic.stop();
			MagicPiece.mBtnMusic.play();
		}
	}
	
	private void touchDraggedMethod1(InputEvent event, float x, float y, int pointer) {
		if (mDirection != DIRECTION_NULL) {
			return;
		}
		if (!isCanTouchDragged) {
			return;
		}
		if (!isEndMoveAction) {
			return;
		}
		if (mIsCatchBar) {
			if (mNewCatchX == 0) {
				if (Math.abs(mOldCatchX - mNewCatchX) > 100
						|| Math.abs(mOldCatchY - mNewCatchY) > 100) {
					mNewCatchX = x;
					mNewCatchY = y;
				}
			} else {
				if (Math.abs(mOldCatchX - mNewCatchX) > Math.abs(mOldCatchY - mNewCatchY)) {
					// TODO 左右划
					if (mNewCatchX - mOldCatchX > 0) {
						//往右划（）
						mDirection = DIRECTION_R;
						for (int i = mColNum - 1; i >= 0; --i) {
							if (i == mColNum - 1) {
								movePiece(mPiece[mCol][i], mPieceWidth, 0, FADE_OUT);
							} else {
								movePiece(mPiece[mCol][i], mPieceWidth, 0, FADE_NULL);
							}
						}
						mfixPhotoPiece.setNum(mPiece[mCol][mColNum - 1].getNum());
						mfixPhotoPiece.setPosition(mPiece[mCol][0].getX() - mPieceWidth, mPiece[mCol][0].getY());
						movePiece(mfixPhotoPiece, mPieceWidth, 0, FADE_IN);
						
					} else if (mNewCatchX - mOldCatchX < 0 ) {
						mDirection = DIRECTION_L;
						for (int i = mColNum - 1; i >= 0; --i) {
							if (i == 0) {
								movePiece(mPiece[mCol][i], -mPieceWidth, 0, FADE_OUT);
							} else {
								movePiece(mPiece[mCol][i], -mPieceWidth, 0, FADE_NULL);
							}
						}
						mfixPhotoPiece.setNum(mPiece[mCol][0].getNum());
						mfixPhotoPiece.setPosition(mPiece[mCol][mColNum - 1].getX() + mPieceWidth, mPiece[mCol][mColNum - 1].getY());
						movePiece(mfixPhotoPiece, -mPieceWidth, 0, FADE_IN);
					}
				} else if (Math.abs(mOldCatchX - mNewCatchX) < Math.abs(mOldCatchY - mNewCatchY)) {
					// TODO 上下划
					if (mNewCatchY - mOldCatchY > 0) {
						mDirection = DIRECTION_U;
						for (int i = mColNum - 1; i >= 0; --i) {
							if (i == mColNum - 1) {
								movePiece(mPiece[i][mRow], 0,mPieceWidth,  FADE_OUT);
							} else {
								movePiece(mPiece[i][mRow], 0,mPieceWidth, FADE_NULL);
							}
						}
						mfixPhotoPiece.setNum(mPiece[mColNum - 1][mRow].getNum());
						mfixPhotoPiece.setPosition(mPiece[0][mRow].getX(), mPiece[0][mRow].getY() - mPieceWidth);
						movePiece(mfixPhotoPiece, 0, mPieceWidth, FADE_IN);
						
					} else if (mNewCatchY - mOldCatchY < 0 ) {
						mDirection = DIRECTION_D;
						for (int i = mColNum - 1; i >= 0; --i) {
							if (i == 0) {
								movePiece(mPiece[i][mRow], 0, -mPieceWidth,  FADE_OUT);
							} else {
								movePiece(mPiece[i][mRow], 0, -mPieceWidth, FADE_NULL);
							}
						}
						mfixPhotoPiece.setNum(mPiece[0][mRow].getNum());
						mfixPhotoPiece.setPosition(mPiece[mColNum - 1][mRow].getX(), mPiece[mColNum - 1][mRow].getY() + mPieceWidth);
						movePiece(mfixPhotoPiece, 0, -mPieceWidth, FADE_IN);
					}
				}
				MagicPiece.mBtnMusic.stop();
				MagicPiece.mBtnMusic.play();
			}
		}
	}
	
	private void outOfOrder() {
		boolean leftRight;
		int tmpStep = mColNum * mColNum;
		tmpPosition = new float[3];
		
		for (int time = tmpStep - 1; time >= 0; --time) {
			mCol = MathUtils.random(mColNum - 1);
			mRow = MathUtils.random(mColNum - 1);
			leftRight = MathUtils.randomBoolean();
			if (MathUtils.randomBoolean()) {
				//Col
				if (leftRight) {
					mDirection = DIRECTION_L;
					for (int i = mColNum - 1; i >= 0; --i) {
						positions[mCol][i * 3 + 1] -= mPieceWidth;
					}
					tmpPosition[0] = positions[mCol][0];
					tmpPosition[1] = positions[mCol][(mColNum - 1) * 3 + 1] + mPieceWidth;
					tmpPosition[2] = positions[mCol][((mColNum - 1) * 3) + 2];
				} else {
					mDirection = DIRECTION_R;
					for (int i = mColNum - 1; i >= 0; --i) {
						positions[mCol][i * 3 + 1] += mPieceWidth;
					}
					tmpPosition[0] = positions[mCol][(mColNum - 1) * 3];
					tmpPosition[1] = positions[mCol][1] - mPieceWidth;
					tmpPosition[2] = positions[mCol][2];
				}
			} else {
				//row
				if (leftRight) {
					mDirection = DIRECTION_D;
					for (int i = mColNum - 1; i >= 0; --i) {
						positions[i][mRow * 3 + 2] -= mPieceWidth;
					}
					
					tmpPosition[0] = positions[0][mRow * 3] ;
					tmpPosition[1] = positions[mColNum - 1][mRow * 3 + 1] ;
					tmpPosition[2] = positions[mColNum - 1][mRow * 3 + 2] + mPieceWidth;
				} else {
					mDirection = DIRECTION_U;
					for (int i = mColNum - 1; i >= 0; --i) {
						positions[i][mRow * 3 + 2] += mPieceWidth;
					}
					tmpPosition[0] = positions[mColNum - 1][mRow * 3] ;
					tmpPosition[1] = positions[0][mRow * 3 + 1] ;
					tmpPosition[2] = positions[0][mRow * 3 + 2] - mPieceWidth;
				}
			}
			resectPos();
		}
		mDirection = DIRECTION_NULL;
	}
	
	private void barMoveAction() {
		for (int y = mColNum - 1; y >= 0; --y) {
			for (int x = mColNum - 1; x >= 0; --x) {
				float tmpNum = mPiece[y][x].getNum();
				
				ParallelAction parallelAction = Actions.parallel(
						new SequenceAction(Actions.alpha(0.1f, 0.75f,Interpolation.sineIn), Actions.alpha(1, 0.75f,Interpolation.sineOut)),
						Actions.moveTo(getNewPosition(tmpNum)[0], getNewPosition(tmpNum)[1], 1.5f,Interpolation.swing)
						);
				
				SequenceAction sequenceAction = new SequenceAction(
						parallelAction,
						Actions.run(new Runnable() {
					@Override
					public void run() {
						//TODO 重置位置
						if (!isEndMoveAction) {
							isEndMoveAction = true;
							//TODO 设置
							resetBar();
						}
					}
				}));
				mPiece[y][x].addAction(sequenceAction);
			}
		}
	}
	
	private void resetBar() {
		for (int y = 0; y < mColNum; ++y) {
			for (int x = 0; x < mColNum; ++x) {
				mPiece[y][x].setNum((int)positions[y][x * 3]);
				mPiece[y][x].setPosition(positions[y][x * 3 + 1],positions[y][x * 3 + 2]);
			}
		}
	}
	
	private float[] getNewPosition(float num) {
		float[] tmp = new float[2];
		for (int indexY = mColNum - 1; indexY >= 0; --indexY) {
			for (int indexX = mColNum - 1; indexX >= 0; --indexX) {
				if (positions[indexY][indexX * 3] == num) {
					tmp[0] = positions[indexY][indexX * 3 + 1];
					tmp[1] = positions[indexY][indexX * 3 + 2];
					return tmp;
				}
			}
		}
		return tmp;
	}
	
	private void resectPos() {
		if (mDirection == DIRECTION_L) {
			for (int i = 0; i < mColNum; i++) {
				if (i == mColNum - 1) {
					positions[mCol][i * 3]  = tmpPosition[0];
					positions[mCol][i * 3 + 1]  = tmpPosition[1];
					positions[mCol][i * 3 + 2]  = tmpPosition[2];
				} else {
					positions[mCol][i * 3]  = positions[mCol][(i + 1) * 3];
					positions[mCol][i * 3 + 1]  = positions[mCol][(i + 1) * 3 + 1];
					positions[mCol][i * 3 + 2]  = positions[mCol][(i + 1) * 3 + 2];
				}
			}
		} else if (mDirection == DIRECTION_R) {
			for (int i = mColNum - 1; i >= 0; --i) {
				if (i == 0) {
					positions[mCol][i * 3]  = tmpPosition[0];
					positions[mCol][i * 3 + 1]  = tmpPosition[1];
					positions[mCol][i * 3 + 2]  = tmpPosition[2];
				} else {
					positions[mCol][i * 3]  = positions[mCol][(i - 1)  * 3];
					positions[mCol][i * 3 + 1]  = positions[mCol][(i - 1)  * 3 + 1];
					positions[mCol][i * 3 + 2]  = positions[mCol][(i - 1)  * 3 + 2];
				}
			}
		} else if (mDirection == DIRECTION_U) {
			for (int i = mColNum - 1; i >= 0; --i) {
				if (i == 0) {
					positions[i][mRow * 3]  = tmpPosition[0];
					positions[i][mRow * 3 + 1]  = tmpPosition[1];
					positions[i][mRow * 3 + 2]  = tmpPosition[2];
				} else {
					positions[i][mRow * 3]  = positions[i - 1][mRow * 3];
					positions[i][mRow * 3 + 1]  = positions[i - 1][mRow * 3 + 1];
					positions[i][mRow * 3 + 2]  = positions[i - 1][mRow * 3 + 2];
				}
			}
		} else if (mDirection == DIRECTION_D) {
			for (int i = 0; i < mColNum; i++) {
				if (i == mColNum - 1) {
					positions[i][mRow * 3]  = tmpPosition[0];
					positions[i][mRow * 3 + 1]  = tmpPosition[1];
					positions[i][mRow * 3 + 2]  = tmpPosition[2];
				} else {
					positions[i][mRow * 3]  = positions[i + 1][mRow * 3];
					positions[i][mRow * 3 + 1]  = positions[i + 1][mRow * 3 + 1];
					positions[i][mRow * 3 + 2]  = positions[i + 1][mRow * 3 + 2];
				}
			}
		} 
	}
	
	private static void movePiece(PhotoPieceNum obj,float x,float y,byte fadeType) {
		int alphaBegin = 1;
		if(fadeType == FADE_IN) {
			alphaBegin = 1;
		} else if (fadeType == FADE_OUT) {
			alphaBegin = 0;
		}
		
		if (fadeType != FADE_NULL) {
			obj.setColor(
					obj.getColor().r, 
					obj.getColor().g, 
					obj.getColor().b, 
					alphaBegin==0?1:0);
		}
		
		ParallelAction parallelAction = Actions.parallel(
				Actions.alpha(alphaBegin, DURATION), 
				Actions.moveTo(obj.getX() + x, obj.getY() + y, DURATION,Interpolation.sineOut)
				);
		
		SequenceAction sequenceAction = new SequenceAction(parallelAction, Actions.run(new Runnable() {
			@Override
			public void run() {
				//TODO 重置位置
				if (!isMoveEnd) {
					isMoveEnd = true;
				}
			}
		}));
		
		obj.addAction(sequenceAction);
	}
	
	private void swapPosition() {
		if (mDirection == DIRECTION_L) {
			for (int i = 0; i < mColNum; i++) {
				if (i == mColNum - 1) {
					mPiece[mCol][i].setNum(mfixPhotoPiece.getNum());
					mPiece[mCol][i].setPosition(mfixPhotoPiece.getX(), mfixPhotoPiece.getY());
				} else {
					mPiece[mCol][i].setNum(mPiece[mCol][i + 1].getNum());
					mPiece[mCol][i].setPosition(mPiece[mCol][i + 1].getX(), mPiece[mCol][i + 1].getY());
				}
				setPieceAlpha(mCol,i,1);
			}
		} else if (mDirection == DIRECTION_R) {
			for (int i = mColNum - 1; i >= 0; --i) {
				if (i == 0) {
					mPiece[mCol][i].setNum(mfixPhotoPiece.getNum());
					mPiece[mCol][i].setPosition(mfixPhotoPiece.getX(), mfixPhotoPiece.getY());
				} else {
					mPiece[mCol][i].setNum(mPiece[mCol][i - 1].getNum());
					mPiece[mCol][i].setPosition(mPiece[mCol][i - 1].getX(), mPiece[mCol][i - 1].getY());
				}
				setPieceAlpha(mCol,i,1);
			}
		} else if (mDirection == DIRECTION_U) {
			for (int i = mColNum - 1; i >= 0; --i) {
				if (i == 0) {
					mPiece[i][mRow].setNum(mfixPhotoPiece.getNum());
					mPiece[i][mRow].setPosition(mfixPhotoPiece.getX(), mfixPhotoPiece.getY());
				} else {
					mPiece[i][mRow].setNum(mPiece[i - 1][mRow].getNum());
					mPiece[i][mRow].setPosition(mPiece[i - 1][mRow].getX(), mPiece[i - 1][mRow].getY());
				}
				setPieceAlpha(i,mRow,1);
			}
		} else if (mDirection == DIRECTION_D) {
			for (int i = 0; i < mColNum; i++) {
				if (i == mColNum - 1) {
					mPiece[i][mRow].setNum(mfixPhotoPiece.getNum());
					mPiece[i][mRow].setPosition(mfixPhotoPiece.getX(), mfixPhotoPiece.getY());
				} else {
					mPiece[i][mRow].setNum(mPiece[i + 1][mRow].getNum());
					mPiece[i][mRow].setPosition(mPiece[i + 1][mRow].getX(), mPiece[i + 1][mRow].getY());
				}
				setPieceAlpha(i,mRow,1);
			}
		} 
		mfixPhotoPiece.setColor(
				mfixPhotoPiece.getColor().r,
				mfixPhotoPiece.getColor().g,
				mfixPhotoPiece.getColor().b,
				0);
		
		isCanTouchDragged = true;
	}
	
	/**
	 * 行列透明度设置
	 * @param col 列
	 * @param row 横
	 * @param a
	 */
	private void setPieceAlpha(int col, int row,float a) {
		mPiece[col][row].setColor(
				mPiece[col][row].getColor().r, 
				mPiece[col][row].getColor().g, 
				mPiece[col][row].getColor().b, 
				a);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (isMoveEnd) {
			swapPosition();
			//检测
			if (isFinish()) {
				dispFinish();
			}
			isMoveEnd = false;
			mDirection = DIRECTION_NULL;
			mNewCatchX = 0;
		}
	}
	
	private void dispFinish() {
		mEnd = new ActorObject(Assets.FINISH);
		mEnd.setPosition(1000, Assets.SCREEN_HEIGHT / 2);
		addActor(mEnd);
		
		mEnd.addAction(
				Actions.sequence(
						Actions.parallel(Actions.alpha(0, 0), Actions.scaleTo(0, 0, 0)),
						Actions.parallel(Actions.moveTo(Assets.SCREEN_WIDTH/2, Assets.SCREEN_HEIGHT/2, 1f),Actions.alpha(1, 1f), Actions.scaleTo(1, 1, 1f)),
						Actions.delay(2),
						Actions.parallel(Actions.moveTo(0, Assets.SCREEN_HEIGHT/2, 1f),Actions.alpha(0, 1f), Actions.scaleTo(0, 0, 1f)),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								if (Assets.mCurPiece < 10) {
									++Assets.mCurPiece;
								}
								MyScreenManager.jumpScreen(MyScreenManager.SCREEN_GAME);
							}
						})
						)
				);
	}
	
	private boolean isFinish() {
		for (int y = mColNum - 1; y >= 0; --y) {
			for (int x = mColNum - 1; x >= 0; --x) {
//				System.out.println("效果如图" + mPiece[y][x].getNum());
				if (mPiece[y][x].getNum() != y*mColNum + x) {
					return false;
				}
			}
		}
//		System.out.println("完成");
		return true;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
	}

	public void dispose() {
		for (int y = mColNum - 1; y >= 0; --y) {
			for (int x = mColNum - 1; x >= 0; --x) {
				mPiece[y][x].clear();
				mPiece[y][x] = null;
			}
		}
		tmpPosition = null;
		mTmpPos = null;
		positions = null;
		mPiece = null;
	}
	
	
}
