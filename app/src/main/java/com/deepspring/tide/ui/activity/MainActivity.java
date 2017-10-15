package com.deepspring.tide.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import com.deepspring.tide.R;
import com.deepspring.tide.server.MusicService;
import com.deepspring.tide.ui.adapter.ViewFragmentAdapter;
import com.deepspring.tide.ui.fragment.ClassicFragment;
import com.deepspring.tide.ui.fragment.DynamicFragment;
import com.deepspring.tide.ui.fragment.ForestFragment;
import com.deepspring.tide.ui.fragment.RainFragment;
import com.deepspring.tide.ui.fragment.WaveFragment;
import com.deepspring.tide.ui.widget.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * todo-list:优先级1：后台播放时的通知 && 按钮动画
 * todo-list:优先级3：大图片OOM
 * todo-list:优先级2：奇怪的BUG:服务第一次启动&&切屏过多时短暂失效的bug
 */

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    MyViewPager mViewpager;
    @BindView(R.id.bt_play)
    Button mBtPlay;
    @BindView(R.id.bt_pause)
    Button mBtPause;
    @BindView(R.id.bt_continute)
    Button mBtContinute;
    @BindView(R.id.bt_giveup)
    Button mBtGiveup;

    public static int mPosition;

    private ViewFragmentAdapter mAdapter;
    private List<Fragment> mFragments;
    private MusicService mMusicService;
    private Animation mAnimation;


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mMusicService = ((MusicService.MyBinder)iBinder).getMusicService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mMusicService = null;
        }
    };

    private void bindServiceConnection() {
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, conn, this.BIND_AUTO_CREATE);
    }
    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        initBtn();
        initFragments();
        mMusicService = new MusicService();
        bindServiceConnection();
        //TODO:OOM TEST
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.d("TAG", "Max memory is " + maxMemory + "KB");
    }

    private void initBtn() {
        mBtPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_BUTTON_PRESS)
                {
                    v.setBackgroundResource(R.drawable.shape_play_pressed);
                }else if (event.getAction() == MotionEvent.ACTION_BUTTON_RELEASE){
                    v.setBackgroundResource(R.drawable.shape_play);
                }
                return false;
            }
        });

        mBtPause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_BUTTON_PRESS)
                {
                    v.setBackgroundResource(R.drawable.shape_pause_pressed);
                }else if (event.getAction() == MotionEvent.ACTION_BUTTON_RELEASE){
                    v.setBackgroundResource(R.drawable.shape_pause);
                }
                return false;
            }
        });
        mBtContinute.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_BUTTON_PRESS)
                {
                    v.setBackgroundResource(R.drawable.shape_continute_pressed);
                }else if (event.getAction() == MotionEvent.ACTION_BUTTON_RELEASE){
                    v.setBackgroundResource(R.drawable.shape_continute);
                }
                return false;
            }
        });
        mBtGiveup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_BUTTON_PRESS)
                {
                    v.setBackgroundResource(R.drawable.shape_pause_pressed);
                }else if (event.getAction() == MotionEvent.ACTION_BUTTON_RELEASE){
                    v.setBackgroundResource(R.drawable.shape_pause);
                }
                return false;
            }
        });
    }

    private void initFragments() {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new DynamicFragment());
        mFragments.add(new RainFragment());
        mFragments.add(new ForestFragment());
        mFragments.add(new WaveFragment());
        mFragments.add(new ClassicFragment());
        mAdapter = new ViewFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewpager.setAdapter(mAdapter);
        mViewpager.addOnPageChangeListener(this);
        //TODO:大图片OOM问题
        mViewpager.setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.a3));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.mPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @OnClick({R.id.bt_play, R.id.bt_pause, R.id.bt_continute, R.id.bt_giveup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_play:
                //mAnimation = AnimationUtils.loadAnimation(this,R.anim.play_bt);
                //mBtPlay.startAnimation(mAnimation);
                mMusicService.play();
                mBtPlay.setVisibility(View.GONE);
                mBtPause.setVisibility(View.VISIBLE);
                mBtContinute.setVisibility(View.GONE);
                mBtGiveup.setVisibility(View.GONE);
                break;
            case R.id.bt_pause:
                //mAnimation = AnimationUtils.loadAnimation(this,R.anim.pause_left);
                //mBtPause.startAnimation(mAnimation);
                mMusicService.pause();
                mBtPlay.setVisibility(View.GONE);
                mBtPause.setVisibility(View.GONE);
                mBtContinute.setVisibility(View.VISIBLE);
                mBtGiveup.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_continute:
                //mAnimation = AnimationUtils.loadAnimation(this,R.anim.pause_left);
                //mBtContinute.startAnimation(mAnimation);
                mMusicService.play();
                mBtPlay.setVisibility(View.GONE);
                mBtPause.setVisibility(View.VISIBLE);
                mBtContinute.setVisibility(View.GONE);
                mBtGiveup.setVisibility(View.GONE);
                break;
            case R.id.bt_giveup:
                //mAnimation = AnimationUtils.loadAnimation(this,R.anim.pause_right);
                //mBtGiveup.startAnimation(mAnimation);
                mMusicService.pause();
                mBtPlay.setVisibility(View.VISIBLE);
                mBtPause.setVisibility(View.GONE);
                mBtContinute.setVisibility(View.GONE);
                mBtGiveup.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
