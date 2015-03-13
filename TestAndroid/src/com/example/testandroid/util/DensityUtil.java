package com.example.testandroid.util;

import android.content.Context;

/**
 * 根据手机的分辨率，进行dp和px转换
 * 
 */
public class DensityUtil {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		

		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;

		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 * @param spValue
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		float scale = context.getResources().getDisplayMetrics().scaledDensity;

		return (int) (spValue * scale + 0.5f);
	}
	
	/**
	 * 得到屏幕宽度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getDisplayWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	/**
	 * 得到屏幕高度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getDisplayHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
}
