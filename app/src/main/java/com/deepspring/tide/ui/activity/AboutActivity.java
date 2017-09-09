package com.deepspring.tide.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.deepspring.tide.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * todo-list:优先级1：about页面其余几个布局，顶部渐变style，
 * todo-list:优先级2：scrollview按钮，返回按钮没效果，字体，颜色
 */
public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        mToolbar.setTitle("关于");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
