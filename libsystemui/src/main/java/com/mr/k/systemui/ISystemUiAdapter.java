package com.mr.k.systemui;

import android.view.Window;

public  abstract class ISystemUiAdapter {
    protected SystemBarConfig config;
    protected int oldFlag;

    protected  int flag = 0;

    public ISystemUiAdapter(SystemBarConfig config) {
        this.config = config;
    }

    public  void apply(){
        flag = 0;
        hideStatusBar();
        contentBehindStatusBar();

        setStatusBarLightMode();

        hideNavigationBar();
        contentBehindNavigationBar();
        setFullMode();
        setSystemUiVisibility(flag);
        
        setStatusBarColor();
        setNavigationBarColor();
    }



    protected Window window() {
        return config.getActivity().getWindow();
    }

    protected int getSystemUiVisibility(){
        return window().getDecorView().getSystemUiVisibility();
    }

    protected void setSystemUiVisibility(int visibility){
        window().getDecorView().setSystemUiVisibility(visibility);
    }

    abstract void hideStatusBar();
    abstract void contentBehindStatusBar();
    abstract void setStatusBarColor();

    abstract void setStatusBarLightMode();


    abstract void hideNavigationBar();

    abstract void contentBehindNavigationBar();

    abstract void setNavigationBarColor();

    abstract void setFullMode();


}
