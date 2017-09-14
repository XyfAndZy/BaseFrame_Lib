package com.xyf.baseframe_lib.base;

import android.content.Context;

/**
 * Created by Administrator on 2017/2/28.
 */

public interface BaseView {
    /**
     * 加载时显示加载框
     */
    void showLoading();

    /**
     * 加载完成时隐藏加载框
     */
    void hideLoading();

    /**
     * 显示提示消息
     */
    void showToastMessage(String message);

    /**
     * 显示提示消息
     */
    void showToastMessage(int message);

    /**
     * 隐藏空列表的提示
     */
    void hideEmptyHint();

    /**
     * 显示空列表的提示
     */
    void showEmptyHint();

    /**
     * 获取Context对象
     */
    Context getViewContext();
}
