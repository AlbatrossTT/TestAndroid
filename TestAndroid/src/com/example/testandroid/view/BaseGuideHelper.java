package com.example.testandroid.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;

import com.example.testandroid.util.Utils;

/**
 * 引导动画基类
 * 
 */
public abstract class BaseGuideHelper {

	protected static final int MSG_AUTO_DISMISS = 3000;
	
	protected static final int AUTO_DISMISS_INTERVAL = 3000;

	protected Activity mActivity;

	protected PopupWindow mPopupWindow;
	
	protected int mWindowWidth;
	
	protected int mWindowHeight;

	protected View mPopupView;

	protected boolean isDestory;

	protected boolean isShowingFlag;

	public BaseGuideHelper(Activity activity) {
		mActivity = activity;
		init();
	}

	protected void init() {
		
		mWindowWidth = Utils.getDisplayWidth(mActivity);
		mWindowHeight = Utils.getDisplayHeight(mActivity);
		
		mPopupView = new RelativeLayout(mActivity);
		mPopupView.setBackgroundResource(getGuideDrawableId());

		mPopupWindow = new PopupWindow(mPopupView, getWidth(), getHeight());
		mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
	}

	public void setOnClickListener(View.OnClickListener listener) {
		mPopupView.setOnClickListener(listener);
	}

	public void setOnDismissListener(OnDismissListener listener) {
		mPopupWindow.setOnDismissListener(listener);
	}

	public void showGuide(Activity activity, View parent) {
	    showGuide(activity, parent, true, AUTO_DISMISS_INTERVAL);
	}
	
	/**
	 * @param activity
	 * @param parent
	 * @param isAnimation 是否添加弹出动画
	 * @param autoDismissInterval 自动消失时间，传0不自动消失
	 */
	public void showGuide(Activity activity, View parent, boolean isAnimation, int autoDismissInterval) {
	    
	    if (isDestory) {
	        return;
	    }
	    
	    mPopupWindow.setFocusable(false);
	    mPopupWindow.setClippingEnabled(true);
	    mPopupWindow.setOutsideTouchable(true);
	    mPopupWindow.setTouchable(true);
//	    if (isAnimation) {
//	        mPopupWindow.setAnimationStyle(R.style.photo_poi_guide_anim_style);
//        }
	    mPopupWindow.update();
	    
	    Point pos = getShowLocationPoint();
	    mPopupWindow.showAtLocation(parent, getGravity(), pos.x, pos.y);
	    
	    if (autoDismissInterval > 0) {
	        mHandler.sendEmptyMessageDelayed(MSG_AUTO_DISMISS, autoDismissInterval);
        }
	}

	public void updateGuide(Activity activity, View parent) {

		if (isPopWindowShowing()) {
			Point pos = getShowLocationPoint();
			mPopupWindow.showAtLocation(parent, getGravity(), pos.x, pos.y);
			mPopupWindow.update(pos.x, pos.y, -1, -1, true);
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_AUTO_DISMISS:
				dismissPopView();
				break;

			default:
				break;
			}
		};
	};

	public void destory() {
		isDestory = true;
	}

	public void dismissPopView() {
		mHandler.removeMessages(MSG_AUTO_DISMISS);
		if (!isDestory && isPopWindowShowing()) {
			mPopupWindow.setContentView(null);
			mPopupWindow.dismiss();
		}
	}

	public PopupWindow getPopupWindow() {
		return mPopupWindow;
	}

	public boolean isPopWindowShowing() {
		return (mPopupWindow != null && mPopupWindow.isShowing());
	}

//	public void setShowingFlag(boolean isShowingFlag) {
//		this.isShowingFlag = isShowingFlag;
//	}
//
//	public boolean isShowingFlag() {
//		return isShowingFlag;
//	}
	
	/**
	 * 子类可以重写此方法，设置Gravity
	 * @return
	 */
	protected int getGravity() {
		return Gravity.NO_GRAVITY;
	}

	/**
	 * 设置引导显示的宽度
	 * 
	 * @return
	 */
	abstract protected int getWidth();

	/**
	 * 设置引导显示的高度
	 * 
	 * @return
	 */
	abstract protected int getHeight();

	/**
	 * 设置引导显示的高度
	 * 
	 * @return
	 */
	abstract protected int getGuideDrawableId();
	
	/**
	 * 设置显示的起点point
	 * @return
	 */
	abstract protected Point getShowLocationPoint();
	
	/**
	 * 设置sharedPref的key
	 * @return
	 */
	abstract public String getPrefKey();
	
	public static boolean isShowGuide(Context context, String prefKey) {
//		SharePrefManager spManager = SharePrefManager.getInstance(context, SharePrefManager.PREF_WEIBO_SP);
//		return spManager.getBoolean(prefKey, true);
	    return true;
	}

	public static boolean setGuideSp(Context context, String prefKey) {
//		SharePrefManager spManager = SharePrefManager.getInstance(context, SharePrefManager.PREF_WEIBO_SP);
//		return spManager.putBoolean(prefKey, false);
	    return true;
	}
	
}
