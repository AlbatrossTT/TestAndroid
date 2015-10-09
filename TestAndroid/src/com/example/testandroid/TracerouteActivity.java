package com.example.testandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class TracerouteActivity extends Activity {
    
    private static final String EMPTY = "";
    private static final String SPACE = " ";
    private static final String UNKNOWN_HOST = "unknown host";
    private static final String UNKNOWN_PREFIX = "unknown prefix";
    private static final int REQUEST_TIMEOUT = 60 * 1000;
    
    private TextView resultTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traceroute_layout);
        
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                TraceRouteTask task = new TraceRouteTask();
                task.startTraceRoute("ww1.sinaimg.cn");
                String result = task.getResult();
                System.out.println("TraceRouteTask result " + result + ", cost time = " + (System.currentTimeMillis() - startTime));
            }
        }).start();
    }
    
    //################## from NetWorkAnalyse ####################################
    
    /**
     * 获得服务器IP
     * 
     * @return
     */
    private static String getServerIp(String host) {
        if (TextUtils.isEmpty(host)) {
            return "";
        }
        try {
            InetAddress add = InetAddress.getByName(host.trim());
            String ip = add.getHostAddress();
            return ip;
        } catch (UnknownHostException e) {
        } catch (Exception e) {
        }
        return "";
    }
    
    public static class TraceRouteTask {
        private String sbstdOut = EMPTY;
        private String sbstdErr = EMPTY;
        private final List<Hop> list = new ArrayList<Hop>();

        public String getResult() {
            return this.sbstdOut;
        }

        public void startTraceRoute(String host) {
            if (TextUtils.isEmpty(host)) {
                return;
            }

            String ip = getServerIp(host);
            if (TextUtils.isEmpty(ip)) {
                return;
            }

            int i = 1;
            while (i <= 30) {
                StringBuilder cmd = new StringBuilder();
                cmd.append("ping -c1  -t").append(i).append(SPACE)
                        .append(host.trim());

                if (runcmd(cmd.toString())) {
                    String out;
                    if (sbstdOut.contains("bytes of ")) {
                        out = sbstdOut.substring(sbstdOut.indexOf("bytes of "));
                    } else {
                        out = sbstdOut;
                    }
                    addData(cmd.toString());
                    if (out.contains(ip)) {
                        break;
                    }

                } else {
                    break;
                }
                i++;
            }
        }

        private void addData(String c) {
            Hop data = new Hop(sbstdOut, c);
            for (Hop hop : list) {
                if (hop != null && data.ip.equals(hop.ip)
                        && !data.ip.equals(UNKNOWN_HOST)) {
                    return;
                }
            }
            list.add(data);
        }

        private boolean runcmd(String cmd) {
            Runtime runtime = Runtime.getRuntime();
            Process proc;
            sbstdOut = EMPTY;
            sbstdErr = EMPTY;

            final String command = cmd.trim();
            try { // Run Script
                proc = runtime.exec(command);
            } catch (IOException ex) {
                ex.printStackTrace();
                return true;
            }
            try {
                if (proc != null) {
                    proc.waitFor();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sbstdOut = (ReadBufferedReader(new InputStreamReader(
                    proc.getInputStream())));
            sbstdErr = (ReadBufferedReader(new InputStreamReader(
                    proc.getErrorStream())));
            return true;
        }

        private String ReadBufferedReader(InputStreamReader inputStreamReader) {
            BufferedReader buf = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            String line;
            try {
                while ((line = buf.readLine()) != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return builder.toString();
            }
            return builder.toString();
        }
    }
    
    private static class Hop {
        private static final String TAG_BYTES_FROM = "bytes from ";
        private static final String TAG_ICMP_SEQ1 = ": icmp_seq=";
        private static final String TAG_TIME = "time=";

        String ip = UNKNOWN_HOST, host, min, avg, max, mdev;
        String cmd;
        int sends = 0;
        int rev = 0;
        String last = "0";

        public Hop(String source, String c) {
            cmd = c;
            if (source != null && source.length() > 0) {
                if ((source.contains("1 received,"))) {
                    int ipstart = source.indexOf(TAG_BYTES_FROM);
                    int ipend = source.indexOf(TAG_ICMP_SEQ1);
                    if (ipstart != -1 && ipend != -1
                            && ipstart < source.length()
                            && ipend < source.length()) {
                        ip = source.substring(ipstart + 11, ipend);
                        try {
                            InetAddress address = InetAddress.getByName(ip);
                            host = address.getCanonicalHostName();
                        } catch (Exception e) {
                            host = EMPTY;
                            e.printStackTrace(); // To change body of catch
                                                    // statement use File |
                                                    // Settings | File
                                                    // Templates.
                        }
                    }
                    sends = 1;
                    int timestart = source.indexOf(TAG_TIME);
                    int timeend = source.indexOf(" ms");
                    if (timeend != -1 && (timeend) < source.length()
                            && timestart != -1
                            && (timestart + 5) < source.length()
                            && ipstart != -1 && ipend != -1
                            && ipstart < source.length()
                            && ipend < source.length()) {
                        rev = 1;
                        last = source.substring(timestart + 5, timeend);
                    }
                    int otherstart = source.indexOf("rtt min/avg/max/mdev = ");
                    if (otherstart != -1 && otherstart + 23 < source.length()) {
                        String temp = source.substring(otherstart + 23);
                        String[] sp = split(temp, "/");
                        if (sp != null && sp.length == 4) {
                            min = sp[0];
                            avg = sp[1];
                            max = sp[2];
                            mdev = sp[3];
                        }
                    }
                } else {
                    int ipstart = source.indexOf("From ");
                    int ipend = source.indexOf(" icmp_seq=");
                    if (ipstart != -1 && ipend != -1
                            && ipstart < source.length()
                            && ipend < source.length()) {
                        ip = source.substring(ipstart + 5, ipend);
                        try {
                            InetAddress address = InetAddress.getByName(ip);
                            host = address.getCanonicalHostName();
                        } catch (Exception e) {
                            host = EMPTY;
                            e.printStackTrace(); // To change body of catch
                                                    // statement use File |
                                                    // Settings | File
                                                    // Templates.
                        }
                        sends = 1;
                        rev = 0;
                    } else {
                        ip = UNKNOWN_HOST;
                        sends = 1;
                        rev = 0;
                    }
                }
            }
        }

        private String[] split(String source, String separate) {
            // WaitWindow.errStr = "split...1...";
            Vector<String> tmpVector = new Vector<String>();
            int equalLen = 0, startIndex = 0;
            for (int i = 0, n = source.length(); i < n; i++) {
                if (source.charAt(i) == separate.charAt(equalLen)) {
                    equalLen++;
                    if (equalLen == separate.length()) {
                        // 模式匹配成功
                        tmpVector.addElement(source.substring(startIndex, i
                                - separate.length() + 1));
                        startIndex = i + 1;
                        equalLen = 0;
                    }
                } else {
                    equalLen = 0;
                }
            }
            // WaitWindow.errStr = "split...2...";
            tmpVector.addElement(source.substring(startIndex));
            String[] arr = new String[tmpVector.size()];
            for (int i = 0, n = arr.length; i < n; i++) {
                arr[i] = tmpVector.elementAt(i);
            }
            // WaitWindow.errStr = "split...3...";
            return arr;
        }

        @Override
        public String toString() {
            return "Hop{" + "ip='" + ip + '\'' + ", host='" + host + '\''
                    + ", min='" + min + '\'' + ", avg='" + avg + '\''
                    + ", max='" + max + '\'' + ", mdev='" + mdev + '\'' + '}';
        }
    }

}
