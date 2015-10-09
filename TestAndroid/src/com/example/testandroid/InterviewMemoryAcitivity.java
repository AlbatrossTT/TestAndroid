package com.example.testandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

public class InterviewMemoryAcitivity extends Activity {

    Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            public void handleMessage(Message msg) {
            }
        };
        setContentView(R.layout.interview_memory);
        OnClickListener listener = new OnClickListener() {
            public void onClick(View v) {
            }
        };
        findViewById(R.id.button).setOnClickListener(listener);
        Manager.getInstance(this).register(listener);
    }

    public static enum Manager {
        INSTANCE;
        Context         context;
        OnClickListener listener;

        private Manager() {
        }

        public static Manager getInstance(Context context) {
            if (INSTANCE.context == null) {
                INSTANCE.context = context;
            }
            return INSTANCE;
        }

        public void register(OnClickListener listener) {
            this.listener = listener;
        }
    }

}
