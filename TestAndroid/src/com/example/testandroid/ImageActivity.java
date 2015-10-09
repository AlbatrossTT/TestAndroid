package com.example.testandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore.Video.Thumbnails;
import android.widget.ImageView;

public class ImageActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bitmap_layout);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		
//		Bitmap bitmap = Bitmap.createBitmap(700, 700, Config.ARGB_8888);
//		Canvas canvas = new Canvas(bitmap);
//		canvas.drawColor(getResources().getColor(android.R.color.holo_blue_bright));
//		System.out.println("bitmap.getWidth() = " + bitmap.getWidth() + ", bitmap.getHeight() = " + bitmap.getHeight());
//		
//		BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
//		
//		imageView.setImageDrawable(bitmapDrawable);
		
		Bitmap bitmap = getVideoThumbnail("/storage/sdcard0/DCIM/Camera/VID_20150422_175027.3gp", 965, 965);
		imageView.setImageBitmap(bitmap);
	}
	
	public static Bitmap getVideoThumbnail(String videoPath, int width, int height) {  
        Bitmap bitmap = null;  
        // 获取视频的缩略图  参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
        // 其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96 
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, Thumbnails.MINI_KIND);  
        if (width > 0 && height > 0) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,  
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;  
    }  

}
