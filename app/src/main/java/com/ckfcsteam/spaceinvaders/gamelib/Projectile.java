package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;

import com.ckfcsteam.replikapp.library.gamelib.GameObject;
import com.ckfcsteam.replikapp.library.gamelib.Sprite;


/**
 * Représente un projectile
 */
public class Projectile extends GameObject {
    /*Constructeurs*/
    public Projectile(Context context, int renderRessource, float x, float y){
        super(new Sprite(renderRessource, context), context);
        setCord(x, y);
    }


    /* Méthodes  specifique aux Invaders */

    /**
     * Modifie les coordonnées du projectile
     * @param x nouvelle position en x
     * @param y nouvelle position en y
     */
    public void setCord(float x, float y){
        setCordx(x);
        setCordy(y);
    }

    /**
     * Deplacement du projectile
     * @param n distance du déplacement
     */
    public void move(float n){
        setCordy(getCordy()+n);
    }

}
