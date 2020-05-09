package com.ckfcsteam.replikapp.library.gamelib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Sprite {

    /*Déclaration des attributs*/
    private Bitmap bitmap; /*attribut bitmap*/

    /*Constructeur*/
    public Sprite(int ressource, Context context){
        /* Options d'optimisation de la Bitmap */
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inPreferredConfig = Bitmap.Config.RGB_565;
        op.inJustDecodeBounds = false;
        /*Ici, on construit notre bitmap à partir de la donnée entrée en paramètre*/
        bitmap = BitmapFactory.decodeResource(context.getResources(), ressource, op);
    }

    /*Zone Setters/Getters*/
    public void setBitmap(Bitmap bitmap) {this.bitmap = bitmap;} /*Modifier la bitmap associée au Sprite*/
    public Bitmap getBitmap() {return bitmap;} /*Renvoie la bitmap associée au Sprite*/
    public int getWidth(){return bitmap.getWidth();} /*Renvoie la largeur du Sprite*/
    public int getHeight(){return bitmap.getHeight();} /*Renvoie la longueur du Sprite*/
    /*Fin de la zone Setters/Getters*/

    /**
     * Redimensionne le Sprite
     * @param width largeur voulue
     * @param height longueur voulue
     */
    public void resize(int width, int height){
        /*La stratégie ici, est de créer une Bitmap "mis à l'échelle" avec createScaledBitmap et de la mettre à la place de la Bitmap initiale*/
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

}
