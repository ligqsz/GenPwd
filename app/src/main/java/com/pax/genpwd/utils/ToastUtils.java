package com.pax.genpwd.utils;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pax.genpwd.GenPwdApp;
import com.pax.genpwd.R;

/**
 * @author ligq
 * @date 2018/5/22
 */

public class ToastUtils {
    private static Toast mToast;

    private ToastUtils() {
        throw new IllegalArgumentException();
    }

    @SuppressWarnings("SameParameterValue")
    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(GenPwdApp.getApp(), msg, Toast.LENGTH_SHORT);
            mToast.setText(msg);
            View view = mToast.getView();
            view.setBackground(ContextCompat.getDrawable(GenPwdApp.getApp(), R.drawable.rect_toast_bg));
            view.setPadding(20, 20, 20, 20);
            TextView textView = view.findViewById(android.R.id.message);
            textView.setTextSize(25);
            textView.setTextColor(ContextCompat.getColor(GenPwdApp.getApp(), R.color.white));
            mToast.setView(view);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.show();
            return;
        }
        mToast.setText(msg);
        mToast.show();
    }

}
