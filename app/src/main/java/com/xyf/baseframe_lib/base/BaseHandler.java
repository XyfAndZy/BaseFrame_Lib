package com.xyf.baseframe_lib.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class BaseHandler<T> extends Handler
{
    public WeakReference<T> mReference;

    public BaseHandler(T t)
    {
        if (mReference == null)
        {
            mReference = new WeakReference<T>(t);
        }
    }


    @Override
    public void handleMessage(Message msg)
    {
        T t = mReference.get();
        if (t == null)
        {
            return;
        }
    }
}
