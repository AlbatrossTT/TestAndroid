package com.example.testandroid;

import java.io.File;

import com.example.testandroid.util.MD5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

public class MD5Activity extends Activity {

	public static final String FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera/1437469963555_4e2db9ae-db2c-49bb-9c2f-68e97c4b8be7_by_camera.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_md5);
		
		Intent intent = getIntent();
		if (intent != null) {
			Toast.makeText(this, intent.getDataString(), Toast.LENGTH_SHORT).show();
		}
		
		TextView md5Result = (TextView) findViewById(R.id.md5Result);
		md5Result.setText(MD5.getMD5(new File(FILE_PATH)));
	}

}
