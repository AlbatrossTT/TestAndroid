package com.example.testandroid;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;

public class BitmapDecodeActivity extends Activity {
	
	private static final String PIC_CROP_DIR = Environment.getExternalStorageDirectory() + File.separator + "piccrop";
	private String originFilePath = PIC_CROP_DIR + File.separator + "originalPic.jpg";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("start decode " + Thread.currentThread().getId());
					final BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inSampleSize = 1;
		            opts.inJustDecodeBounds = false;
		            Bitmap bitmap = safeDecodeBimtapFile(originFilePath, opts);
		            if (bitmap == null || bitmap.isRecycled()) {
		            	System.out.println("decode failed!!! " + Thread.currentThread().getId());
		            } else {
		            	System.out.println("decode success!!! " + Thread.currentThread().getId());
					}
				}
			}).start();
		}
		
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("start decode " + Thread.currentThread().getId());
					final BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inSampleSize = 1;
					opts.inJustDecodeBounds = false;
					Bitmap bitmap = BitmapFactory.decodeFile(originFilePath, opts);
					if (bitmap == null || bitmap.isRecycled()) {
						System.out.println("decode failed!!! " + Thread.currentThread().getId());
					} else {
						System.out.println("decode success!!! " + Thread.currentThread().getId());
					}
				}
			}).start();
		}
	}
	
	public Bitmap safeDecodeBimtapFile( String bmpFile, BitmapFactory.Options opts ) {
        BitmapFactory.Options optsTmp = opts;
        if ( optsTmp == null ) {
            optsTmp = new BitmapFactory.Options();
            optsTmp.inSampleSize = 1;
        }
        
        Bitmap bmp = null;
        FileInputStream input = null;
        
        final int MAX_TRIAL = 5;
        for( int i = 0; i < MAX_TRIAL; ++i ) {
            try {
                File file = new File(bmpFile);
                file.setLastModified(System.currentTimeMillis());
                input = new FileInputStream( file );
                bmp = BitmapFactory.decodeStream(input, null, opts);
                closeStream(input);
                break;
            }
            catch( OutOfMemoryError e ) {
                e.printStackTrace();
                optsTmp.inSampleSize *= 2;
                closeStream(input);
            }
            catch (FileNotFoundException e) {
                break;
            }
        }
        
        return bmp;
    }
	
	public boolean closeStream(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
				return true;
			}
		} catch (IOException e) {
		}

		return false;
	}

}
