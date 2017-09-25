package com.deepspring.tide.server;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.deepspring.tide.R;
import com.deepspring.tide.ui.activity.MusicActivity;

import java.io.IOException;

/**
 * Created by Anonym on 2017/9/22.
 */

public class MusicService extends Service {

    public final Binder mBinder = new MyBinder();

    public static MediaPlayer mediaPlayer = new MediaPlayer();

    public String[] mPath = {
         "android.resource://" + getPackageName() + "/" + R.raw.rain,
         "android.resource://" + getPackageName() + "/" + R.raw.forest,
         "android.resource://" + getPackageName() + "/" + R.raw.wave,
         "android.resource://" + getPackageName() + "/" + R.raw.classic
    };

    public Uri uri0 = Uri.parse(mPath[0]);
    public Uri uri1 = Uri.parse(mPath[1]);
    public Uri uri2 = Uri.parse(mPath[2]);
    public Uri uri3 = Uri.parse(mPath[3]);


    MusicActivity music = new MusicActivity();

    public class MyBinder extends Binder {
        public MusicService getMusicServer() {
            return MusicService.this;
        }
    }



    /**
     * UI中 继续和开始 都直接使用play
     * @param
     */
    public void play(Uri uri) {
        mediaPlayer.reset();
        try {
            //mediaPlayer.setDataSource(uri);
            mediaPlayer.start();
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
    }

    /**
     * 暂停
     */
    public void pause() {
        if(mediaPlayer != null) {
            mediaPlayer.pause();
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


}
