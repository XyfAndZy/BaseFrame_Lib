package com.xyf.baseframe_lib;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;

import com.xyf.baseframe_lib.utlis.PreferencesHelper;
import com.xyf.baseframe_lib.utlis.ToastMgr;

import java.lang.ref.WeakReference;

public class App extends Application {
    public static Handler mHandler = new Handler();
    private static WeakReference<App> instance;
    public static float radius;
//    public static ImagePicker imagePicker;
//    public static UMShareAPI umShareAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        // 设置百度地图，manifest
        instance = new WeakReference<App>(this);
        ToastMgr.init(getApplicationContext());//初始化Toast管理器
        PreferencesHelper.init(getApplicationContext());//初始化share
       /* imagePicker = ImagePicker.getInstance();
        //设置图片加载器
        imagePicker.setImageLoader(new GlideImageLoader());
        //设置显示拍照按钮
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状

        imagePicker.setFocusWidth(600);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(600);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(300);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(300);//保存文件的高度。单位像素
        //集成扫描二维码
        ZXingLibrary.initDisplayOpinion(this);
        //Jpush初始化
        JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        //设置推送最多显示的条数
//        JPushInterface.setLatestNotificationNumber(getContext(),5);
        //友盟分享
        umShareAPI = UMShareAPI.get(this);
        com.umeng.socialize.Config.DEBUG = true;
        PlatformConfig.setWeixin("wx5daa8b7748e27db4", "3116e68eb4b70f151bb144d7dc4c9712");*/

        // 加载系统默认设置，字体不随用户设置变化
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public static App getContext() {
        return (App) instance.get();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }
}


