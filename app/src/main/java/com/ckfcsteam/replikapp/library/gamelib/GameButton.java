package com.ckfcsteam.replikapp.library.gamelib;

import android.content.Context;

public class GameButton extends GameObject {

    /*Constructeur*/
    public GameButton(Sprite sprite, Context context) {
        super(sprite, context);
    }

    /**
     * Indique si un GameButton est "clické"
     * @param touchx La coordonnée en x de la position du click
     * @param touchy La coordonnée en y de la position du click
     * @return True si la position du click est dans le bouton, False sinon
     */
    public Boolean checkIfClicked(float touchx, float touchy){
        /*La stratégie n'est pas la plus précise, mais pas la moins efficace. La notion de la forme du GameButton étant abstraite, on déduit les coordonnées des sommets:
         *   - Sommet haut gauche : (x,y)
         *   - Sommet bas gauche : (x,y+taille en longueur)
         *   - Sommet haut droit : (x + taille en largeur,y)
         *   - Sommet bas droit : (x + taille en largeur, y+taille en longueur)
         * En partant de ces données, le bouton est "clické" ssi :
         *   -   Sommets gauches du GameButton < Coordonnée x du click < Sommets droits du GameButton
         *   -   Sommets hauts du GameButton < Coordonnée y du click < Sommets bas du GameButton
         * Nous pouvons alors traduire cette condition comme ceci :
         * */
        return(touchx < this.getCordx() + this.getWidth() &&
                 touchx > this.getCordx() &&
                touchy < this.getCordy() + this.getHeight() &&
                touchy > this.getCordy());
    }
}
