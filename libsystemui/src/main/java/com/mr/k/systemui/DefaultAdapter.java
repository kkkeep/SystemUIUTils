package com.mr.k.systemui;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;


class DefaultAdapter implements ILightModeAdapter, INotchAdapter {


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int setLightMode(Activity activity, boolean lightMode,int flag) {
        if (lightMode) {
            flag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        return  flag;
    }

     @RequiresApi(api = Build.VERSION_CODES.P)
     @Override
     public void setNotchUse(Activity activity,boolean use) {

         WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
         if(use){
             lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
         }else{
             lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
         }
         activity.getWindow().setAttributes(lp);

     }
 }
