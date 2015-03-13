package com.example.testandroid;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

public class WeChatShareActivity extends Activity {

    private static final String PIC_CROP_DIR   = Environment.getExternalStorageDirectory() + File.separator + "piccrop";
    private String              originFilePath = PIC_CROP_DIR + File.separator + "originalPic.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        share2WeChat();
    }

    private void share2WeChat() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        File file = new File(originFilePath);
        if (file != null && file.exists()) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            shareIntent.setType("file/*");
            shareIntent.putExtra("sms_body", "sms_body");
        } else {
            shareIntent.setType("text/plain");
        }
        shareIntent.putExtra("Kdescription", "Kdescription");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "text");
        shareIntent = Intent.createChooser(shareIntent, "分享");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(shareIntent);
    }

}
