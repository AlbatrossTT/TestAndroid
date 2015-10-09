package com.example.testandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CanvasDrawView extends View {
	
	private Paint paint;
	private PorterDuffXfermode porterDuffXfermode;
	private static final int BG_WIDTH = 1080;
	private static final int BG_HEIGHT = 1700;
	private static final int RADIUS = 300;

	public CanvasDrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CanvasDrawView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		// 创建画笔  
		paint = new Paint();  
		porterDuffXfermode = new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_OUT);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
        paint.setAntiAlias(true);
        canvas.drawCircle(RADIUS + (BG_WIDTH - RADIUS * 2) / 2, RADIUS + (BG_HEIGHT - RADIUS * 2) / 2, RADIUS, paint);
        
        
        paint.setARGB(102, 0, 0, 0);
        paint.setXfermode(porterDuffXfermode);
        RectF _rect = new RectF(0, 0, BG_WIDTH, BG_HEIGHT);
        canvas.drawRect(_rect, paint);
	}

}
