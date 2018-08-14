package com.mxjapp.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private OnDismissListener onDismissListener;
    private  boolean extendDarkHeight=false;
    private int backgroundColor=Color.parseColor("#a0000000");

    public EasyPopupWindow(View view, View bindView) {
        if(view==null||bindView==null) return;
        this.bindView = bindView;
        initWindowManager(view.getContext());
        initView(view);
    }
    public EasyPopupWindow(int resId,View bindView){
        if(bindView==null) return;
        this.bindView=bindView;
        initWindowManager(bindView.getContext());
        initView(LayoutInflater.from(bindView.getContext()).inflate(resId,(ViewGroup) bindView.getParent(),false));
    }
    private void initView(View view){
        setContentView(view);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hideDarkView();
                if(onDismissListener!=null) onDismissListener.onDismiss();
            }
        });
        setOutsideTouchable(true);
    }
    private void initWindowManager(Context context){
        windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(windowManager!=null) windowManager.getDefaultDisplay().getSize(point);
    }
    private void hideDarkView(){
        if(darkView!=null) darkView.setVisibility(View.GONE);
    }
    private void showDarkView(){
        if(darkView==null) {
            darkView = new View(bindView.getContext());
            darkView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            darkView.setBackgroundColor(backgroundColor);
            int contentHeight=0;
            if(!extendDarkHeight) {
                getContentView().measure(0, 0);
                contentHeight=getContentView().getMeasuredHeight();
            }
            int[] location = new int[2];
            bindView.getLocationInWindow(location);
            WindowManager.LayoutParams p = new WindowManager.LayoutParams();
            p.gravity = Gravity.START | Gravity.BOTTOM;
            p.width = WindowManager.LayoutParams.MATCH_PARENT;
            p.height = point.y - location[1] - bindView.getMeasuredHeight() - contentHeight;
            p.format = PixelFormat.TRANSLUCENT;
            p.type = WindowManager.LayoutParams.LAST_SUB_WINDOW;
            p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED;
            windowManager.addView(darkView, p);
        }
        darkView.setVisibility(View.VISIBLE);
    }
    public void show(){
        showAsDropDown(bindView);
        showDarkView();
    }

    public void setExtendDarkHeight(boolean extendDarkHeight) {
        this.extendDarkHeight = extendDarkHeight;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public interface OnDismissListener{
        void onDismiss();
    }
}
