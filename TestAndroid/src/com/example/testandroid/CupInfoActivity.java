package com.example.testandroid;

import java.io.IOException;
import java.io.RandomAccessFile;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class CupInfoActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_cpuinfo);
		
		TextView cpuinfoTv = (TextView) findViewById(R.id.cupinfoTextView);
		cpuinfoTv.setText(getCpuType());
	}
	
	
	public String getCpuType(){ 
	    String strInfo = getCpuString(); 
	    String strType = null; 
	        
	    if (strInfo.contains("ARMv5")) { 
	        strType = "armv5"; 
	    } else if (strInfo.contains("ARMv6")) { 
	        strType = "armv6"; 
	    } else if (strInfo.contains("ARMv7")) { 
	        strType = "armv7"; 
	    } else if (strInfo.contains("Intel")){ 
	        strType = "x86"; 
	    }else{ 
	        strType = "unknown"; 
	        return strType; 
	    } 
	        
	    if (strInfo.contains("neon")) { 
	        strType += "_neon"; 
	    }else if (strInfo.contains("vfpv3")) { 
	        strType += "_vfpv3"; 
	    }else if (strInfo.contains(" vfp")) { 
	        strType += "_vfp"; 
	    }else{ 
	        strType += "_none"; 
	    } 
	        
	    return strType; 
	} 
	
	public String getCpuString(){ 
	    if(Build.CPU_ABI.equalsIgnoreCase("x86")){ 
	        return "Intel"; 
	    } 
	        
	    String strInfo = ""; 
	    try
	    { 
	        byte[] bs = new byte[1024]; 
	        RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r"); 
	        reader.read(bs); 
	        String ret = new String(bs); 
	        int index = ret.indexOf(0); 
	        if(index != -1) { 
	            strInfo = ret.substring(0, index); 
	        } else { 
	            strInfo = ret; 
	        } 
	    } 
	    catch (IOException ex){ 
	        ex.printStackTrace(); 
	    } 
	        
	    return strInfo; 
	} 

}
