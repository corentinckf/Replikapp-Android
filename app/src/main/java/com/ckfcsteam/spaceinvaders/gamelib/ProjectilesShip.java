package com.ckfcsteam.spaceinvaders.gamelib;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Représente tout les projectiles du vaisseau en jeu
 */
public class ProjectilesShip extends ArrayList<Projectile> {

    // Dimensions de l'écran
    private int screenWidth;
    private int screenHeight;

    public ProjectilesShip(int width, int height){
        super();
        screenWidth = width;
        screenHeight = height;
    }

    /**
     * resize redimensionne tout les projectiles ennemi
     * @param nWidth largeur
     * @param nHeight hauteur
     */
    public void resize(int nWidth, int nHeight){
        for(Projectile p: this){
            p.resize(nWidth, nHeight);
        }
    }

    /**
     * move effectue un deplacement de tous les projectiles alliés
     *
     * @param n distance à parcourir
     */
    public void move(int n){
        for(Projectile p: this){
            p.move(-n);
        }
    }

    /**
     * Compte le nombre d'ennemi touché par les projectiles en jeu
     * @param invaders liste des ennemie
     * @return le nombre d'ennemis touchés
     */
    public int hit(Invaders invaders){
        int c = 0;
        for(int i = 0; i< this.size(); i++){
            if(invaders.entringEnCollisioningCarreAll(this.get(i))){
                this.remove(i);
                c++;
            }
        }
        return(c);
    }

    /**
     * maj mets à jour tout les projectile
     *
     * Il supprime les projectiles qui sorte de l'écran
     */
    private void maj(){
        for(int i = 0; i<this.size(); i++){
            if(this.get(i).cordy < 0){
                this.remove(i);
            }
        }
    }

    /**
     * displayAll affiche tout les projectiles alliés
     *
     * @param canvas canvas qui va dessiner les projectiles
     */
    public void displayAll(Canvas canvas){
        for(Projectile p: this){
            p.display(canvas);
        }
        maj();
    }

}
