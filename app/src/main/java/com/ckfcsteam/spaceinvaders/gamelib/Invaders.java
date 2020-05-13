package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;
import android.graphics.Canvas;


import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.replikapp.library.gamelib.GameObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Représente tous les invaders du jeu
 */
public class Invaders  {
    /* Attributs */
    // Tous les invaders en jeu, liste de ligne d'invaders
    private ArrayList<LineInvaders> invaders;
    // Les cordonnes en Y associé a chaque ligne
    private ArrayList<Float> cordsY;
    // Context de l'application
    private Context context;
    // Dimensions de l'écran
    private int screenWidth;
    private int screenHeight;
    // Temps lors du dernier appel de tirs
    private long lastShootTime;


    /* Constructeur*/
    public Invaders(int screenWidth, int screenHeight, Context context){
        // Récupération des infos sur l'application et l'écran
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.context = context;

        // Chaque invaders.get(i) sera lié à cordsY.get(i)
        invaders = new ArrayList<>();
        cordsY = new ArrayList<>();

        // On initialise à 0 car il n'y a pas encore de tir
        lastShootTime = 0;

        // Nombre de ligne à afficher au début de partie
        int nbStart = 10;

        // Création des invaders présent en début de partie
        for(int i=0; i<nbStart; i++){
            if(i==0){
                invaders.add(new LineInvaders(screenWidth, screenHeight, context,10, i));
                //cordsY.add( (nbStart-1)*(screenWidth/10)+ nbStart*(screenWidth/16));
                cordsY.add((float) ((nbStart - 1) * (screenWidth / 20) + nbStart * (screenWidth / 32)));
            }else{
                addInvadersLine();
            }
        }
        /* On charge toujours les autres lignes qui seront affché si le joueur perds*/
        // Nb de ligne maximale supporté par l'écran
        int nbRowMax = screenHeight / ((screenWidth/20) + (screenWidth/32));
        for (int i = 0; i < nbRowMax-nbStart; i++) {
            addInvadersLine();
        }

        // On applique à chaque ligne d'invaders sa coordonné en y
        applyCordY();

    }

    /* Méthodes */

    /**
     * Redimensionne tous les invaders créé
     * @param width nouvelle largeur
     * @param height nouvelle hauteur
     */
    public void resize(int width, int height){
        for(int i=0; i<invaders.size(); i++){
            invaders.get(i).resize(width, height);
        }
    }


    /**
     * Ajoute une ligne d'invaders dans la file
     */
    public void addInvadersLine(){
        invaders.add(new LineInvaders(screenWidth, screenHeight, context,10, 0));
        cordsY.add(cordsY.get(cordsY.size()-1) - (screenWidth/20+screenWidth/32));
    }

    /**
     * Affiche tout les invaders
     * @param canvas canvas qui va dessiner dans le jeu
     */
    public void displayAll(Canvas canvas){
        for (int i=0; i<invaders.size(); i++){
            // hauteur d'un invaders (screenWidth/20)
            if(invaders.get(i).getCordY()+((float)screenWidth/20) > 0)
                invaders.get(i).displayLine(canvas);
        }
    }

    /**
     * Change les coodonnées de chaque lignes d'invaders,
     * en fonction de la valeurs qui leurs correspondent dans CordY
     */
    public void applyCordY(){
        for(int i=0; i<invaders.size(); i++){
            invaders.get(i).setCordY(cordsY.get(i));
        }
    }

    /**
     * Detecte si un invaders est entré en collision avec un autre objet
     * @param G2 Un objet du jeu
     * @return Test si l'objet est entré en collisions avec un invaders
     */
    public Boolean entringEnCollisioningCarreAll(GameObject G2){
        for (int i = 0; i < invaders.size() ; i++) {
            if(invaders.get(i).entringEnCollisioningCarreLine(G2))
                return(true);
        }
        return(false);
    }

    /**
     * Supprime les lignes vides
     */
    public void majLines(){
        if(invaders.get(0).isEmpty()){
            invaders.remove(0);
            cordsY.remove(0);
            addInvadersLine();
        }
    }

    /**
     * Gére la descente des invaders
     * @param n distance à parcourir
     */
    public void raid(float n){
        for (int i = 0; i < cordsY.size() ; i++) {
            float value = cordsY.get(i) +n;
            cordsY.set(i, value);
            //cordsY.get(i) = new Integer(cordsY.get(i) + n);
        }
        applyCordY();
    }

    /**
     * Coordonnés de la premières ligne d'invaders en Y su l'écran
     * @return Coordonnés de la premières ligne d'invaders en Y
     */
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
    public void moveAllLR(float n, int screenWidth){
        for(int i=0; i< invaders.size(); i++){
            invaders.get(i).moveLineLR(n, screenWidth);
        }
    }

    /**
     * FirstLineSoot gére le tirs de la 1ere lignes des invaders
     *
     * Chaque invaders de la 1ere ligne à 10% chance de tirer
     *
     * @param width largeur des projectiles créé
     *
     * @param height hauteur des projectiles créé
     *
     * @return La liste des projectiles créé
     */
    //TODO Verifier que ça marche
    public ArrayList<Projectile> FirstLineShoot(int width, int height){
        Random r = new Random();
        // Liste des projectiles tirés durant cettes méthodes
        ArrayList<Projectile> lp = new ArrayList<Projectile>();
        // Nouveau tire possible après 5s
        if(System.currentTimeMillis()-lastShootTime > 5000){
            for(Invader i : invaders.get(0).getLine()  ){
                // Une chance sur 50% pour chaque invaders de tirer
                if(r.nextInt(100)<50 ){
                    float x = i.getCordx()+((float) i.getWidth()/2);
                    float y = i.getCordy()+i.getHeight();
                    Projectile p = new Projectile(context,R.drawable.project_inv, x, y);
                    p.resize(width/70, height/140);
                    lp.add(p);
                }
            }
            // Màj du temps pour le dernier tire
            lastShootTime = System.currentTimeMillis();
        }
        return(lp);
    }


}
