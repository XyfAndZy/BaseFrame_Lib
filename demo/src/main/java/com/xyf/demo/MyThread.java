package com.xyf.demo;

import android.util.Log;

/**
 * Description: <br/>
 * Author:XYF<br/>
 * Date:2017/9/13 10:26
 */

public class MyThread extends Thread {
    private static final String TAG = "MyThread";
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        Log.e(TAG,Thread.currentThread().getName());
    }
}
