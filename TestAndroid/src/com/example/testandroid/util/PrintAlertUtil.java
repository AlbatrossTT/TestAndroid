package com.example.testandroid.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

public class PrintAlertUtil {
	
	public static ProgressDialog showDialog(Activity context, String strContent) {
		if (context != null) {
			ProgressDialog infoDialog = new ProgressDialog(context);
			infoDialog.setMessage(strContent);
			infoDialog.show();
			return infoDialog;
		}
		return null;
	}

	public static ProgressDialog showDialog(Activity context, String strContent, int max) {
		if (context != null) {
			ProgressDialog dialog = new ProgressDialog(context);
			dialog.setMessage(strContent);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMax(max);
			dialog.setProgress(0);
			dialog.show();
			return dialog;
		}
		return null;
	}

	/**
	 * @param infoDialog
	 */
	public static void dismissDialog(ProgressDialog infoDialog) {
		if (infoDialog != null) {
			infoDialog.dismiss();
		}
		infoDialog = null;
	}

	public static void showToast(Activity ac, String content) {
		Toast.makeText(ac, content, Toast.LENGTH_SHORT).show();
	}
}