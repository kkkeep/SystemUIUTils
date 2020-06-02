package com.mr.k.systemui;

import android.os.Build;

import androidx.annotation.RequiresApi;


public class PieUiAdapter extends OreoUiAdapter {


    public PieUiAdapter(SystemBarConfig config) {
        super(config);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    void setUseNotch() {

        new DefaultAdapter().setNotchUse(config.getActivity(),config.isUseNotch());
    }
}
