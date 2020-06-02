package com.mr.k.systemui;

import android.app.Activity;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class XiaoMiAdapter implements ILightModeAdapter, INotchAdapter {

   /**
    * MIUI的沉浸支持透明白色字体和透明黑色字体
    * https://dev.mi.com/console/doc/detail?pId=1159
    */
   @Override
   public int setLightMode(Activity activity, boolean lightMode,int flag) {
       try {
           Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");

           Window window = activity.getWindow();
           Class<? extends Window> clazz = activity.getWindow().getClass();
           Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
           int darkModeFlag = field.getInt(layoutParams);

           Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);

           extraFlagField.invoke(activity.getWindow(), lightMode ? darkModeFlag : 0, darkModeFlag);

       } catch (Exception e) {
           // e.printStackTrace();
       }
       return flag;
   }

   @Override
   public void setNotchUse(Activity activity,boolean use) {

   }
}
