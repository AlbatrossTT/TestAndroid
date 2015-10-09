package com.example.testandroid.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class Utils {

    /**
     * Get display width
     * 
     * @return
     */
    public static int getDisplayWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager w = activity.getWindowManager();
        Display display = w.getDefaultDisplay();
        display.getMetrics(metrics);
        return display.getWidth();
    }

    /**
     * Get display height
     * 
     * @return
     */
    public static int getDisplayHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager w = activity.getWindowManager();
        Display display = w.getDefaultDisplay();
        display.getMetrics(metrics);
        return display.getHeight();
    }
    
    public static void getScreenRect( Context ctx_, Rect outrect_ ) {
        Display screenSize = ((WindowManager) ctx_.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        outrect_.set(0, 0, screenSize.getWidth(), screenSize.getHeight());
    }

}
