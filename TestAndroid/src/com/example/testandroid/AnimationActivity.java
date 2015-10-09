package com.example.testandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.testandroid.view.AnimationView;

public class AnimationActivity extends Activity implements OnClickListener {
	
	private static final String TAG = AnimationActivity.class.getSimpleName() + ":";
	
	private TextView titleBar;
	private TextView bottomBar;
	private AnimationView animationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation_activity_layout);
		
		titleBar = (TextView) findViewById(R.id.titleBar);
		bottomBar = (TextView) findViewById(R.id.bottomBar);
		animationView = (AnimationView) findViewById(R.id.animationView);
		
		titleBar.setOnClickListener(this);
		bottomBar.setOnClickListener(this);
	};
	
	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		if (viewId == R.id.titleBar) {
			
		} else if (viewId == R.id.bottomBar) {
			animationView.show();
		}
	}

}
