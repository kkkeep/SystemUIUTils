package com.mr.k.systemui;

import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;

import com.mr.k.systemui.uitils.Logger;
import com.mr.k.systemui.uitils.SystemFacade;


class KitKatUiAdapter extends ISystemUiAdapter {

    private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
    private static final String TAG_MARGIN_ADDED = "marginAdded";


    public KitKatUiAdapter(SystemBarConfig config) {
        super(config);
    }

    @Override
    public void apply() {
        removeFakeStatusBar();
        addMarginTopToContentChild(0);
        super.apply();
    }

    @Override
    void hideStatusBar() {
        if (config.isHideStatusBar()) {
            flag = flag | View.SYSTEM_UI_FLAG_FULLSCREEN;
        }


    }


    @Override
    void contentBehindStatusBar() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    void setStatusBarColor() {
        // 为了保证 和 5.0 之后的版本一致，因为5.0 开始，只要设置了内容在导航栏之后，内容同样也会设置到状态栏之后。
        if(config.isContentBehindNavBar()){
            config.setContentBehindStatusBar(true);
        }

        if (config.isHideStatusBar()) { // 4.4 只要是隐藏状态栏都不支持状态栏颜色修改.

            Logger.w("android 4.4 在状态栏隐藏的情况下，不支持设置状态栏颜色");


            // 不支持颜色方案---------------------------
            if (config.isContentBehindStatusBar()) {

                window().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
              //  setFakeStatusBarColor(config.getStatusBarColor());
              //  addMarginTopToContentChild(0);
            } else {
                window().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
               // window().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //setFakeStatusBarColor(config.getStatusBarColor());
               // addMarginTopToContentChild(getStatusBarHeight());


            }
            // 不支持颜色方案---------------------------



            // 支持颜色方案 ------------------

          /*  if (config.isContentBehindStatusBar()) {

                window().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                  setFakeStatusBarColor(config.getStatusBarColor());
                  addMarginTopToContentChild(0);
            } else {
                //window().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                setFakeStatusBarColor(config.getStatusBarColor());
                 addMarginTopToContentChild(getStatusBarHeight());
            }*/


            // 支持颜色方案 ------------------

        } else {
            if (config.isContentBehindStatusBar()) {
                window().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if (config.getStatusBarColor() != SystemBarConfig.NO_COLOR && config.getStatusBarColor() != Color.TRANSPARENT) { // 这是了颜色，并且不是透明，
                    setFakeStatusBarColor(config.getStatusBarColor());
                    //addMarginTopToContentChild(0);
                } else {
                    // 没设置颜色，或者颜色为透明时，什么都不做
                }

            } else {
                if (config.getStatusBarColor() == SystemBarConfig.NO_COLOR) { //  如果没有设置颜色。并且内容在状态栏下面
                    window().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 如果之前这是了这个标识，那么清楚标识

                } else {
                    window().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    setFakeStatusBarColor(config.getStatusBarColor());
                    addMarginTopToContentChild(getStatusBarHeight());// 设置内容的margin 让其到状态栏下面来。

                    window().getDecorView().postInvalidate();

                }
            }
        }


    }


    private void fitSystemWindow(boolean fit){
        ViewGroup mContentView = window().findViewById(Window.ID_ANDROID_CONTENT);
        //获取父布局
        View mContentChild = mContentView.getChildAt(0);

        if(mContentChild != null){
            mContentChild.setFitsSystemWindows(fit);
        }
    }
    @Override
    void setStatusBarLightMode() {
        ILightModeAdapter adapter = null;
        Manufacturer manufacture = Manufacturer.getManufacture();

        switch (manufacture) {
            case XIAOMI: {
                adapter = new XiaoMiAdapter();
                break;
            }

            case MEIZU: {
                adapter = new MeizuAdapter();
                break;
            }
            default: {
                Logger.d("该手机版本不支持修改状态栏 模式");
                return;
            }

        }
        adapter.setLightMode(config.getActivity(), config.isStatusBarLightMode(), 0);
    }

    @Override
    void hideNavigationBar() {
        if (config.isHideNavBar()) {
            flag |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
    }

    @Override
    void contentBehindNavigationBar() {
        if (config.isContentBehindNavBar()) {
            flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        }
    }

    @Override
    void setNavigationBarColor() {

    }

    @Override
    void setFullMode() {
        switch (config.getHideMode()) {
            case SystemBarConfig.MODE_IMMERSIVE: {
                flag |= View.SYSTEM_UI_FLAG_IMMERSIVE;

                break;
            }
            case SystemBarConfig.MODE_IMMERSIVE_STICKY: {
                flag |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                break;
            }
        }
    }


    private int getStatusBarHeight() {
        int height = SystemFacade.getStatusBarHeight(config.getActivity());

        if (height == 0) {
            int result = 0;
            int resId = config.getActivity().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) {
                height = config.getActivity().getResources().getDimensionPixelOffset(resId);
            }
        }

        return height;

    }

    private void addMarginTopToContentChild(int statusBarHeight) {
        Logger.d("给 status bar 设置 margin %s ",statusBarHeight  );
        ViewGroup mContentView = window().findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView == null) {
            return;
        }

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();

        lp.topMargin = statusBarHeight;
        mChildView.setLayoutParams(lp);
    }

    private void setFakeStatusBarColor(int color) {


        ViewGroup mDecorView = (ViewGroup) window().getDecorView();
        View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView == null) {
            Logger.d(" 添加 fake status bar 并设置颜色 %s",color );
            fakeView = new View(config.getActivity());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
            layoutParams.gravity = Gravity.TOP;
            fakeView.setLayoutParams(layoutParams);
            fakeView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
            mDecorView.addView(fakeView);
        }else{
            Logger.d(" 在已有的 fake status bar 设置颜色 %s",color );
        }

        fakeView.setBackgroundColor(color);
    }

    private void removeFakeStatusBar() {

        ViewGroup mDecorView = (ViewGroup) window().getDecorView();
        View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView != null) {
            Logger.d("如果 status bar 存在，移除" );
            mDecorView.removeView(fakeView);
        }
    }
}

