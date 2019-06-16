package com.example.koks.dynamicvolume;

import java.util.ArrayList;

public class IbusMessages {

    private final static Byte[] MUTE = {0x50, 0x04, 0x68, 0x3B, (byte) 0x80, 0x27};
    private final static Byte[] RT = {0x50, 0x03, (byte) 0xC8, 0x01, (byte) 0x9A};

    private final static Byte[] NEXT_CLICK ={0x50, 0x04, 0x68, 0x3B, 0x01, 0x06};
    private final static Byte[] NEXT_PRESS = {0x50, 0x04, 0x68, 0x3B, 0x11, 0x16};
    private final static Byte[] NEXT_RELASE = {0x50, 0x04, 0x68, 0x3B, 0x21, 0x26};

    private final static Byte[] PREVIUS_CLICK = {0x50, 0x04, 0x68, 0x3B, 0x08, 0x0F};
    private final static Byte[] PREVIUS_PRESS = {0x50, 0x04, 0x68, 0x3B, 0x18, 0x1F};
    private final static Byte[] PREVIOUS_RELEASE = {0x50, 0x04, 0x68, 0x3B, 0x28, 0x2F};

    ArrayList<Byte> receiveMessage(String rawText){
        ArrayList<Byte> tempList = new ArrayList<>();
        String[] temp = {};

        if(rawText.contains(" ")){
            temp = rawText.split(" ");
        }else if(rawText.contains(",")){
            temp = rawText.split(",");
        }else if(rawText.contains(".")){
            temp = rawText.split(".");
        }

        for(String momentary: temp){
            tempList.add(Byte.decode(momentary));
        }

        System.out.println(tempList);
        return tempList;
    }

    public ArrayList composeMessage(byte src, byte dest, byte[] message){
        ArrayList<Byte> temp = new ArrayList<>();

        temp.add(src);
        temp.add(dest);

        for(byte msgOne:message) temp.add(msgOne);

        temp.add(1,(byte)(temp.size()));

        temp.add(generateCheckSum(temp));
        System.out.println(temp);

        return temp;
    }

    private byte generateCheckSum(ArrayList<Byte> fullMsg){
        byte checksumByte = 0;
        for (byte msgPart:fullMsg) checksumByte ^= msgPart;

        return checksumByte;
    }
}
