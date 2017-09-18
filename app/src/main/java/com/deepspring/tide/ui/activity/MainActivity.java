package com.deepspring.tide.ui.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

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

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    MyViewPager mViewpager;

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
    }

    private void initFragments() {
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new DynamicFragment());
        fragments.add(new RainFragment());
        fragments.add(new ForestFragment());
        fragments.add(new WaveFragment());
        fragments.add(new ClassicFragment());
        ViewFragmentAdapter mAdapter = new ViewFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(mAdapter);
        mViewpager.setBackground(BitmapFactory.decodeResource(getResources(),R.drawable.test));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
