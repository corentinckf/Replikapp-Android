package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;

import com.ckfcsteam.papangue.gamelib.GameObject;
import com.ckfcsteam.replikapp.R;

/**
 * Represente un invader dans le jeu
 */
public class Invader extends GameObject {

    public Invader(Context context){
        super(R.drawable.invader, context);
    }

    /**
     * collisionDroite teste si l'invader est rentré en collision avec le bord droit de l'écran
     *
     * @param screenWidth Largeur de l'écran
     * @return vrai si le bord gauche de l'invader touche le bord droit de l'écran, sinon faux
     */
    public boolean collisionDroite(int screenWidth){
        return(getCordx() >= screenWidth-getWidth());
    }

    /**
     * collisionGauche teste si l'invader est rentré en collision avec le bord gauche de l'écran
     *
     * @return vrai si le bord droit de l'invader touche le bord gauche de l'écran, sinon faux
     */
    public boolean collisionGauche(){
        return(getCordx() <= 0);
    }

}
