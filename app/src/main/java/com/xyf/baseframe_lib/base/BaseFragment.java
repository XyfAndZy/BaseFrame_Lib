package com.xyf.baseframe_lib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/28.
 */

public abstract class BaseFragment extends Fragment implements BaseView {
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    private View view;
    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {

    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
        }
        ButterKnife.bind(this, view);
        init(savedInstanceState);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 指定Fragment需加载的布局ID
     *
     * @return 需加载的布局ID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化方法, 类似OnCreate, 仅在此方法中做初始化操作, findView与事件绑定请使用ButterKnife
     */
    protected abstract void init(Bundle savedInstanceState);

    @Override
    public void showLoading() {
        if (getActivity() instanceof BaseActivity) ((BaseActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        if (getActivity() instanceof BaseActivity) ((BaseActivity) getActivity()).hideLoading();
    }

    @Override
    public void showToastMessage(String message) {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showToastMessage(message);
    }

    @Override
    public void showToastMessage(int message) {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showToastMessage(message);
    }

    @Override
    public void hideEmptyHint() {
    }

    @Override
    public void showEmptyHint() {
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    /**
     * 设置全屏模式，并将状态栏设置为透明，支持4.4及以上系统
     */
    protected void setTranslucentStatusBar() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).setTranslucentStatusBar();
    }
}
