package com.deepspring.tide.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Chronometer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Anonym on 2017/11/15.
 */

public class MyTimeCountDown extends Chronometer {

    private long mTime;
    private long mNextTime;
    private OnTimeCompleteListener mListener;
    private SimpleDateFormat mTimeFormat;

    public MyTimeCountDown(Context context) {
        super(context);
    }

    public MyTimeCountDown(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTimeFormat = new SimpleDateFormat("mm:ss");
        this.setOnChronometerTickListener(listener);
    }

    public void reStart(long time) {
        if (time == -1) {
            mNextTime = mTime;
        } else {
            mTime = mNextTime = time;
        }
        this.start();
    }

    public void reStart() {
        reStart(-1);
    }

    public void TimeContinute() {
        this.start();
    }

    public void TimePause() {
        this.stop();
    }

    public void setTimeFormat(String pattern) {
        mTimeFormat = new SimpleDateFormat(pattern);
    }

    public void setOnTimeCompleteListener(OnTimeCompleteListener l) {
        mListener = l;
    }

    OnChronometerTickListener listener = new OnChronometerTickListener() {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            if (mNextTime <= 0) {
                if (mNextTime == 0) {
                    MyTimeCountDown.this.stop();
                    if (null != mListener)
                        mListener.onTimeComplete();
                }
                mNextTime = 0;
                updateTimeText();
                return;
            }
            mNextTime--;
            updateTimeText();
        }
    };

    public void initTime(long time) {
        mTime = mNextTime = time;
        updateTimeText();
    }

    private void updateTimeText() {
        this.setText(mTimeFormat.format(new Date(mNextTime * 1000)));
    }

    interface OnTimeCompleteListener {
        void onTimeComplete();
    }
}
