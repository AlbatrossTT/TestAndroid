package com.example.testandroid;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testandroid.util.BitmapHelper;
import com.example.testandroid.util.BitmapUtils;
import com.example.testandroid.util.Utils;

public class ImageQualityActivity extends Activity {
    
    private static final String PIC_DIR = Environment.getExternalStorageDirectory() + File.separator + "pictest";
    
    private TextView tvOriginalFilePath;
    private TextView tvOutputFileDir;
    private EditText etQuality;
    private Button btnCompress;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_quality_layout);
        
        final String originalPicFile = PIC_DIR + File.separator + "original_pic.jpg";
        final String outputFileDir = PIC_DIR + File.separator + System.currentTimeMillis();
        
        tvOriginalFilePath = (TextView) findViewById(R.id.tvOriginalFilePath);
        tvOutputFileDir = (TextView) findViewById(R.id.tvFileDir);
        etQuality = (EditText) findViewById(R.id.etQuality);
        btnCompress = (Button) findViewById(R.id.btnCompress);
        
        tvOriginalFilePath.setText(originalPicFile);
        btnCompress.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
                if (!new File(originalPicFile).exists()) {
                    Toast.makeText(ImageQualityActivity.this, originalPicFile + " 不存在！", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                int quality = 0;
                try {
                    quality = Integer.parseInt(etQuality.getEditableText().toString(), 0);
                } catch (NumberFormatException e) {
                }
                if (quality < 1 || quality > 100) {
                    Toast.makeText(ImageQualityActivity.this, "请输入1-100之间的数", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                doCompress(quality);
            }
        });
    }
    
    private void doCompress(int quality) {
        
    }
    
    private static Bitmap loadPhotoPreviewBitmap( Context context, String imagePath, int rateRatio, int screenRatio ) {
        
        if ( imagePath == null || imagePath.length() == 0  ) {
            return null;
        }

        // screen size
        Rect screenSize = new Rect();
        Utils.getScreenRect( context, screenSize );
        File bmpFile = new File( imagePath );
        // bitmap size
        Rect size = new Rect();
        BitmapUtils.getZoomOutBitmapBound( bmpFile, 1, size );
        int rate = BitmapHelper.getSampleSizeAutoFitToScreen( screenSize.width() / screenRatio,
                screenSize.height() / screenRatio, size.width(), size.height() );

        // not use BitmapUtils.createZoomOutBitmap for some device has something wrong when working
        // with BufferedInputStream, e.g. LG610s
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = rate * rateRatio;
        return BitmapFactory.decodeFile( imagePath, opts );
    }

}
