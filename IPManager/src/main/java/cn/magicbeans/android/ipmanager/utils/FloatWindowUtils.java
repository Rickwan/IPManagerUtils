package cn.magicbeans.android.ipmanager.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import cn.magicbeans.android.ipmanager.R;
import cn.magicbeans.android.ipmanager.ui.MBIPActivity;

/**
 * author wanqiang
 * date 17/5/20
 * desc${TODO}
 */

public class FloatWindowUtils {

    public void init(final Activity activity) {
        final View floatView = LayoutInflater.from(activity).inflate(R.layout.flaot_layout, null);
        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MBIPActivity.class);
                activity.startActivityForResult(intent, MBIPContant.REQUEST_CODE);
            }
        });

        final WindowManager windowManager = activity.getWindowManager();
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        final int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        params.x =width;
        params.y = 0;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.RGBA_8888;

        floatView.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;
            int paramX, paramY;

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        paramX = params.x;
                        paramY = params.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        params.x = paramX + dx;
                        params.y = paramY + dy;
                        windowManager.updateViewLayout(floatView, params);
                        break;

                    case MotionEvent.ACTION_UP:
                        if (params.x<0){
                            params.x = -width;
                        }else {
                            params.x = width;
                        }
                        windowManager.updateViewLayout(floatView, params);
                        break;
                }
                return false;
            }
        });
        windowManager.addView(floatView, params);
    }


}
