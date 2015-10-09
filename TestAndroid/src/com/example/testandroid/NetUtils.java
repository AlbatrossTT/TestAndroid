package com.example.testandroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.example.testandroid.util.Reflection;

public class NetUtils {

    public static final String NETWORK_CLASS_2_G = "2g";
    public static final String NETWORK_CLASS_3_G = "3g";
    public static final String NETWORK_CLASS_4_G = "4g";
    public static final String NETWORK_CLASS_WIFI = "wifi";
    public static final String NETWORK_CLASS_MOBILE = "mobile";
    
    public enum NetworkState {
        NOTHING, MOBILE, WIFI
    }
    
    // 用反射调用（自己写个反射类）
    private static Reflection reflection;

    private static String getPhoneSystem() {
        if (reflection == null) {
            reflection = new Reflection();
        }
        try {
            Object opp = reflection.newInstance("android.os.SystemProperties", new Object[] {});
            String system = (String) reflection.invokeMethod(opp, "get", new Object[] { "apps.setting.platformversion", "" });
            return system;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static NetworkState getNetworkState(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        try {
            info = cm.getActiveNetworkInfo();
        } catch (NullPointerException e) {
            // do nothing,有的手机可以关闭这个权限，在这个方法内部会抛出空指针，这里做的容错处理
        }
        String phoneSystem = getPhoneSystem();
        if (!TextUtils.isEmpty(phoneSystem) && (phoneSystem.equals("Ophone OS 2.0") || phoneSystem.equals("OMS2.5"))) {
            if (info == null || !info.isAvailable()) {
                return NetworkState.MOBILE;
            } else {
                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return NetworkState.MOBILE;
                } else {
                    return NetworkState.WIFI;
                }
            }
        }
        if (info == null || !info.isAvailable()) {
            return NetworkState.NOTHING;
        } else {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NetworkState.MOBILE;
            } else {
                return NetworkState.WIFI;
            }
        }
    }
    
    public static String getNetworkClass(Context context) {

        NetworkState state = getNetworkState(context);
        if (state == NetworkState.WIFI) {
            return NETWORK_CLASS_WIFI;
        }

        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        int type = telManager.getNetworkType();

        switch (type) {
        case TelephonyManager.NETWORK_TYPE_GPRS:
        case TelephonyManager.NETWORK_TYPE_EDGE:
        case TelephonyManager.NETWORK_TYPE_CDMA:
        case TelephonyManager.NETWORK_TYPE_1xRTT:
        case 11:// TelephonyManager.NETWORK_TYPE_IDEN:
            return NETWORK_CLASS_2_G;
        case TelephonyManager.NETWORK_TYPE_UMTS:
        case TelephonyManager.NETWORK_TYPE_EVDO_0:
        case TelephonyManager.NETWORK_TYPE_EVDO_A:
        case 8:// TelephonyManager.NETWORK_TYPE_HSDPA:
        case 9:// TelephonyManager.NETWORK_TYPE_HSUPA:
        case 10:// TelephonyManager.NETWORK_TYPE_HSPA:
        case 12:// TelephonyManager.NETWORK_TYPE_EVDO_B:
        case 14:// TelephonyManager.NETWORK_TYPE_EHRPD:
        case 15:// TelephonyManager.NETWORK_TYPE_HSPAP:
            return NETWORK_CLASS_3_G;
        case 13:// TelephonyManager.NETWORK_TYPE_LTE:
            return NETWORK_CLASS_4_G;
        default:
            // return NETWORK_CLASS_UNKNOWN;
            return NETWORK_CLASS_2_G;
        }
    }
    
}
