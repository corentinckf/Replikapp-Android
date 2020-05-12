package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;

import com.ckfcsteam.papangue.gamelib.GameObject;
import com.ckfcsteam.replikapp.R;

/**
 * Représente un projectile
 */
public class Projectile extends GameObject {
    /* Attributs */

    // booléeen sur la désactivation
    private boolean disabled;

    /*Constructeurs*/
    public Projectile(Context context, int renderRessource, float x, float y){
        super(renderRessource, context);
        setCord(x, y);
        // Il est affiché par défaut
        disabled = false;

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
