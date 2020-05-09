package com.ckfcsteam.replikapp.library.gamelib;
import android.content.Context;
import android.graphics.Canvas;

public class GameObject {

    /*Déclaration des attributs*/
    public float cordx,cordy; /*attributs coordonnées*/
    public int width,height; /*attributs taille*/
    private Sprite sprite; /*attribut Sprite*/
    private Context context; /*attribut context*/

    /*Constructeur*/
    public GameObject(Sprite sprite, Context context){
        this.cordx = 0; /*Coordonée x de base en 0*/
        this.cordy = 0; /*Coordonée y de base en 0*/
        this.sprite = sprite;
        width = this.sprite.getWidth(); /* La largeur du GameObject est celle du Sprite associé */
        height = this.sprite.getHeight(); /* La longueur du GameObject est celle du Sprite associé */
        this.context = context;
    }
    /*Zone Setters/Getters*/
    public void setSprite(Sprite sprite) {this.sprite = sprite;} /*Modifier le sprite associé au GameObject*/
    public Sprite getSprite() {return sprite;} /*Renvoie le sprite associé au GameObject*/
    public void setCordy(float cordy) {this.cordy = cordy;} /*Modifier la coordonnée y au GameObject*/
    public void setCordx(float cordx) {this.cordx = cordx;} /*Modifier la coordonnée x au GameObject*/
    public float getCordy() { return cordy;} /*Renvoie la coordonnée y au GameObject*/
    public float getCordx() {return cordx;} /*Renvoie la coordonnée x au GameObject*/
    public int getHeight() {return height;} /*Renvoie la taille en longueur du GameObject*/
    public int getWidth(){return width;} /*Renvoie la taille en largeur du GameObject*/
    /*Fin de zone Setters/Getters*/

    /**
     * Affiche le sprite associé au GameObject sur le canvas en paramètre
     * @param canvas Canvas sur lequel on souhaite l'affichage
     */
    public void display(Canvas canvas){canvas.drawBitmap(sprite.getBitmap(), cordx, cordy, null);}

    /**
     * Redimensionne la taille du GameObject et du Sprite associé
     * @param width Largeur voulue
     * @param height Longueur voulue
     */
    public void resize(int width, int height){
        sprite.resize(width,height); /*Méthode resize expliquée dans la classe Sprite*/
        this.width = width;
        this.height = height;
    }

    /**
     * Indique si le GameObject entre en collision avec un GameObject voisin donné en paramètre
     * @param G2 L'objet GameObject auquel on souhait contrôler si il y a collision ou non
     * @return True si il y a collision, False sinon
     */
    public Boolean entringEnCollisioningCarre(GameObject G2){
        /*La stratégie n'est pas la plus précise, mais pas la moins efficace. La notion de la forme du GameObject étant abstraite, on déduit les coordonnées des sommets: :
        *   - Sommet haut gauche : (x,y)
        *   - Sommet bas gauche : (x,y+taille en longueur)
        *   - Sommet haut droit : (x + taille en largeur,y)
        *   - Sommet bas droit : (x + taille en largeur, y+taille en longueur)
        * En partant de ces données, il y a collision ssi :
        *   -   Sommets gauches du GameObject en paramètre < Un des sommets du GameObject < Sommets droits du GameObject en paramètre
        *   -   Sommets hauts du GameObject en paramètre < Un des sommets du GameObject < Sommets bas du GameObject en paramètre
        * Nous pouvons alors traduire cette condition comme ceci :
        * */
        return(this.getCordx() < G2.getCordx() + G2.getWidth() &&
                this.getCordx() + this.getWidth() > G2.getCordx() &&
                this.getCordy() < G2.getCordy() + G2.getHeight() &&
                this.getHeight() + this.getCordy() > G2.getCordy());
    }
}
