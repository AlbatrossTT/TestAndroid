package com.example.testandroid.view;

import com.example.testandroid.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnimationView extends LinearLayout implements OnClickListener {
	
	private TextView titleBar;
	private TextView bottomBar;
	
	public AnimationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public AnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AnimationView(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context) {
        initBaseView();
    }
	
	private void initBaseView() {
		final LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.animation_view_layout, this, true);
		
		titleBar = (TextView) findViewById(R.id.titleBar);
		bottomBar = (TextView) findViewById(R.id.bottomBar);
		
		titleBar.setOnClickListener(this);
		bottomBar.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		if (viewId == R.id.bottomBar) {
			finish();
		}
	}
	
	public void show() {
		setVisibility(View.VISIBLE);
		Animation enterAnimation = new TranslateAnimation(0, 0, 144, 0);
		enterAnimation.setDuration(200);
		enterAnimation.setFillAfter(true);
		bottomBar.startAnimation(enterAnimation);
//		ViewTreeObserver obViewTreeObserver = bottomBar.getViewTreeObserver();
//		obViewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//
//			@Override
//			public boolean onPreDraw() {
//				
//				return true;
//			}
//			
//		});
	}
	
	public void finish() {
		Animation exitAnimation = new TranslateAnimation(0, 0, 0, 144);
		exitAnimation.setDuration(200);
		exitAnimation.setFillAfter(true);
		exitAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				setVisibility(GONE);
			}
		});
		bottomBar.startAnimation(exitAnimation);
		
	}

}
