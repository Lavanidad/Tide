package com.deepspring.tide.ui.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.deepspring.tide.R;
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
 * todo-list:优先级2：大图片OOM
 * todo-list:优先级1：服务和音乐
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    MyViewPager mViewpager;
    @BindView(R.id.bt_play)
    Button mBtPlay;
    @BindView(R.id.bt_pause)
    Button mBtPause;

    private List<Fragment> mFragments;
    //private MusicService mMusicService;
    //private TestService mTestService;
    private MyServiceConn conn;

    private MediaPlayer mmm;


    private class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
            //mMusicService = myBinder.getMusicServer();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
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

        initFragments();

        //test
        mmm = MediaPlayer.create(this,R.raw.rain);



       // Intent intent = new Intent(this, TestService.class);
        //startService(intent);
       // conn = new MyServiceConn();
       // bindService(intent, conn, BIND_AUTO_CREATE);

        //TODO:OOM TEST
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.d("TAG", "Max memory is " + maxMemory + "KB");
    }


    @OnClick({R.id.bt_play, R.id.bt_pause})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_play:
                mmm.start();//TODO:test musci
                break;
            case R.id.bt_pause:
                mmm.stop();//test
                break;
        }
    }

    private void initFragments() {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new DynamicFragment());
        mFragments.add(new RainFragment());
        mFragments.add(new ForestFragment());
        mFragments.add(new WaveFragment());
        mFragments.add(new ClassicFragment());
        ViewFragmentAdapter mAdapter = new ViewFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewpager.setAdapter(mAdapter);
        //TODO:大图片OOM问题
        mViewpager.setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.test));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unbindService(conn);
    }
}
