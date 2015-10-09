package com.example.testandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.ImageView;

public class MaskBitmapActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.mask_bitmap_layout);
        
        ImageView imageView = (ImageView) findViewById(R.id.maskImageView);
        
        Bitmap roundBitmap = createMaskedCircleBitmap(1080, 1700, 300);
        
        imageView.setImageBitmap(roundBitmap);
    }
    
    public Bitmap createMaskedCircleBitmap(int bgWidth, int bgHeight, int radius) {
    	
    	Bitmap bmp = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawCircle(radius + (bgWidth - radius * 2) / 2, radius + (bgHeight - radius * 2) / 2, radius, paint);
        
        
        paint.setARGB(102, 0, 0, 0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        RectF _rect = new RectF(0, 0, bgWidth, bgHeight);
        canvas.drawRect(_rect, paint);
        
        return bmp;
    }

}
