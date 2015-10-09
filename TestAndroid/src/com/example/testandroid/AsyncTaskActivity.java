package com.example.testandroid;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class AsyncTaskActivity extends Activity {

    public static final int INTERVAL  = 100;
    public static final int TASK_SIZE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpuinfo);

        MyTask s = new MyTask();
        MyTask s1 = new MyTask();
        MyTask s2 = new MyTask();
        MyTask s3 = new MyTask();
        MyTask s4 = new MyTask();
        MyTask s5 = new MyTask();

        s.execute("Task 1");
        s1.execute("Task 2");
        s2.execute("Task 3");
        s3.execute("Task 4");
        s4.execute("Task 5");
        s5.execute("Task 6");

//         s.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Task 1");
//         s1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Task 2");
//         s2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Task 3");
//         s3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Task 4");
//         s4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Task 5");
//         s5.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Task 6");
    }

    class MyTask extends AsyncTask<String, Integer, String> {
        public MyTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            String s = params[0];
            int i = 0;
            while (i++ < TASK_SIZE)
                try {
                    Thread.sleep(INTERVAL);
                    System.out.println("doInBackground In Task============>" + s);
                    this.publishProgress(i);

                } catch (Exception e) {

                }
            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            System.out.println(values[0] + "/" + TASK_SIZE);
            super.onProgressUpdate(values);
        }
    }
}