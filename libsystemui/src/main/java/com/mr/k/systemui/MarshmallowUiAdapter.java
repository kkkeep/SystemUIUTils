package com.mr.k.systemui;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class MarshmallowUiAdapter extends LollipopUIAdapter {
    public MarshmallowUiAdapter(SystemBarConfig config) {
        super(config);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    void setStatusBarLightMode() {
        flag = new DefaultAdapter().setLightMode(config.getActivity(), config.isStatusBarLightMode(), flag);
    }
}
