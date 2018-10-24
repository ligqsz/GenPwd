package com.pax.genpwd.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * @author ligq
 * @date 2018/6/28
 */

public class Density {

    private Density() {
        throw new IllegalArgumentException();
    }

    public static void adaptScreen(final Activity activity,
                                   final float sizeInDp,
                                   final boolean isVerticalSlide) {
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        DisplayMetrics appDm = activity.getApplication().getResources().getDisplayMetrics();
        float appDmScaleDensity = appDm.scaledDensity;
        float appDmDensity = appDm.density;
        if (isVerticalSlide) {
            activityDm.density = activityDm.widthPixels / sizeInDp;
        } else {
            activityDm.density = activityDm.heightPixels / sizeInDp;
        }
        activityDm.scaledDensity = activityDm.density * (appDmScaleDensity / appDmDensity);
        activityDm.densityDpi = (int) (160 * activityDm.density);
    }

    public static void cancelAdaptScreen(final Activity activity) {
        final DisplayMetrics appDm = activity.getApplication().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        activityDm.density = appDm.density;
        activityDm.scaledDensity = appDm.scaledDensity;
        activityDm.densityDpi = appDm.densityDpi;
    }
}
