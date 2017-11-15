package com.deepspring.tide.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.deepspring.tide.R;

/**
 * Created by Anonym on 2017/11/15.
 */

public class MyCircleProgress extends ProgressBar {

    //定义一些属性常量
    private static final int PROGRESS_DEFAULT_COLOR = 0xCCd3d6da;//默认圆(边框)的颜色
    private static final int PROGRESS_REACHED_COLOR = 0XFFFFFFFF;//进度条的颜色
    private static final int PROGRESS_REACHED_HEIGHT = 3;//进度条的高度
    private static final int PROGRESS_DEFAULT_HEIGHT = 3;//默认圆的高度
    private static final int PROGRESS_RADIUS = 125;//圆的半径

    //文字
    private static final int TEXT_COLOR = Color.parseColor("#CCd3d6da");
    private static final int TEXT_SIZE = 48;

    //View的当前状态，默认为未开始
    private Status mStatus = Status.End;
    //画笔
    private Paint mPaint;
    //文字画笔
    private Paint mTextPaint;
    //进度条的颜色
    private int mReachedColor = PROGRESS_REACHED_COLOR;
    //进度条的高度
    private int mReachedHeight = dp2px(PROGRESS_REACHED_HEIGHT);
    //默认圆(边框)的颜色
    private int mDefaultColor = PROGRESS_DEFAULT_COLOR;
    //默认圆(边框)的高度
    private int mDefaultHeight = dp2px(PROGRESS_DEFAULT_HEIGHT);
    //圆的半径
    private int mRadius = dp2px(PROGRESS_RADIUS);
    //默认文字的颜色
    private int textColor = TEXT_COLOR;
    //默认文字的大小
    private int textSize = sp2px(TEXT_SIZE);

    private String textDesc = "欢迎";

    public MyCircleProgress(Context context) {
        this(context,null);
    }

    public MyCircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性的值
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCircleProgress);
        //默认圆的颜色
        mDefaultColor = array.getColor(R.styleable.CustomCircleProgress_progress_default_color, PROGRESS_DEFAULT_COLOR);
        //进度条的颜色
        mReachedColor = array.getColor(R.styleable.CustomCircleProgress_progress_reached_color, PROGRESS_REACHED_COLOR);
        //默认圆的高度
        mDefaultHeight = (int) array.getDimension(R.styleable.CustomCircleProgress_progress_default_height, mDefaultHeight);
        //进度条的高度
        mReachedHeight = (int) array.getDimension(R.styleable.CustomCircleProgress_progress_reached_height, mReachedHeight);
        //圆的半径
        mRadius = (int) array.getDimension(R.styleable.CustomCircleProgress_circle_radius, mRadius);
        //最后不要忘了回收 TypedArray
        array.recycle();

        //设置画笔（new画笔的操作一般不要放在onDraw方法中，因为在绘制的过程中onDraw方法会被多次调用）
        setPaint();
    }

    private void setPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
    }

    /**
     * 使用onMeasure方法是因为我们的自定义圆形View的一些属性(如：进度条宽度等)都交给用户自己去自定义了，所以我们需要去测量下
     * 看是否符合要求
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int paintHeight = Math.max(mReachedHeight, mDefaultHeight);//比较两数，取最大值

        if(heightMode != MeasureSpec.EXACTLY){
            //如果用户没有精确指出宽高时，我们就要测量整个View所需要分配的高度了，测量自定义圆形View设置的上下内边距+圆形view的直径+圆形描边边框的高度
            int exceptHeight = getPaddingTop() + getPaddingBottom() + mRadius*2 + paintHeight;
            //然后再将测量后的值作为精确值传给父类，告诉他我需要这么大的空间，你给我分配吧
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight, MeasureSpec.EXACTLY);
        }
        if(widthMode != MeasureSpec.EXACTLY){
            //这里在自定义属性中没有设置圆形边框的宽度，所以这里直接用高度代替
            int exceptWidth = getPaddingLeft() + getPaddingRight() + mRadius*2 + paintHeight;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getPaddingLeft(),getPaddingTop());
        mPaint.setStyle(Paint.Style.STROKE);
        //画默认圆(边框)的一些设置
        mPaint.setColor(mDefaultColor);
        mPaint.setStrokeWidth(mDefaultHeight);
        canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);
        //画进度条的一些设置
        mPaint.setColor(mReachedColor);
        mPaint.setStrokeWidth(mReachedHeight);
        //根据进度绘制圆弧
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius *2), -90, sweepAngle, false, mPaint);
        canvas.restore();

    }

    protected int dp2px(int dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    protected int sp2px(int spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

    //当前view显示的状态
    public enum Status{
        End,
        Starting
    }

    public Status getStatus(){
        return mStatus;
    }
    public void setStatus(Status status){
        this.mStatus = status;
        invalidate();
    }
}
