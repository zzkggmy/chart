package com.example.chart;

import android.content.Context;

public class LibUtils {

    public static float dip2px(Context context,double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (float) (dpValue * scale + 0.5d);
    }
}