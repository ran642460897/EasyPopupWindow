package com.mxjapp.easypopupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

/**
 * user: Jason Ran
 * date: 2018/8/10.
 */
public class EasyPopupWindow extends PopupWindow{
    private View darkView;
    private View bindView;
    private WindowManager windowManager;
    private Point point=new Point();

    public EasyPopupWindow(View view,View bindView) {
        if(view==null||bindView==null) return;
        this.bindView = bindView;
        initDarkView();
        initWindowManager(view.getContext());
    }
    private void initWindowManager(Context context){
        windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(windowManager!=null) windowManager.getDefaultDisplay().getSize(point);
    }
    private void initDarkView(){
        darkView = new View(bindView.getContext());
        darkView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        darkView.setBackgroundColor(Color.parseColor("#a0000000"));
    }
    private void removeDarkView(){
        windowManager.removeViewImmediate(darkView);
    }
    private void addDarkView(){
        int[] location = new  int[2] ;
        bindView.getLocationInWindow(location);
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.gravity = Gravity.START | Gravity.BOTTOM;
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height =point.y- location[1];
        p.format = PixelFormat.TRANSLUCENT;
        p.type = WindowManager.LayoutParams.LAST_SUB_WINDOW;
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED;
        windowManager.addView(darkView, p);
    }
    public void show(){
        showAsDropDown(bindView);
        addDarkView();
    }
}
