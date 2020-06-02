package com.mr.k.systemui;

import android.app.Activity;

import com.mr.k.systemui.uitils.Logger;
import com.mr.k.systemui.uitils.SystemFacade;


public class SystemBarConfig {

    // https://developer.android.google.cn/training/system-ui/immersive


    public static final int MODE_IMMERSIVE = 0x101;
    // 并且该模式下即使 contentBehindStatusBar 和 contentBehindNavBar 为false，一旦 导航栏显示时，内容也是在导航栏后面。这点和 其他两个模式不一样。
    /**
     * 该模式下 修改状态栏和导航栏颜色无效，
     *  如果模式为该模式，有一下情况：
     *      1. 如果 设置了 hideStatusBar = true , 那么 contentBehindStatusBar 默认为true, 即使调用 contentBehindStatusBar（false） 也没用
     *      2. 如果 设置了 hideNavBar = true ,那么 contentBehindNavBar 默认为true ,即使调用了 contentBehindNavBar（false） 也没用
     */
    public static final int MODE_IMMERSIVE_STICKY = 0x103;

    public static final int MODE_HIDE_LEAN_BACK = 0x104;
    public static final int NO_COLOR = -2;

    private Activity activity;
    private int statusBarColor = NO_COLOR;
    private int navBarColor = NO_COLOR;

    private boolean contentBehindStatusBar = false;
    private boolean contentBehindNavBar = false;
    // true 状态栏字体为黑色，false  状态栏字体为白色
    private boolean statusBarLightMode = false;

    private boolean hideStatusBar = false;
    private boolean hideNavBar = false;

    private boolean isKeepLayoutStable = false;

    private int hideMode = MODE_HIDE_LEAN_BACK;

    private boolean isUseNotch = true;

    private boolean isHideSystemUi;

    public SystemBarConfig(Activity activity) {
        this.activity = activity;
    }





    /**
     * 给状态栏设置指定的颜色
     * @param color
     * @return
     */
    public SystemBarConfig setStatusBarColor(int color) {
        this.statusBarColor = color;
        return this;
    }

    /**
     * 设置状态栏的的模式。 非小米和魅族手机只有 android 6.0 才开始支持，小米和 魅族 4.4 以上即可支持
     * @param lightMode true 表示亮色模式，对应的状态栏字体和图标就会变为灰色，反之 false 字体和图标变成白色
     * @return
     */
    public SystemBarConfig setStatusBarLightMode(boolean lightMode) {
        this.statusBarLightMode = lightMode;
        return this;
    }

    /**
     *
     * @param behind : true 表示内容在状态栏后面(不是下面)。false 相反.
     * 注意：  如果  状态栏被设置为隐藏 hideStatusBar = true，并且设置了 Immersive sticky 模式，那么该方法将会失去作用 默认为contentBehindStatusBar = true 的效果
     * @return
     */
    public SystemBarConfig setContentBehindStatusBar(boolean behind) {
        this.contentBehindStatusBar = behind;
        return this;
    }

    /**
     * 给底部导航栏设置指定的颜色，导航栏的隐藏模式设置了 immersive  sticky ，导航栏颜色无法修改。
     * @param color
     * @return
     */
    public SystemBarConfig setNavBarColor(int color){
        this.navBarColor = color;
        return this;
    }
    /**
     *
     * @param behind : true 表示内容在导航栏后面(不是下面)。false 相反
     *  注意：1. 如果一旦设置了该属性为true, 系统会自动设置内容也在状态栏后面，即设置 View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
     *       2. 如果  导航栏被设置为隐藏 hideNavBar = true，并且设置了 Immersive sticky 模式，那么该方法将会失去作用。默认为contentBehindNavBar = true 的效果
     * @return
     */
    public SystemBarConfig setContentBehindNavBar(boolean behind){
        this.contentBehindNavBar = behind;
        if(behind){
            Logger.w("让内容显示在导航栏的后面，会默认自动设置内容也显示在状态栏后面");
        }
        return this;
    }

