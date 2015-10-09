package com.example.testandroid;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

public class HttpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
//                HttpHead httpHead = new HttpHead("http://ww2.sinaimg.cn/woriginal/61e895aejw1eqr1acj1cij20z20hidld.jpg");  
                HttpGet httpGet = new HttpGet("http://ww2.sinaimg.cn/woriginal/dafdafafdasfdasfdas.jpg");
//                HttpGet httpGet = new HttpGet("http://us.sinaimg.cn/002DhL22jx06Rl4XsjYj010d0100001X0k01.m3u8?KID=unistore,video&Expires=1428548631&ssig=DWayPS3jGS");
                httpGet.setHeader("Range", "bytes=0-1");
                try {
                    HttpResponse response = httpClient.execute(httpGet);
                    
                    //获取HTTP状态码  
                    int statusCode = response.getStatusLine().getStatusCode();  
                    System.out.println("statusCode = " + statusCode);
                    
                    if(statusCode != 200) {
                        
                    } else {
                        for(Header header : response.getAllHeaders()){  
                            System.out.println(header.getName()+":"+header.getValue());  
                        }  
                    }
                    
                    //Content-Length  
                    Header[] headers = response.getHeaders("Content-Length");  
                    if(headers.length > 0) {
                        long contentLength = Long.valueOf(headers[0].getValue()); 
                        System.out.println("contentLength = " + contentLength);  
                    }
                    
                    //X-Via-CDN
                    headers = response.getHeaders("X-Via-CDN");  
                    if(headers.length > 0) {
                        String cdn = headers[0].getValue(); 
                        System.out.println("response.getHeaders cdn = " + cdn);  
                    }
                    Header header = response.getFirstHeader("X-Via-CDN");
                    if (header != null && !TextUtils.isEmpty(header.getValue())) {
                        String cdn = header.getValue();
                        System.out.println("response.getFirstHeader cdn = " + cdn);  
                    }
                  
                    httpGet.abort();  
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }  
            }
        }).start();
    }
}
