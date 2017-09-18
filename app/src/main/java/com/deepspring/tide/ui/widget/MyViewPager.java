package com.deepspring.tide.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 移动背景图片
 * Created by Anonym on 2017/9/18.
 */

public class MyViewPager extends ViewPager {

    private Bitmap mBitmap;
    private Paint mPaint = new Paint(1);

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if(this.mBitmap != null) {
            int width = this.mBitmap.getWidth();
            int height = this.mBitmap.getHeight();
            int count = getAdapter().getCount();
            int x = getScrollX();
            int n =height * getWidth() /getHeight();

            /**
             * (width - n) / (count - 1)表示除去显示第一个ViewPager页面用去的背景宽度，剩余的ViewPager需要显示的背景图片的宽度。
             * getWidth()等于ViewPager一个页面的宽度，即手机屏幕宽度。在该计算中可以理解为滑动一个ViewPager页面需要滑动的像素值。
             * ((width - n) / (count - 1)) /getWidth()也就表示ViewPager滑动一个像素时，背景图片滑动的宽度。
             * x * ((width - n) / (count - 1)) /  getWidth()也就表示ViewPager滑动x个像素时，背景图片滑动的宽度。
             * 背景图片滑动的宽度的宽度可以理解为背景图片滑动到达的位置。
             */

            int w = x * ((width - n) / (count - 1)) / getWidth();
            canvas.drawBitmap(this.mBitmap, new Rect(w, 0, n + w, height),
                    new Rect( x, 0, x + getWidth(), getHeight()), this.mPaint);
        }
        super.dispatchDraw(canvas);
    }

    public void setBackground(Bitmap paramBitmap) {
        this.mBitmap = paramBitmap;
        this.mPaint.setFilterBitmap(true);
    }

}
