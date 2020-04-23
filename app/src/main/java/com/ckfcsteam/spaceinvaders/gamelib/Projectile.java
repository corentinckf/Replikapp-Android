package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;

import com.ckfcsteam.papangue.gamelib.GameObject;
import com.ckfcsteam.replikapp.R;

public class Projectile extends GameObject {
    /* Attributs specifique aux Invaders */
    // booléeen sur la désactivation de l'affichage
    private boolean disabled;

    /*Constructeurs*/
    public Projectile(Context context){
        super(R.drawable.projectile, context);
    }
    public Projectile(Context context, float x, float y){
        super(R.drawable.projectile, context);
        setCord(x, y);
        // Il est afficher par défaut
        disabled = false;
    }

    /* Méthodes  specifique aux Invaders */
    public void setCord(float x, float y){
        setCordx(x);
        setCordy(y);
    }

    // Deplacement du projectiles
    public void move(float n){
        setCordy(getCordy()-n);
    }

    /*Getters && Setters*/

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
