package com.ckfcsteam.papangue.gamelib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Sprite {

    private Bitmap bitmap;

    public Sprite(int ressource, Context context){
        bitmap = BitmapFactory.decodeResource(context.getResources(), ressource);
    }

    public void setBitmap(Bitmap bitmap) {this.bitmap = bitmap;}
    public Bitmap getBitmap() {return bitmap;}

    public int getWidth(){return bitmap.getWidth();}
    public int getHeight(){return bitmap.getHeight();}

    public void resize(int width, int height){
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }
}
