package com.example.testandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.testandroid.util.Utils;
import com.example.testandroid.view.StartRecordGuideHelper;

public class Image9PatchAtivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image9patch);
		
		TextView textView = (TextView) findViewById(R.id.image9patch);
		textView.getLayoutParams().width = Utils.getDisplayWidth(this);
		
		textView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                StartRecordGuideHelper guideHelper = new StartRecordGuideHelper(Image9PatchAtivity.this);
                guideHelper.showGuide(Image9PatchAtivity.this, Image9PatchAtivity.this.getWindow().getDecorView().findViewById(android.R.id.content), false, 0);
            }
        });
	}
	
}
