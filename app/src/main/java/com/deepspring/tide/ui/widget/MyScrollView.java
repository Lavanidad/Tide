package com.deepspring.tide.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
/**
 * Created by Anonym on 2017/9/11.
 */

public class MyScrollView extends ScrollView  {

    private TranslucentListener listener;

    public void setTranslucentListener(TranslucentListener listener) {
        this.listener = listener;
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            int scrollY = getScrollY();
            int screen_height = getContext().getResources().getDisplayMetrics().heightPixels;
            if (scrollY <= screen_height / 3f) {//0~1f,而透明度应该是1~0f
                listener.onTranlucent(1 - scrollY / (screen_height / 3f));//alpha=滑出去的高度/(screen_height/3f)
            }
        }
    }
}