package com.example.koks.dynamicvolume;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private long eventime = SystemClock.uptimeMillis();
    private AudioManager audioManager;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, IbusService.class));

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final Button Plus = findViewById(R.id.Plus);
        final Button Minus = findViewById(R.id.Minus);
        final Button Next = findViewById(R.id.Next);
        final Button Prev = findViewById(R.id.Prev);
        final Button Play = findViewById(R.id.Play);

        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, 0);
            }
        });

        Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER, 0);
            }
        });

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audioManager.isMusicActive()){
                    buttonClick( KeyEvent.KEYCODE_MEDIA_PLAY);
                    Play.setText(getString(R.string.play));
                }else {
                    buttonClick(KeyEvent.KEYCODE_MEDIA_PLAY);
                    Play.setText(getString(R.string.pause));
                }
            }

        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(KeyEvent.KEYCODE_MEDIA_NEXT);
            }
        });

        Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
            }
        });
    }

    private void buttonClick(int keyEvent){
        audioManager.dispatchMediaKeyEvent(
                new KeyEvent(eventime, eventime, KeyEvent.ACTION_DOWN, keyEvent,0));
        audioManager.dispatchMediaKeyEvent(
                new KeyEvent(eventime, eventime, KeyEvent.ACTION_UP, keyEvent,0));
    }
}