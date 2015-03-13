package com.example.testandroid;

import com.example.testandroid.view.VideoCutSeekBar;

import android.app.Activity;
import android.os.Bundle;

public class ViewTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view_test);
		
		VideoCutSeekBar videoCutSeekBar = (VideoCutSeekBar) findViewById(R.id.video_cut_seekbar);
		videoCutSeekBar.start(30000);
		
//		final ImageView imageView = (ImageView) findViewById(R.id.view_test_imageview);
//		
//		new Handler().postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				imageView.layout(300, 300, 300 + imageView.getWidth(), 300 + imageView.getHeight());
//			}
//		}, 2000);
	}
	
}
