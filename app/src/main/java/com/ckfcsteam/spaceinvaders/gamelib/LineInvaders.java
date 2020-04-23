package com.ckfcsteam.spaceinvaders.gamelib;

import android.content.Context;
import android.graphics.Canvas;

import com.ckfcsteam.papangue.gamelib.GameObject;

import java.util.ArrayList;

public class LineInvaders {
    /* Attributs */

    // Tous les invaders d'une ligne
    private ArrayList<Invader> line;

    // numero de la ligne
    private int numLine;

    // nombre d'invaders par ligne à la creation de la ligne
    private int nbByLine;

    // Context de l'application
    private Context context;

    // Dimensions de l'écran
    private int screenWidth;
    private int screenHeight;

    // Dimensions des invaders
    //private int width;
    //private int height;

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
        this.numLine = numLine;

        cordY = 0;

        line = new ArrayList<Invader>();
        // Les invaders se déplace vers la droite
        toRight = true;
        createLine();
        //width = line.get(0).getWidth();
        //height = line.get(0).getHeight();
    }

    /* Méthodes */

    // Redimensionner tout les invaders
    public void resize(int width, int height){
        //this.width = width;
        //this.height = height;
        for(int i=0; i<line.size(); i++){
            line.get(i).resize(width, height);
        }
    }

    // Ajouter une ligne d'invader
    public void createLine(){
        for(int i=0; i<nbByLine; i++){
            Invader invader = new Invader(context, numLine, i);
            // Ajustement de l'image
            //invader.resize(screenWidth/8, screenWidth/10);
            // Placement en x des invader
            //invader.setCordx((screenWidth/16)*(i+1)+(screenWidth/8)*i);
            invader.setCordx((float)((screenWidth/((nbByLine+2)*3))*(i+1)+(screenWidth/((nbByLine+2)*1.5))*i));
            line.add(invader);

        }
    }





    // Nombre d'invaders sur la ligne
    public int getNbInvaders(){
        return(line.size());
    }

    // Afficher la ligne d'invaders
    public void displayLine(Canvas canvas){
        for(int i=0; i<getNbInvaders(); i++){
            line.get(i).display(canvas);
        }
    }

    // Collision en mode carre
    public Boolean entringEnCollisioningCarreLine(GameObject G2){
        for (int i = 0; i < getNbInvaders() ; i++) {
            if(line.get(i).entringEnCollisioningCarre(G2)) {
                line.remove(i);
                return (true);
            }
        }
        return(false);
    }

    // Verifie si la ligne est vide
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
     * moveLR déplace la ligne horizontalement
     *
     * Le deplacement se fera à droite ou à gauche
     * en fonction de son sens de deplacement
     *
     * @param n nombre à ajouter ou soustraire aux coordonnées en X
     *          de chaque invaders de la ligne
     */

    public void moveLR(int n){

        for (int i = 0; i < getNbInvaders() ; i++) {
            line.get(i).setCordx(line.get(i).getCordx() + (toRight ? n : -n));
        }
    }

    /*
    // Renvoie les coordonné en X du premier et
    // du dernier élement de la ligne
    public float minCoordXLine(){
        return(line.get(0).getCordx());
    }

    // Renvoie les coordonné en X du premier et
    // du dernier élement de la ligne
    public float maxCoordXLine(){
        return(line.get(getNbInvaders()).getCordx());
    }*/

    /* Getters && Setters */

    // Aligner en y les invaders
    public void setCordY(int y){
        cordY = y;
        for(int i=0; i<getNbInvaders(); i++){
            line.get(i).setCordy(cordY);
        }
    }

    public float getCordY() {
        return cordY;
    }

    /*public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }*/

    public boolean isToRight() {
        return toRight;
    }

    public void setToRight(boolean toRight) {
        this.toRight = toRight;
    }
}
