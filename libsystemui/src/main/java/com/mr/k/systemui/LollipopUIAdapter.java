package com.mr.k.systemui;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

public class LollipopUIAdapter extends KitKatUiAdapter {


    public LollipopUIAdapter(SystemBarConfig config) {
        super(config);


    }


    @Override
    void hideStatusBar() {
        super.hideStatusBar();

    }

    @Override
    void contentBehindStatusBar() {
        if (config.isContentBehindStatusBar()) {
            flag |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    void setStatusBarColor() {
        if (config.getStatusBarColor() != SystemBarConfig.NO_COLOR) {
            window().setStatusBarColor(config.getStatusBarColor());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    void setNavigationBarColor() {
        if (config.getNavBarColor() != SystemBarConfig.NO_COLOR) {
            window().setNavigationBarColor(config.getNavBarColor());
        }
    }

}
