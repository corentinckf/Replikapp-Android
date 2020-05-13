package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;


import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.replikapp.library.gamelib.GameObject;
import com.ckfcsteam.replikapp.library.gamelib.Sprite;

/**
 * Represente un invader dans le jeu
 */
public class Invader extends GameObject {

    public Invader(Context context){
        super(new Sprite(R.drawable.invader, context), context);
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
