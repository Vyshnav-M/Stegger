package com.example.navigation;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

public class G extends Application {
    public static Drawable imageDrawable;
    public static Bitmap bmap;
    public static Bitmap enbmap;
    public static String secMsg;
    public static Bitmap debmap;
    public static String msgDecoded = "";
    public static String encodePassword = "";
    public static String decodePassword ;
    public static char[] bits;

    public static void encode()
    {
        enbmap = bmap.copy(Bitmap.Config.ARGB_8888, true);
        //enbmap.setPremultiplied(false);

        secMsg = secMsg + "#";

        if(encodePassword.length() != 0)
            secMsg = "Y" + encodePassword + secMsg;
        else
            secMsg = "N" + secMsg;

        bits = getBinary(secMsg);

        int msglen = bits.length;
        int msgpos = 0;
        int width = bmap.getWidth();
        int height = bmap.getHeight();
        outer:
        for(int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                int pixel = bmap.getPixel(x, y);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);

                int newred = action(red,msgpos);
                msgpos = msgpos+1;
                if(msgpos >= msglen) {
                    int newPixel = Color.rgb(newred,green,blue);
                    enbmap.setPixel(x,y,newPixel);
                    break outer;
                }

                int newgreen = action(green,msgpos);
                msgpos = msgpos+1;
                if(msgpos >= msglen) {
                    int newPixel = Color.rgb(newred,newgreen,blue);
                    enbmap.setPixel(x,y,newPixel);
                    break outer;
                }

                int newblue = action(blue,msgpos);
                msgpos = msgpos+1;
                int newPixel = Color.rgb(newred,newgreen,newblue);
                enbmap.setPixel(x, y, newPixel);
                if(msgpos>=msglen)
                    break outer;
            }
        }
    }

    public static int action(int col, int i)
    {
        if( col%2==0 && bits[i]=='0' )
            return col;
        else if( col%2==1 && bits[i]=='0' )
            return col-1;
        else if( col%2==0 && bits[i]=='1' )
            return col+1;
        else if( col%2==1 && bits[i]=='1' )
            return col;
        return col;
    }

    public static void decode()
    {
        int width = debmap.getWidth();
        int height = debmap.getHeight();
        String bytestream = "";
        int index = 0;
        outer:
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = debmap.getPixel(x, y);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                bytestream = bytestream + String.valueOf(red%2);
                index++;
                if(index%8==0){
                    int a = Integer.parseInt(bytestream.substring(index-8,index),2);
                    char character = (char)(a);
                    if(character == '#')
                        break outer;
                    else
                        msgDecoded = msgDecoded + character;
                }
                bytestream = bytestream + String.valueOf(green%2);
                index++;
                if(index%8==0){
                    int a = Integer.parseInt(bytestream.substring(index-8,index),2);
                    char character = (char)(a);
                    if(character == '#')
                        break outer;
                    else
                        msgDecoded = msgDecoded + character;
                }
                bytestream = bytestream + String.valueOf(blue%2);
                index++;
                if(index%8==0){
                    int a = Integer.parseInt(bytestream.substring(index-8,index),2);
                    char character = (char)(a);
                    if(character == '#')
                        break outer;
                    else
                        msgDecoded = msgDecoded + character;
                }
            }
        }
        if(msgDecoded.charAt(0) == 'Y')
        {
            decodePassword = msgDecoded.substring(1,9);
            msgDecoded = msgDecoded.substring(9);
            DecodeResultActivity.passwordFound = true;
        }
        else if(msgDecoded.charAt(0) == 'N'){
            msgDecoded = msgDecoded.substring(1);
            DecodeResultActivity.passwordFound = false;
        }
    }

    public static char[] getBinary(String S)
    {
        StringBuilder sb = new StringBuilder();
        char[] chars = S.toCharArray();
        for (char c : chars) {
            String binary = Integer.toBinaryString(c);
            String formatted = String.format("%8s", binary);
            String output = formatted.replaceAll(" ", "0");
            sb.append(output);
        }
        return sb.toString().toCharArray();
    }
}