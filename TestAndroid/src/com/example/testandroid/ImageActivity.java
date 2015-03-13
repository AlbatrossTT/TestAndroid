package com.example.testandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bitmap_layout);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		
		Bitmap bitmap = Bitmap.createBitmap(700, 700, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(getResources().getColor(android.R.color.holo_blue_bright));
		System.out.println("bitmap.getWidth() = " + bitmap.getWidth() + ", bitmap.getHeight() = " + bitmap.getHeight());
		
		BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
		
		imageView.setImageDrawable(bitmapDrawable);
	}

}
