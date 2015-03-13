package com.example.testandroid;

import android.content.res.Configuration;
import android.os.Bundle;

public class LifeCycleActivity extends BaseActivity {
	
	private static final String TAG = LifeCycleActivity.class.getSimpleName() + ":";
	
	@Override
	protected String getPageInfo() {
		return "daassssssssssssssssssssssssssssssssgggggggggggggggggggggggggggggggasssaaaaaaaaaaaaaaaa";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.life_cycle_activity_layout);
		
		System.out.println(TAG + "onCreate");
		
	};
	
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println(TAG + "onStart");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println(TAG + "onRestart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println(TAG + "onResume");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		System.out.println(TAG + "onRestoreInstanceState");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		System.out.println(TAG + "onPause");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		System.out.println(TAG + "onStop");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println(TAG + "onDestroy");
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		System.out.println(TAG + "onConfigurationChanged");
	}
	
//	@Override
//	public void finish() {
//	    super.finish();
//	    overridePendingTransition(R.anim.fading_in, R.anim.fading_out);
//	}

}
