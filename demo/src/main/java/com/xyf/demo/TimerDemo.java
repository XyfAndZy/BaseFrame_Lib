package com.xyf.demo;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description: <br/>
 * Author:XYF<br/>
 * Date:2017/9/11 10:37
 */

public class TimerDemo extends Timer {

    @Override
    public void schedule(TimerTask task, long delay, long period) {
        super.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 1000, period);
    }


    private void test(){
        TimerRunnable runnable = new TimerRunnable() {
            @Override
            public void run() {

            }
        };
        new Handler();
        new Thread(runnable).start();
    }
}
