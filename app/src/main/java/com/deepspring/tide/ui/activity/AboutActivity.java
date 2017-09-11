package com.deepspring.tide.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.deepspring.tide.R;
import com.deepspring.tide.ui.widget.MyScrollView;
import com.deepspring.tide.ui.widget.TranslucentListener;

/**
 * todo-list:优先级2：顶部透明没效果，自定义滑动效果微调
 * todo-list:优先级3：底部导航颜色、位置
 * todo-list:优先级4：navBt全屏幕时的点击事件
 */
public class AboutActivity extends AppCompatActivity implements TranslucentListener {


    private Toolbar mToolbar;
    private MyScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_2);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("关于");
        setSupportActionBar(mToolbar);

        ActionBar mActionBar = getSupportActionBar();
        if(mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mScrollView = (MyScrollView) findViewById(R.id.scrollView);
        mScrollView.setTranslucentListener(this);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onTranlucent(float alpha) {
        mToolbar.setAlpha(1-alpha);
    }
}
