package com.mr.k.systemui;

import android.os.Build;

public enum Manufacturer {

    XIAOMI, HUAWEI, MEIZU, OPPO, VIVO,UNKNOW;



    public static Manufacturer getManufacture(){
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        if (manufacturer.contains("xiaomi")) {
            return XIAOMI;
        } else if (manufacturer.contains("meizu")) {
           return MEIZU;
        }else if(manufacturer.contains("huawei")){
            return HUAWEI;
        }else if(manufacturer.contains("vivo")){
            return VIVO;
        }else if(manufacturer.contains("oppo")){
            return OPPO;
        }else{
            return UNKNOW;
        }

    }
}
