package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;

import com.ckfcsteam.papangue.gamelib.GameObject;
import com.ckfcsteam.replikapp.R;

public class ProjectileInvaders extends Projectile {
    /* Attributs specifique aux Invaders */
    // booléeen sur la désactivation de l'affichage
    //private boolean disabled;
    // Direction du projectiles

    /*Constructeurs*/

    public ProjectileInvaders(Context context, float x, float y){
        super(context, R.drawable.projectile, x, y);

    }

    /* Méthodes  specifique aux Invaders */
    // Deplacement du projectiles
    public void move(float n){
        setCordy(getCordy()+n);
    }
    /*
    public void setCord(float x, float y){
        setCordx(x);
        setCordy(y);
    }

    // Deplacement du projectiles
    public void move(float n){
        setCordy(getCordy()+n);
    }

    /*Getters && Setters*//*

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }*/
}

