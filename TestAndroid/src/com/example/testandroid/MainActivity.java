package com.example.testandroid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testandroid.util.DensityUtil;

public class MainActivity extends Activity {
	
	private String[] DEMO_DES = {
			"Animation", 
			"LifeCycle", 
			"WindowSoftInputMode", 
			"ViewTree",
			"DispatchKeyEvent",
			"ImageActivity",
			"ViewTestActivity",
			"BitmapDecodeActivity",
			"CupInfoActivity", 
			"Image9PatchAtivity",
			"MaskBitmapActivity",
			"WeChatShareActivity",
			"GetAppSignatureActivity",
			"HttpActivity",
			"TracerouteActivity",
			"AsyncTaskActivity",
			"MD5Activity",
			"ImageScaleTypeActivity",
			"CanvasDrawActivity",
			"TextureViewCameraActivity"
	};
	
	@SuppressWarnings("rawtypes")
	private Class[] CLASS = {
			AnimationActivity.class,
			LifeCycleActivity.class,
			WindowSoftInputModeActivity.class,
			ViewTreeActivity.class,
			DispatchKeyEventActivity.class,
			ImageActivity.class,
			ViewTestActivity.class,
			BitmapDecodeActivity.class,
			CupInfoActivity.class,
			Image9PatchAtivity.class,
			MaskBitmapActivity.class,
			WeChatShareActivity.class,
			GetAppSignatureActivity.class,
			HttpActivity.class,
			TracerouteActivity.class,
			AsyncTaskActivity.class,
			MD5Activity.class,
			ImageScaleTypeActivity.class,
			CanvasDrawActivity.class,
			TextureViewCameraActivity.class
	};
	
	private ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		Uri uri = Uri.parse("android.resource://com.example.testandroid/" + R.drawable.ic_launcher);
//		System.out.println(uri.getLastPathSegment());
		
		URI uri = URI.create("http://us.sinaimg.cn/0007iJenjx06Vsb24XKg01040100aBEo0k01.mp4?KID=unistore,video&Expires=1443077914&ssig=Uh%2F%2Bly7ZZU");
		System.out.println(uri.getHost());
		System.out.println(uri.getPath());
		System.out.println(uri.getAuthority());
		
		mListView = new ListView(this);
		mListView.setBackgroundColor(getResources().getColor(android.R.color.black));
		
		setContentView(mListView);
		
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(mOnItemClickListener);
		
		System.out.println(Build.DEVICE);
		System.out.println(Build.MODEL);
		System.out.println(Build.MANUFACTURER);
		
		final String picPath = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera/1404111805703.jpg";
		
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = false;
			        options.inPreferredConfig = Config.ARGB_8888;
			        options.inSampleSize = 1;
			        try {
						FileInputStream input = new FileInputStream(picPath);
						Log.d("SONG", "loadImage input == null ? " + (input == null) + ", threadId = " + Thread.currentThread());
						Bitmap bmp = BitmapFactory.decodeStream(input, null, options);
						Log.d("SONG", "loadImage bmp == null ? " + (bmp == null) + ", threadId = " + Thread.currentThread());
						input.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
	}
	
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, CLASS[position]);
//			String str = new String(new byte[1024 * 1024]);
//			intent.putExtra("byte", new Obj());
			MainActivity.this.startActivity(intent);
		}
	};
	
	public static class Obj implements Serializable {
		
		private static final long serialVersionUID = 576199120901288640L;
		
		String str;

		public Obj() {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < 1024 * 1024; i++) {
				buffer.append("1");
			}
			str = buffer.toString();
		}
		
	}
	
	private BaseAdapter mAdapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			TextView textView = new TextView(MainActivity.this);
			textView.setTextColor(getResources().getColor(android.R.color.white));
			textView.setTextSize(DensityUtil.dip2px(MainActivity.this, 8));
			textView.setText(DEMO_DES[position]);
			
			int padding = DensityUtil.dip2px(MainActivity.this, 4);
			textView.setPadding(padding, padding, padding, padding);
			
			return textView;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public Object getItem(int position) {
			return CLASS[position];
		}
		
		@Override
		public int getCount() {
			return CLASS.length;
		}
	};
	
}
