package com.example.testandroid;

import com.example.testandroid.util.DensityUtil;

import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {
	
	private TextView mInfoView;
	private PopupWindow mPopupWindow;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	int itemId = item.getItemId();
    	if (itemId == R.id.action_info) {
			showInfo();
		} else {
			Toast.makeText(this, "Selected Item: [" + item.getTitle() + ":" + item.getItemId() + "]", Toast.LENGTH_SHORT).show();
		}
        return true;
    }
    
    private void showInfo() {
    	
    	if (mInfoView == null || mPopupWindow == null) {
    		
    		mInfoView = new TextView(this);
    		mInfoView.setTextColor(getResources().getColor(android.R.color.white));
    		mInfoView.setTextSize(DensityUtil.dip2px(this, 6));
    		mInfoView.setText(getPageInfo());
			
			int padding = DensityUtil.dip2px(this, 4);
			mInfoView.setPadding(padding, padding, padding, padding);
			
			int popupWindowWidth = getWindowManager().getDefaultDisplay().getWidth();
			int popupWindowHeight = getWindowManager().getDefaultDisplay().getHeight() / 2;
    		mPopupWindow = new PopupWindow(mInfoView, popupWindowWidth, popupWindowHeight);
		}

		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.dialog_holo_light_frame));
		mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
		mPopupWindow.setFocusable(true);
		mPopupWindow.update();
		mPopupWindow.showAtLocation(getWindow().getDecorView().findViewById(android.R.id.content), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }
    
    protected abstract String getPageInfo();
	
}
