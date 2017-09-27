package com.deepspring.tide.server;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.deepspring.tide.ui.activity.MainActivity;

/**
 * Created by Anonym on 2017/9/22.
 */

public class MusicService extends Service {

    public MainActivity mActivity = new MainActivity();

    public final Binder mBinder = new MyBinder();

    public final Uri uri0 = null;
    public final Uri uri1 = Uri.parse("android.resource://com.deepspring.tide/raw/rain");
    public final Uri uri2 = Uri.parse("android.resource://com.deepspring.tide/raw/forest");
    public final Uri uri3 = Uri.parse("android.resource://com.deepspring.tide/raw/wave");
    public final Uri uri4 = Uri.parse("android.resource://com.deepspring.tide/raw/classic");

    public MediaPlayer mp = new MediaPlayer();

    public class MyBinder extends Binder {
        public MusicService getMusicService() {
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * UI中 继续和开始 都直接使用play
     * @param
     */
    public void play() {
        switch (mActivity.mPosition) {
            case 0:
                break;
            case 1:
                mp = MediaPlayer.create(this,uri1);
                mp.start();
                break;
            case 2:
                mp = MediaPlayer.create(this,uri2);
                mp.start();
                break;
            case 3:
                mp = MediaPlayer.create(this,uri3);
                mp.start();
                break;
            case 4:
                mp = MediaPlayer.create(this,uri4);
                mp.start();
                break;
            default:
        }
    }

    /**
     * 暂停、放弃
     */
    public void pause() {
        mp.stop();
    }
}
