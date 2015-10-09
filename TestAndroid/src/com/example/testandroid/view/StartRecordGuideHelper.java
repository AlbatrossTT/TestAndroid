package com.example.testandroid.view;

import android.app.Activity;
import android.graphics.Point;
import android.view.Gravity;

import com.example.testandroid.R;


/**
 * 首次用户进入拍摄界面
 * 对准有趣的画面，开始拍摄
 * 
 */
public class StartRecordGuideHelper extends BaseGuideHelper {
	
	public static final String PREF_KEY = "helper_video_videorecord";

	public StartRecordGuideHelper(Activity activity) {
		super(activity);
	}

	@Override
	protected int getWidth() {
		return mWindowWidth;
	}

	@Override
	protected int getHeight() {
		return 132;
	}

	@Override
	protected int getGuideDrawableId() {
		return R.drawable.helper_video_videorecord;
	}

	@Override
	protected Point getShowLocationPoint() {
		final int x = 0;
		final int y = mWindowWidth + 200;
		return new Point(x, y);
	}
	
	@Override
	public String getPrefKey() {
		return PREF_KEY;
	}
	
	@Override
	protected int getGravity() {
		return Gravity.TOP | Gravity.LEFT;
	}

}
