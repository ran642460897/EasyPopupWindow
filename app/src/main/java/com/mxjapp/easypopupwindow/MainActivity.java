package com.mxjapp.easypopupwindow;

import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxjapp.popupwindow.EasyPopupWindow;

public class MainActivity extends AppCompatActivity {
    private EasyPopupWindow popupWindow;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        textView=findViewById(R.id.test);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.show();
            }
        });
        popupWindow=new EasyPopupWindow(R.layout.popup_test,textView);
        popupWindow.setExtendDarkHeight(false);
        popupWindow.setBackgroundColor(Color.RED);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
    }
}
