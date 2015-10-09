package com.example.testandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageScaleTypeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_scale_type_layout);
		
		ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView1.setScaleType(ScaleType.FIT_CENTER);
		imageView1.setImageResource(R.drawable.health_share_bg);
		
		ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView2.setScaleType(ScaleType.FIT_CENTER);
		imageView2.setImageResource(R.drawable.health_share_bg_horizontal);
	}
	
}
