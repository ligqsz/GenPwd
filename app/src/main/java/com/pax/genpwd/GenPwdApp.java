package com.pax.genpwd;

import android.app.Application;

/**
 * @author ligq
 * @date 2018/5/22
 */

public class GenPwdApp extends Application {
    private static GenPwdApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static GenPwdApp getApp() {
        return app;
    }
}
