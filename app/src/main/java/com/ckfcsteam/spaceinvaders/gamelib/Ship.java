package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;

import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.replikapp.library.gamelib.GameObject;
import com.ckfcsteam.replikapp.library.gamelib.Sprite;

/**
 * Représente les vaisseaux
 */
public class Ship extends GameObject {

    public Ship(Context context){
        super(new Sprite(R.drawable.ship1, context), context);
    }

    /* Méthodes  specifique aux vaisseaux */

    /**
     * Crée le projectiles lorsque le vaisseau tire
     * @param context Contexte de l'activité
     * @return Le projectile tiré par le vaisseau
     */
    public Projectile shoot(Context context, int width, int height){
        float x = getMidCordX()-((float)width/2);
        float y = getCordy();
        Projectile p = new Projectile(context,R.drawable.projectile, x, y);
        p.resize(width, height);
        return(p);
    }

    /**
     * Detecte si une position est sur le vaisseau
     * @param touchx position en axe x
     * @param touchy position en axe y
     * @return si la position est bien sur le vaisseau
     */
    public Boolean checkIfClicked(float touchx, float touchy){
        return(touchx < this.getCordx() + this.getWidth() &&
                touchx > this.getCordx() &&
                touchy < this.getCordy() + this.getHeight() &&
                touchy > this.getCordy());
    }


    /**
     * Renvoie la cordonnée en X du milieu du vaisseau
     *
     * @return la cordonnée en X du milieu du vaisseau
     */
    private float getMidCordX(){
        return (getCordx()+((float)getWidth()/2));
    }

}
