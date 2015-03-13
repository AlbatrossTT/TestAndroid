package com.example.testandroid;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

public class AnimationActivity extends Activity {
	
	private static final String TAG = AnimationActivity.class.getSimpleName() + ":";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.animation_activity_layout);
		
		System.out.println(TAG + "onCreate");
		
	};
	
	@Override
	public void finish() {
	    super.finish();
//	    overridePendingTransition(R.anim.fading_in, R.anim.fading_out);
	}

}
