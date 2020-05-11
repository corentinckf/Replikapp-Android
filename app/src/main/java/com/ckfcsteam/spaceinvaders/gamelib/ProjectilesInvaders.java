package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;
import android.graphics.Canvas;

import com.ckfcsteam.replikapp.R;

import java.util.ArrayList;

public class ProjectilesInvaders extends ArrayList<Projectile> {

    // Dimensions de l'écran
    private int screenWidth;
    private int screenHeight;

    public ProjectilesInvaders(int width, int height){
        super();
        screenWidth = width;
        screenHeight = height;
    }

    /*public void addProjectile(Context context, float x, float y){
        Projectile p = new Projectile(context, R.drawable.projectile, x, y);

    }*/

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
     * move effectue un deplacement de tous les projectiles ennemi
     *
     * @param n distance à parcourir
     */
    public void move(int n){
        for(Projectile p: this){
            p.move(n);
        }
    }

    /**
     * lose verifie si le joueur à perdue à cause d'un tir ennemi
     * @param s le vaisseau du joueur
     * @return vrai si le vaisseau est entré en collision avec un projectile ennemi
     */
    public boolean hitShip(Ship s){
        for(Projectile p: this){
            if(s.entringEnCollisioningCarre(p))
                return (true);
        }
        return (false);
    }

    /**
     * annulation supprime deux projectiles de camp opposé qui entre en collisions
     * @param lShip projectiles du joueur
     */
    public void annulation(ProjectilesShip lShip){
        for(int i = 0; i<this.size(); i++){
            for(int j = 0; j<lShip.size(); j++){
                if(this.get(i).entringEnCollisioningCarre(lShip.get(j))){
                    this.remove(i);
                    lShip.remove(j);
                    break;
                }
            }
        }
    }

    /**
     * maj mets à jour tout les projectile
     *
     * Il supprime les projectiles qui sorte de l'écran
     */
    private void maj(){
        for(int i = 0; i<this.size(); i++){
            if(this.get(i).cordy > screenHeight){
                this.remove(i);
            }
        }
    }

    /**
     * displayAll affiche tout les projectiles ennemi
     *
     * @param canvas canvas qui va dessiner les projectiles
     */
    public void displayAll(Canvas canvas){
        for(Projectile p: this){
            p.display(canvas);
        }
        maj();
    }

    /**
     * Desactive/Active tous les projectiles
     *
     * @param b si b est vrai desactive, sinon les actives
     */
    public void setDisabled(boolean b){
        for(Projectile p: this){
            p.setDisabled(b);
        }
    }
}
