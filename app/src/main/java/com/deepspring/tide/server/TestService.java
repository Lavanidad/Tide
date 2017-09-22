package com.deepspring.tide.server;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by Anonym on 2017/9/22.
 */

public class TestService extends Service{

    public final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        TestService getService() {
            return TestService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public  MediaPlayer mp = new MediaPlayer();


    public TestService() {
        try {
            //mp = MediaPlayer.create(TestService.this, R.raw.forest);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {

            mp.start();

    }
    public void stop() {
        if(mp != null) {
            mp.stop();
            }
    }
}
