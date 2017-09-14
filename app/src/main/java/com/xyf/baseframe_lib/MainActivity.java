package com.xyf.baseframe_lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.xyf.baseframe_lib.base.BaseActivity;
import com.xyf.baseframe_lib.base.BaseResponse;
import com.xyf.baseframe_lib.manager.API;
import com.xyf.baseframe_lib.manager.RetrofitManager;
import com.xyf.baseframe_lib.utlis.AppManager;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    public static final int REQUEST_CODE = 5;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;

    private ArrayList<Fragment> fragments;
//    private HomeFragment homeFragment;
//    private InterestFragment interestFragment;
//    private MineFragment mineFragment;
//    private ShopCartFragment shopCartFragment;
//    private CityWideFragment classifyFragment;

    @Override
    protected void init(Bundle savedInstanceState) {
        radioGroup.setOnCheckedChangeListener(this);

        fragments = new ArrayList<>();
      /*  homeFragment = new HomeFragment();
        classifyFragment =  new CityWideFragment();
        interestFragment = new InterestFragment();
        shopCartFragment = new ShopCartFragment();
        mineFragment = new MineFragment();

        fragments.add(homeFragment);
        fragments.add(classifyFragment);
        fragments.add(interestFragment);
        fragments.add(shopCartFragment);
        fragments.add(mineFragment);*/

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.fragment_content, homeFragment).show(homeFragment);
        ft.commit();
        initData();
    }

    private void initData() {
//        UserManager.update(mContext, new BaseHttpManager.BaseCallListener() {
//            @Override
//            public void onSuccess(BaseResponse pResponse) {
//                try {
//                    UpdateResponse.UpdateBean data = ((UpdateResponse) pResponse).data;
//                    buildVersion(data);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFail(BaseResponse pResponse) {
//
//            }
//        });
        RetrofitManager.getInstance().createReq(API.class)
                .addMemberTag("", "", "", "", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //处理请求失败的操作
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        //处理成功后的结果
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private int index = 0;
    private int currentTabIndex = 0;

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rbtn_home:
                index = 0;
                break;
            case R.id.rbtn_classify:
                index = 1;
                break;
            case R.id.rbtn_public_interest:
                index = 2;
                break;
            case R.id.rbtn_shopcart:
                index = 3;
                break;
            case R.id.rbtn_mine:
                index = 4;
                break;
        }
        switchFragment(index);
    }

    private void switchFragment(int index) {
        if (currentTabIndex == index)
            return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = fragments.get(index);
        if (!fragment.isAdded()) {
            ft.add(R.id.fragment_content, fragment);
        }
        ft.show(fragment).hide(fragments.get(currentTabIndex)).commit();
        currentTabIndex = index;
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (currentTabIndex != 0) {
            radioGroup.check(R.id.rbtn_home);
            return;
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                AppManager.getAppManager().finishAllActivity();
                System.exit(0);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
