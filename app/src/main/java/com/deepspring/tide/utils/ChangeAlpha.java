package com.deepspring.tide.utils;

import android.graphics.Color;

/**
 * Created by Anonym on 2017/9/11.
 */

public class ChangeAlpha {
    /** 根据百分比改变颜色透明度 */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }
}
