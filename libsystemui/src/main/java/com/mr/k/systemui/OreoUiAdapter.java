package com.mr.k.systemui;

import com.mr.k.systemui.uitils.Logger;


public class OreoUiAdapter extends MarshmallowUiAdapter {


    public OreoUiAdapter(SystemBarConfig config) {
        super(config);
    }

    @Override
    void hideStatusBar() {
        super.hideStatusBar();
        setUseNotch();
    }

    void setUseNotch(){
        Manufacturer manufacturer = Manufacturer.getManufacture();
      INotchAdapter adapter = null;
        switch (manufacturer){

            case OPPO:{
                adapter = new OppoAdapter();
                break;
            }
            case VIVO:{
                adapter = new VivoAdapter();
                break;
            }

            case HUAWEI:{
                adapter = new HuaWeiAdapter();
                break;
            }
            case XIAOMI:{
                adapter = new XiaoMiAdapter();
                break;
            }
            default:{
                Logger.d("该手机还没有对刘海区域做适配，请联系作者");
                return;
            }
        }

        adapter.setNotchUse(config.getActivity(),config.isHideStatusBar());
    }
}
