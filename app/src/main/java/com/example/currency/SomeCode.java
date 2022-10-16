package com.example.currency;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class SomeCode {
    public static Bitmap getCurrencyBitmapFromCode(Context _context, String code){
        AssetManager am = _context.getResources().getAssets();
        try{
            InputStream is = am.open("flags/"+code.toLowerCase()+".png");
            Bitmap bitmap1 = BitmapFactory.decodeStream(is);
            return bitmap1;
        }catch (IOException e){
            InputStream is = null;
            try{
                is = am.open("flags/error.png");
            }catch (IOException ex){
                ex.printStackTrace();
            }
            return BitmapFactory.decodeStream(is);
        }
    }
}
