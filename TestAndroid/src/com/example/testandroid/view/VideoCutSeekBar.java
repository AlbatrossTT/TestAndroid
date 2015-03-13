package com.example.testandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class VideoCutSeekBar extends View {
	
	private static final String TAG = VideoCutSeekBar.class.getSimpleName();
	
	private Paint seekPaint;
	private int seekPaintWidth;
	private int currentPlayDuration;
	private int videoDuration;
	private boolean progressChanged;

	public VideoCutSeekBar(Context paramContext) {
		super(paramContext);
		init();
	}

	public VideoCutSeekBar(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init();
	}

	public VideoCutSeekBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		init();
	}

	private void init() {
		seekPaint = new Paint();

//		seekWidht = getResources().getDimensionPixelOffset(R.dimen.camera_progress_line_width);
		seekPaintWidth = 6;

		setBackgroundColor(getResources().getColor(android.R.color.transparent));
		seekPaint.setColor(getResources().getColor(android.R.color.white));
		seekPaint.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

//		Log.d(TAG, "call onDraw currentPlayDuration = " + currentPlayDuration + ", videoDuration = " + videoDuration);
		
		if (videoDuration > 0) {
			final int width = getMeasuredWidth();
			final int height = getMeasuredHeight();
			final int currentPostion = currentPlayDuration * width / videoDuration;
			final int left = currentPostion > 0 ? currentPostion : 0;
			final int right = left + seekPaintWidth < width ? left + seekPaintWidth : width;
			
			canvas.drawRect(left, 0, right, height, seekPaint);
		}

	}
	
	// 播放中
	private final static int HANDLER_INVALIDATE_VIDEO_PLAYING = 0;

	private Handler mHandler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case HANDLER_INVALIDATE_VIDEO_PLAYING:
				invalidate();
				if (progressChanged) {
					sendEmptyMessageDelayed(HANDLER_INVALIDATE_VIDEO_PLAYING, 50);
					currentPlayDuration += 50;
				}
				break;
			}
			super.dispatchMessage(msg);
		}
	};
	
	/**
	 * 暂停播放，seekbar保持原有位置
	 * @param isPause
	 */
	public void pause(boolean isPause) {
//		Log.d(TAG, "call pause isPause = " + isPause);
		progressChanged = !isPause;
	}
	
	/**
	 * 播放视频从头开始播放，seekbar复位
	 * @param videoDuration
	 */
	public void start(int videoDuration) {
//		Log.d(TAG, "call start videoDuration = " + videoDuration);
		this.videoDuration = videoDuration;
		currentPlayDuration = 0;
		progressChanged = true;
		mHandler.removeMessages(HANDLER_INVALIDATE_VIDEO_PLAYING);
		mHandler.sendEmptyMessage(HANDLER_INVALIDATE_VIDEO_PLAYING);
	}

	/**
	 * 停止播放，seekbar复位
	 */
	public void stop() {
//		Log.d(TAG, "call stop");
		progressChanged = false;
		currentPlayDuration = 0;
		invalidate();
	}

}
