package com.example.testandroid;

import com.example.testandroid.util.MaskBitmapUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.ImageView;

public class MaskBitmapActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.mask_bitmap_layout);
        
        ImageView imageView = (ImageView) findViewById(R.id.maskImageView);
        
        Bitmap bitmap = Bitmap.createBitmap(600, 600, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(255, 255, 0, 0);
        
//        Bitmap roundBitmap = MaskBitmapUtil.CreateMaskedBitmap(bitmap, 600, 600, 90);
        Bitmap roundBitmap = MaskBitmapUtil.CreateMaskedCircleBitmap(bitmap, 600, 20, 0xffffffff, 0xffffff00);
        
        imageView.setImageBitmap(roundBitmap);
    }

}
