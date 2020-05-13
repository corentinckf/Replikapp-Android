package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;
import android.graphics.Canvas;


import com.ckfcsteam.replikapp.library.gamelib.GameObject;

import java.util.ArrayList;

/**
 * Represente une ligne d'invaders
 */
public class LineInvaders {
    /* Attributs */

    // Tous les invaders d'une ligne
    private ArrayList<Invader> line;
    // nombre d'invaders par ligne à la creation de la ligne
    private int nbByLine;
    // Context de l'application
    private Context context;
    // Dimensions de l'écran
    private int screenWidth;
    private int screenHeight;
    // Cordonnes en y de la ligne
    private float cordY;

    // Si vrai les invaders se deplace vers la droite
    // sinon, vers la gauche
    private boolean toRight;


    /* Constructeur*/
    public LineInvaders(int screenWidth, int screenHeight, Context context, int nbByLine, int numLine){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.context = context;
        this.nbByLine = nbByLine;
        // Valeurs par défaut de la coordonnée en Y
        cordY = 0;

        line = new ArrayList<Invader>();

        // Les invaders se déplace vers la droite
        toRight = true;
        // Creation d'une nouvelle ligne
        createLine();
    }

    /* Méthodes */

    /**
     * Redimensionne tout les invaders de la ligne
     * @param width nouvelle largeur
     * @param height nouvelle hauteur
     */
    public void resize(int width, int height){
        for(int i=0; i<line.size(); i++){
            line.get(i).resize(width, height);
        }
    }

    /**
     * Création des invaders de la ligne
     */
    public void createLine(){
        for(int i=0; i<nbByLine; i++){
            Invader invader = new Invader(context);
            // Ajustement de l'image
            //invader.resize(screenWidth/8, screenWidth/10);
            // Placement en x des invader
            //invader.setCordx((screenWidth/16)*(i+1)+(screenWidth/8)*i);
            //invader.setCordx((float)((screenWidth/((nbByLine+2)*3))*(i+1)+(screenWidth/((nbByLine+2)*1.5))*i));
            invader.resize(screenWidth/16, screenWidth/20);
            invader.setCordx((float)((screenWidth/((nbByLine+2)*3))*(i+1)+(screenWidth/((nbByLine+2)*1.5))*(i+1)));
            line.add(invader);

        }
    }

    /**
     * Obtention du nb d'invaders sur la ligne
     * @return nb d'invaders sur la ligne
     */
    public int getNbInvaders(){
        return(line.size());
    }

    /**
     * Affiche la ligne d'invaders
     * @param canvas canvas qui dessine sur le jeu
     */
    public void displayLine(Canvas canvas){
        for(int i=0; i<getNbInvaders(); i++){
            line.get(i).display(canvas);
        }
    }

    /**
     * Detection des collision
     * @param G2 Objet avec qui on teste la collisions
     * @return Test si un invaders est entré en collision avec G2
     */
    public Boolean entringEnCollisioningCarreLine(GameObject G2){
        for (int i = 0; i < getNbInvaders() ; i++) {
            if(line.get(i).entringEnCollisioningCarre(G2)) {
                line.remove(i);
                return (true);
            }
        }
        return(false);
    }

    /**
     * Verifie si une ligne est vide
     * @return test si la ligne est vide
     */
    public boolean isEmpty(){
        return(line.isEmpty());
    }

    /**
     * gereDirectionHorizontal gère les changements de direction sur l'axe X
     *
     * Si la ligne d'invaders arrive à droite de l'écran, la direction sera changé vers la gauche
     * et inversement
     *
     * @param screenWidth La taille de l'écran
     */
    public void gereDirectionHorizontal(int screenWidth){
        if (toRight){
            if(getNbInvaders() != 0 && line.get(getNbInvaders()-1).collisionDroite(screenWidth)){
                toRight = false;
            }
        }else{
            if(getNbInvaders() != 0 && line.get(0).collisionGauche()){
                toRight = true;
            }
        }
    }

    /**
     * moveLineLR déplace la ligne horizontalement
     *
     * Le deplacement se fera à droite ou à gauche
     * en fonction de son sens de deplacement
     *
     * @param n nombre à ajouter ou soustraire aux coordonnées en X
     *          de chaque invaders de la ligne
     *
     * @param screenWidth La taille de l'écran
     *
     */
    public void moveLineLR(float n, int screenWidth){
        gereDirectionHorizontal(screenWidth);

        for (int i = 0; i < getNbInvaders() ; i++) {
            line.get(i).setCordx(line.get(i).getCordx() + (toRight ? n : -n));
        }
    }


    /* Getters && Setters */

    /**
     * Recupere la liste des invaders
     * @return la liste des invaders
     */
    public ArrayList<Invader> getLine() {
        return line;
    }

    /**
     * Change les coordonnées en y de la ligne
     * @param y nouvelle coordonnée en y
     */
    public void setCordY(float y){
        cordY = y;
        for(int i=0; i<getNbInvaders(); i++){
            line.get(i).setCordy(cordY);
        }
    }

    /**
     * Obtenir la coordonnées en y
     * @return la coordonnées en y
     */
    public float getCordY() {
        return cordY;
    }

}
