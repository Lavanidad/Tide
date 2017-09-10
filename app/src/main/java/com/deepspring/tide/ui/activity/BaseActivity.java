package com.deepspring.tide.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.deepspring.tide.R;

/**
 * Created by Anonym on 2017/9/9.
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    public abstract boolean onCreateOptionsMenu(Menu menu);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("id---------",item.getItemId()+"---"+R.id.menu_about);
        switch (item.getItemId()) {
            case R.id.menu_about:
                Intent intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
}
