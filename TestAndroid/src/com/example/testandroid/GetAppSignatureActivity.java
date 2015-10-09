package com.example.testandroid;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.testandroid.util.MD5;

public class GetAppSignatureActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.get_app_signature_layout);
        
        TextView textView = (TextView) findViewById(R.id.appSignature);
        textView.setText(getSignatureWithMd5(this, "com.example.testandroid"));
    }
    
    /**
     * 获得经过MD5校验的应用签名
     * @param packageName
     * @return
     */
    public String getSignatureWithMd5(Context context, String packageName) {
        if (!checkArgs(packageName)) {
            return "";
        }
        String sign = null;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (pi != null) {
                Signature[] signature = pi.signatures;
                if(signature != null && signature.length > 0){
                    sign = MD5.hexdigest(signature[0].toByteArray());
                }
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return sign;
    }
    
    /**
     * 检验参数是否有为空的
     * @param args
     * @return
     */
    public static boolean checkArgs(String...args) {
        for (String s : args) {
            if (TextUtils.isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

}
