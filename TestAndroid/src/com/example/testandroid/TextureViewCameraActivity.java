package com.example.testandroid;

import java.io.IOException;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.widget.FrameLayout;

public class TextureViewCameraActivity extends Activity implements SurfaceTextureListener {
	
	private static final String TAG = TextureViewCameraActivity.class.getSimpleName();
	
	private TextureView mTextureView;
	private Camera mCamera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTextureView = new TextureView(this);
		mTextureView.setSurfaceTextureListener(this);
		setContentView(mTextureView);
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
		Log.d(TAG, "onSurfaceTextureAvailable");
		mCamera = Camera.open();
		Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
		mTextureView.setLayoutParams(new FrameLayout.LayoutParams(
				previewSize.width, previewSize.height, Gravity.CENTER));
		try {
			mCamera.setPreviewTexture(surface);
		} catch (IOException t) {
		}
		mCamera.startPreview();
		mTextureView.setAlpha(1.0f);
		mTextureView.setRotation(90.0f);
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		return false;
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
	}

}
