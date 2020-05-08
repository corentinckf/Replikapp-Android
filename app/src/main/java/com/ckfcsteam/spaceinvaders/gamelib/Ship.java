package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;

import com.ckfcsteam.papangue.gamelib.GameObject;
import com.ckfcsteam.replikapp.R;

public class Ship extends GameObject {
    /* Attributs specifique aux vaisseaux */
    // booléeen sur la désactivation de l'affichage
    private boolean disabled;

    public Ship(Context context){
        super(R.drawable.ship1, context);
        // Il est afficher par défaut
        disabled = false;
    }

    /* Méthodes  specifique aux vaisseaux */

    /**
     * Crée le projectiles lorsque le vaisseau tire
     * @param context Contexte de l'activité
     * @return Le projectile tiré par le vaisseau
     */
    public Projectile shoot(Context context, int width, int height){
        float x = getCordx()+(getWidth()/2);
        float y = getCordy();
        Projectile p = new Projectile(context,R.drawable.projectile, x, y);
        p.resize(width, height);
        return(p);
    }

    // Detecter si une position est sur le vaisseau
    public Boolean checkIfClicked(float touchx, float touchy){
        return(touchx < this.getCordx() + this.getWidth() &&
                touchx > this.getCordx() &&
                touchy < this.getCordy() + this.getHeight() &&
                touchy > this.getCordy());
    }

    // Renvoie la cordonne en X du milieu du vaisseau
    public float getMidCordX(){
        return (getCordx()+(getWidth()/2));
    }

    /* Getters et setters */

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
