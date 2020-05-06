package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;
import android.graphics.Canvas;

import com.ckfcsteam.papangue.gamelib.GameObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Invaders  {
    /* Attributs */
    // Tous les invaders en jeu
    private ArrayList<LineInvaders> invaders;
    // Les cordonnes en Y associé a chaque ligne
    private ArrayList<Integer> cordsY;
    // Cordonnes de la première ligne
    //private float firstLineCordY;
    // Context de l'application
    private Context context;

    // Dimensions de l'écran
    private int screenWidth;
    private int screenHeight;

    // Dimensions des invaders
    //private int width;
    //private int height;

    // Si vrai les invaders se deplace vers la droite
    // sinon, vers la gauche
    private boolean toRight;

    // nombre de ligne au debut
    private int nbStart;

    // booléeen sur la désactivation de l'affichage
    private boolean disabled;

    // Time lors du dernier appel de tirs
    private long lastShootTime;


    /* Constructeur*/
    public Invaders(int screenWidth, int screenHeight, Context context){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.context = context;

        invaders = new ArrayList<>();
        cordsY = new ArrayList<>();

        // On initialise à 0 car il n'y a pas encore de tir
        lastShootTime = 0;

        // Les invaders se déplace vers la droite
        toRight = true;

        // Ils sont afficher par défaut
        disabled = false;

        nbStart = 10;

        for(int i=0; i<nbStart; i++){
            if(i==0){
                invaders.add(new LineInvaders(screenWidth, screenHeight, context,10, i));
                //cordsY.add( (nbStart-1)*(screenWidth/10)+ nbStart*(screenWidth/16));
                cordsY.add( (nbStart-1)*(screenWidth/20)+ nbStart*(screenWidth/32));
            }else{
                addInvadersLine();
            }
        }
        /**/
        // Nb de ligne maximale supporté par l'écran
        int nbRowMax = screenHeight / ((screenWidth/20) + (screenWidth/32));
        for (int i = 0; i < nbRowMax-nbStart; i++) {
            addInvadersLine();
        }
        /**/
        //invaders.add(new LineInvaders(screenWidth, screenHeight, context, 0));
        //cordsY.add( (nbStart-1)*(screenWidth/10)+ nbStart*(screenWidth/16));
        applyCordY();
        //width = invaders.get(0).getWidth();
        //height = invaders.get(0).getHeight();

    }

    /* Méthodes */

    // Redimensionner tout les invaders
    public void resize(int width, int height){
        //this.width = width;
        //this.height = height;
        for(int i=0; i<invaders.size(); i++){
            invaders.get(i).resize(width, height);
        }
    }


    //Ajouter une ligne d'invader
    public void addInvadersLine(){
        invaders.add(new LineInvaders(screenWidth, screenHeight, context,10, 0));
        //cordsY.add(cordsY.get(cordsY.size()-1) - (screenWidth/10+screenWidth/16));
        cordsY.add(cordsY.get(cordsY.size()-1) - (screenWidth/20+screenWidth/32));
        //resize(width, height);
    }

    // Afficher tout les invaders
    public void displayAll(Canvas canvas){
        for (int i=0; i<invaders.size(); i++){
            if(invaders.get(i).getCordY() > 0)
                invaders.get(i).displayLine(canvas);
        }
    }

    // Application des cordY
    public void applyCordY(){
        for(int i=0; i<invaders.size(); i++){
            invaders.get(i).setCordY(cordsY.get(i));
        }
    }

    // Collision en mode carre
    public Boolean entringEnCollisioningCarreAll(GameObject G2){
        for (int i = 0; i < invaders.size() ; i++) {
            if(invaders.get(i).entringEnCollisioningCarreLine(G2))
                return(true);
        }
        return(false);
    }

    // Maj des lignes
    public void majLines(){
        if(invaders.get(0).isEmpty()){
            invaders.remove(0);
            cordsY.remove(0);
            addInvadersLine();
        }
    }

    // Gere la descente des invaders
    public void raid(int n){
        for (int i = 0; i < cordsY.size() ; i++) {
            int value = cordsY.get(i) +n;
            cordsY.set(i, value);
            //cordsY.get(i) = new Integer(cordsY.get(i) + n);
        }
        applyCordY();
    }

    // Renvoie la coordonnée en Y de la première ligne
    public float firstLineCordY(){
        if(cordsY.size()>0)
            return(cordsY.get(0));
        else
            // Tous les invaders de l'écran supprimé
            // les nouveaux vont normalement arrivé
            return(0);
    }

    /**
     * moveAllLR déplace toutes les lignes horizontalement
     *
     * Cette méthode va appeler la méthode moveAllLR
     * sur toutes les lignes d'invaders
     *
     * @param n nombre à ajouter ou soustraire aux coordonnées en X
     *          de chaque invaders
     *
     * @param screenWidth La taille de l'écran
     *
     */
    public void moveAllLR(int n, int screenWidth){
        for(int i=0; i< invaders.size(); i++){
            invaders.get(i).moveLineLR(n, screenWidth);
        }
    }

    /**
     * FirstLineSoot gére le tirs de la 1ere lignes des invaders
     *
     * Chaque invaders de la 1ere ligne à 10% chance de tirer
     *
     * @return La liste des projectiles créé
     */
    //TODO Verifier que ça marche
    public ArrayList<ProjectileInvaders> FirstLineShoot(){
        Random r = new Random();
        ArrayList<ProjectileInvaders> p = new ArrayList<ProjectileInvaders>();
        // Nouveau tire possible après 5s
        if(System.currentTimeMillis()-lastShootTime > 5000){
            for(Invader i : invaders.get(0).getLine()  ){
                // Une chance sur 10% pour chaque invaders de tirer
                if(r.nextInt(100)<50 ){
                    float x = i.getCordx()+(i.getWidth()/2);
                    float y = i.getCordy()+i.getHeight();
                    p.add(new ProjectileInvaders(context,x, y));
                }
            }
            // Màj du time pour le dernier tire
            lastShootTime = System.currentTimeMillis();
        }


        return(p);
    }

    /* Getters && Setters */
    public boolean isToRight() {
        return toRight;
    }

    public void setToRight(boolean toRight) {
        this.toRight = toRight;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
