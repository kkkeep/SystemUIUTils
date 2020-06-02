package com.mr.k.systemui;

import android.app.Activity;
import android.content.Context;

import java.lang.reflect.Method;

class HuaWeiAdapter implements INotchAdapter {
    @Override
    public void setNotchUse(Activity activity, boolean use) {
        if (use) {

        } else {

        }
    }

    public static boolean hasNotchInScreen(Context context) {

        boolean ret = false;

        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        }  catch (Exception e) {

        } finally {
            return ret;
        }
    }

}