    /**
     * 隐藏系统UI（状态栏 + 导航栏）一共有三种模式，<p />
     *
     * 1. Lean back 。 用户按屏幕任意位置， 系统UI 显示。如果再想显示，必须重新调用隐藏方法才能再次隐藏 <p />
     * 2. Immersive . 用户按下状态栏并且往下滑动，或者按下导航啦网上滑动 才能显示  system ui，显示后必须重新调用隐藏方法才能再次隐藏<p />
     * 3. Immersive sticky 和 第二种模式一样，只是 几秒种后会自动再次隐藏,
     *      并且设置为false 无效
     *
     * @param mode
     * @return
     */
    public SystemBarConfig setHideMode(int mode){
        this.hideMode = mode;
        return this;
    }

    /**
     *  是否隐藏状态栏
     * @param hide ，true 隐藏，false 不隐藏
     * @return
     */
    public SystemBarConfig setHideStatusBar(boolean hide){
        this.hideStatusBar = hide;
        return this;
    }
    /**
     *  是否隐藏底部导航栏
     * @param hide ，true 隐藏，false 不隐藏
     * @return
     */
    public SystemBarConfig setHideNavBar(boolean  hide){
        this.hideNavBar = hide;
        return this;
    }


    /**
     * 是否保持布局稳定，当 状态栏和导航栏在显示和隐藏状态之间切换时，是否保持布局大小不变。
     * @param stable
     * @return
     */
    public SystemBarConfig keepLayoutStable(boolean stable){
        this.isKeepLayoutStable = stable;
        return this;
    }


    public SystemBarConfig enterFullScreen(int mode){
        isHideSystemUi =true;
        hideMode = mode;
        return this;
    }

    public SystemBarConfig existFullScreen(){

        contentBehindNavBar =true;
        contentBehindStatusBar = true;
        isUseNotch = true;
        return this;
    }


    public SystemBarConfig setUseNotch(boolean useNotch) {
        isUseNotch = useNotch;

        return this;
    }

     boolean isUseNotch() {
        return isUseNotch;
    }

    int getNavBarColor() {
        return navBarColor;
    }

     boolean isContentBehindNavBar() {
        return contentBehindNavBar;
    }

     boolean isHideStatusBar() {
        return hideStatusBar;
    }

     boolean isHideNavBar() {
        return hideNavBar;
    }

     int getHideMode() {
        return hideMode;
    }
     boolean isKeepLayoutStable(){
        return isKeepLayoutStable;
    }

    public void apply() {

        if(isHideSystemUi){ // 只要设置了进入全屏，强制设置一下值得行为
            hideStatusBar = true;
            hideNavBar = true;
            contentBehindNavBar =true;
            contentBehindStatusBar = true;
            isUseNotch =true;
            if(getHideMode() == MODE_IMMERSIVE_STICKY){
                statusBarColor = NO_COLOR;
                navBarColor = NO_COLOR;
            }
        }

        ISystemUiAdapter statusBarAdapter  = null;
        if (SystemFacade.hasP()) {
            statusBarAdapter = new PieUiAdapter(this);
        } else if (SystemFacade.hasO()) {
            statusBarAdapter = new OreoUiAdapter(this);
        } else if (SystemFacade.hasM()) {
            statusBarAdapter = new MarshmallowUiAdapter(this);
        } else if (SystemFacade.hasLollipop()) {
            statusBarAdapter = new LollipopUIAdapter(this);
        } else if (SystemFacade.hasKitKat()) {
            statusBarAdapter = new KitKatUiAdapter(this);
        }

        if (statusBarAdapter != null) {
            statusBarAdapter.apply();
        }

    }

     Activity getActivity() {
        return activity;
    }

     int getStatusBarColor() {
        return statusBarColor;
    }

     boolean isContentBehindStatusBar() {
        return contentBehindStatusBar;
    }

     boolean isStatusBarLightMode() {
        return statusBarLightMode;
    }


    public static SystemBarConfig create(Activity activity){
        return new SystemBarConfig(activity);
    }



}
