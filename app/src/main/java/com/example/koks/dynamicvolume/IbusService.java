package com.example.koks.dynamicvolume;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class IbusService extends Service {

    static final String TAG = "Battery";
    private int port = 62029;
    DatagramSocket udpSocket = new DatagramSocket(port);
    DatagramPacket packet;
    long eventtime = SystemClock.uptimeMillis();
    String text;
    AudioManager audioManager;

    public IbusService() throws SocketException {
    }

    @Override
    public void onCreate() {
        udpReciever.start();
    }
    //Switch Case to choose what action to take when we have message from ESP device
    void AudioControl () {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        Log.e("Audio", text);

        switch (text) {
            case ("VOLUP"):
                riseVolume(audioManager);
                break;
            case ("VOLDOWN"):
                lowerVolume(audioManager);
                break;
            case ("NEXT"):
                sendNextButton();
                break;
            case ("PREV"):
                sendPrevButton();
                break;
            default: text=null;
        }
        text=null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //check if UDP reciver thread is active, if not, start it
        if(!udpReciever.isAlive())udpReciever.start();

        //service is always active
        return Service.START_STICKY;
    }

    Thread udpReciever = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    InetAddress ip = InetAddress.getByName("192.168.5.13");
                    byte[] message = new byte[300];
                    packet = new DatagramPacket(message, message.length);
                    udpSocket.receive(packet);
                    text = new String(message, 0, packet.getLength());
                    AudioControl();

                } catch (Exception e) {
                    Log.e("UDP Exception", "error: ", e);
                    break;
                }
            }
        }
    };

    //simulate next button
    void sendNextButton(){
        Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(eventtime, eventtime,
                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT, 0);
        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        sendOrderedBroadcast(upIntent, null);

        KeyEvent upEvent = new KeyEvent(eventtime, eventtime,
                KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT, 0);
        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
        sendOrderedBroadcast(upIntent, null);
    }

    //simulate prev button
    void sendPrevButton(){
        Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(eventtime, eventtime,KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        sendOrderedBroadcast(upIntent, null);

        KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
        sendOrderedBroadcast(upIntent, null);
    }

    //up volume
    void riseVolume(AudioManager manage){
        manage.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_RAISE, 0);
    }

    //lower volume
    void lowerVolume(AudioManager manage){
        manage.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_LOWER,0);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
