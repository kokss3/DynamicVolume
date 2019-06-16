package com.example.koks.dynamicvolume;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.TextView;

import com.example.koks.dynamicvolume.base.Archiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class IbusService extends Service {

    int port = 62021;
    IbusMessages messages = new IbusMessages();
    Archiver archiver;

    @Override
    public void onCreate() {
        archiver = new Archiver(getApplicationContext());

        new Thread(new Runnable() {
            @Override
            public void run() {
                String text;

                while (true) {
                    try {
                        InetAddress ip = InetAddress.getByName("192.168.4.1");
                        DatagramSocket udpSocket = new DatagramSocket(port);
                        udpSocket.connect(ip, port);

                        byte[] message = new byte[512];
                        DatagramPacket packet = new DatagramPacket(message, message.length);
                        udpSocket.receive(packet);
                        udpSocket.close();

                        message = packet.getData();
                        text = new String(message);

                        char testSym = ',';
                        StringBuilder myNumbers = new StringBuilder();
                        for (int i = 0; i < text.length(); i++) {
                            if (Character.isDigit(text.charAt(i))||testSym==text.charAt(i)) {
                                myNumbers.append(text.charAt(i));
                            } }
                        archiver.saveMessage(myNumbers.toString());
                        messages.receiveMessage(myNumbers.toString());

                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //service is always active
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}