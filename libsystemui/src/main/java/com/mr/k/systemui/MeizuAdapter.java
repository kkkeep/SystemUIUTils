package com.mr.k.systemui;

import android.app.Activity;
import android.view.WindowManager;

import java.lang.reflect.Field;

class MeizuAdapter implements ILightModeAdapter,INotchAdapter {

   /**
    * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
    */
   @Override
   public int setLightMode(Activity activity, boolean lightMode,int flag) {
       try {
           WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
           Field darkFlag = WindowManager.LayoutParams.class
                   .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
           Field meizuFlags = WindowManager.LayoutParams.class
                   .getDeclaredField("meizuFlags");
           darkFlag.setAccessible(true);
           meizuFlags.setAccessible(true);
           int bit = darkFlag.getInt(null);
           int value = meizuFlags.getInt(lp);
           if (lightMode) {
               value |= bit;
           } else {
               value &= ~bit;
           }
           meizuFlags.setInt(lp, value);
           activity.getWindow().setAttributes(lp);

       } catch (Exception e) {
           //  e.printStackTrace();
       }
       return flag;
   }

   @Override
   public void setNotchUse(Activity activity,boolean use) {

   }
}
