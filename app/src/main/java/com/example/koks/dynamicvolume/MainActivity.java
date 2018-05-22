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
    private Button Plus, Minus, Next, Prev, Play;
    long eventtime = SystemClock.uptimeMillis();

    public MainActivity(){
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, IbusService.class));

        final AudioManager audioManager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        Plus = findViewById(R.id.Plus);
        Minus = findViewById(R.id.Minus);
        Next = findViewById(R.id.Next);
        Prev = findViewById(R.id.Prev);
        Play = findViewById(R.id.Play);

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
                Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);

                KeyEvent downEvent = new KeyEvent(eventtime, eventtime,
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PAUSE, 0);
                upIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
                sendOrderedBroadcast(upIntent,null);

                KeyEvent upEvent = new KeyEvent(eventtime, eventtime,
                        KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PAUSE, 0);
                upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
                sendOrderedBroadcast(upIntent,null);

                Play.setText("Pause");

                }else {
                Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);

                KeyEvent downEvent = new KeyEvent(eventtime, eventtime,
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY, 0);
                upIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
                sendOrderedBroadcast(upIntent,null);

                KeyEvent upEvent = new KeyEvent(eventtime, eventtime,
                        KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY, 0);
                upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
                sendOrderedBroadcast(upIntent,null);

                Play.setText("Play");
                }
            }

        });


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
                KeyEvent downEvent = new KeyEvent(eventtime, eventtime,
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT, 0);
                upIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
                sendOrderedBroadcast(upIntent,null);

                KeyEvent upEvent = new KeyEvent(eventtime, eventtime,
                        KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT, 0);
                upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
                sendOrderedBroadcast(upIntent,null);
            }
        });

        Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);

                KeyEvent downEvent = new KeyEvent(eventtime, eventtime,
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
                upIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
                sendOrderedBroadcast(upIntent,null);

                KeyEvent upEvent = new KeyEvent(eventtime, eventtime,
                        KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
                upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
                sendOrderedBroadcast(upIntent,null);
            }
        });
    }
}
