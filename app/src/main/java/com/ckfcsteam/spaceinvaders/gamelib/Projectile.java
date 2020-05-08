package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;

import com.ckfcsteam.papangue.gamelib.GameObject;
import com.ckfcsteam.replikapp.R;

public class Projectile extends GameObject {
    /* Attributs specifique aux Invaders */
    // booléeen sur la désactivation de l'affichage
    private boolean disabled;
    // Direction du projectiles

    /*Constructeurs*/
    public Projectile(Context context, int renderRessource, float x, float y){
        super(renderRessource, context);
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
        if(!isDisabled())
            setCordy(getCordy()+n);
    }

    /*Getters && Setters*/

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
