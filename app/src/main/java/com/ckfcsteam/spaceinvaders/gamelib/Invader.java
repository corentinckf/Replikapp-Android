package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;

import com.ckfcsteam.papangue.gamelib.GameObject;
import com.example.spaceinvadersv1.R;

public class Invader extends GameObject {
    /* Attributs specifique aux Invaders */
    //private int id;
    private int xtab, ytab;

    public Invader(Context context, int x, int y){
        super(R.drawable.invadersm1, context);
        //this.id = id;
        this.xtab = x;
        this.ytab = y;
    }

    /* Méthodes  specifique aux Invaders */
    public void setCord(float x, float y){
        setCordx(x);
        setCordy(y);
    }

    /**
     * collisionDroite teste si l'invader est rentré en collision avec le bord droit de l'écran
     *
     * @param screenWidth Largeur de l'écran
     * @return vrai si le bord gauche de l'invader touche le bord droit de l'écran, sinon faux
     */
    public boolean collisionDroite(int screenWidth){
        return(getCordx() >= screenWidth+getWidth());
    }

    /**
     * collisionGauche teste si l'invader est rentré en collision avec le bord gauche de l'écran
     *
     * @return vrai si le bord droit de l'invader touche le bord gauche de l'écran, sinon faux
     */
    public boolean collisionGauche(){
        return(getCordx() <= 0);
    }

    /*Getters && Setters*/

    public int getXtab() {
        return xtab;
    }

    public void setXtab(int x) {
        this.xtab = x;
    }

    public int getYtab() {
        return ytab;
    }

    public void setYtab(int y) {
        this.ytab = y;
    }
}
