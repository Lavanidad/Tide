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

public class MusicService extends Service {

    public final Binder mBinder = new MyBinder();

    public static MediaPlayer mediaPlayer = new MediaPlayer();

    public class MyBinder extends Binder {
        public MusicService getMusicServer() {
            return MusicService.this;
        }
    }


    /**
     * UI中 继续和开始 都直接使用play
     * @param path
     */
    public void play(String path) {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(path);
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
