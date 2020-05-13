package com.ckfcsteam.papangue.gamelib;

import android.content.Context;
import android.graphics.Canvas;

public class GameObject {

    public float cordx,cordy;
    public int width,height;
    private Sprite sprite;
    private Context context;

    public GameObject(int renderRessource, Context context){
        this.cordx = 0;
        this.cordy = 0;
        this.sprite = new Sprite(renderRessource, context);
        width = this.sprite.getWidth();
        height = this.sprite.getHeight();
        this.context = context;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setCordy(float cordy) {
        this.cordy = cordy;
    }

    public void setCordx(float cordx) {
        this.cordx = cordx;
    }



    public float getCordy() { return cordy;}

    public float getCordx() {
        return cordx;
    }

    public int getHeight() {return height;}
    public int getWidth(){return width;}

    public void display(Canvas canvas){canvas.drawBitmap(sprite.getBitmap(),   cordx, cordy, null);}

    public void resize(int width, int height){
        sprite.resize(width,height);
        this.width = width;
        this.height = height;
    }

    public Boolean entringEnCollisioningCarre(GameObject G2){
        return(this.getCordx() < G2.getCordx() + G2.getWidth() &&
                this.getCordx() + this.getWidth() > G2.getCordx() &&
                this.getCordy() < G2.getCordy() + G2.getHeight() &&
                this.getHeight() + this.getCordy() > G2.getCordy());
    }

}
