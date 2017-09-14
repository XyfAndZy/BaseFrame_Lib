package com.xyf.baseframe_lib.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;

import com.xyf.baseframe_lib.utlis.AppManager;
import com.xyf.baseframe_lib.utlis.ToastMgr;
import com.xyf.baseframe_lib.utlis.MPermissionUtils;
import com.xyf.baseframe_lib.view.LoadingDialog;

import butterknife.ButterKnife;

/**
 *
 */

public abstract class BaseActivity extends FragmentActivity implements BaseView {

    private LoadingDialog loadingDialog;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        setTranslucentStatusBar();
        init(savedInstanceState);
    }

    /**
     * 初始化方法, 类似OnCreate, 仅在此方法中做初始化操作, findView与事件绑定请使用ButterKnife
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 获取LoadingDialog, 用来显示加载中
     *
     * @return LoadingDialog
     */
    public LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    public void showToast(String s) {
        ToastMgr.show(s);
    }

    public void showToast(int r) {
        ToastMgr.show(r);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showToastMessage(String message) {
        showToast(message);
    }

    @Override
    public void showToastMessage(int message) {
        showToast(message);
    }

    @Override
    public void hideEmptyHint() {
    }

    @Override
    public void showEmptyHint() {
    }

    /**
     * 指定Activity需加载的布局ID, {@link BaseActivity}BaseActivity
     * 会通过{@link #setContentView}方法来加载布局
     *
     * @return 需加载的布局ID
     */
    protected abstract int getLayoutId();

    /**
     * 设置全屏模式，并将状态栏设置为透明，支持4.4及以上系统
     */
    protected void setTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.BLACK);
            setFullScreen();
        }
    }

    /**
     * 设置状态栏为浅色模式，状态栏上的图标都会变为深色。仅支持6.0及以上系统
     */
    protected void setLightStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 设置全屏模式
     */
    protected void setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * 设置系统状态颜色，仅支持6.0及以上系统
     */
    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
