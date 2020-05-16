package com.ckfcsteam.replikapp.surfaceviews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.replikapp.activities.SuperBouleActivity;
import com.ckfcsteam.replikapp.activities.SuperBouleMenuActivity;
import com.ckfcsteam.replikapp.library.Game;
import com.ckfcsteam.replikapp.library.gamelib.GameButton;
import com.ckfcsteam.replikapp.library.gamelib.GameObject;
import com.ckfcsteam.replikapp.library.gamelib.Sprite;

import java.util.concurrent.CopyOnWriteArrayList;

public class SuperBouleSurfaceView extends Game {
    /*Variable lvl : Stoque de la description du niveau de jeu*/
    private int[][] lvl;
    /*Variable block_list : Stoque l'ensemble des briques non détruites du niveau de jeu*/
    private CopyOnWriteArrayList<GameObject> block_list = new CopyOnWriteArrayList<GameObject>();
    /*Note : On préfere l'utilisation d'une CopyOnWriteArrayList à ArrayList pour être plus safe au niveau des accés.
    * Elle ne nécessite pas d'ajouter une synchronisation sur les modifications de la liste.*/

    /*Variables contenant la taille de l'écran*/
    private int screen_width,screen_height;

    /*Variables contenant les objets principaux du jeu : Le Pad et la Balle */
    private GameObject paddle, ball;
    /*Variables contenant les Boutons : Recommencer et Quitter le niveau*/
    private GameButton restart,quit;

    /*Variables contenant la "Vitesse" à laquelle balle se déplace*/
    private float velocityX, velocityY;

    /*Variables contenant les coordonnées de la position du "doigt" posé sur l'écran*/
    private float touch_cordx, touch_cordy;

    /*Variables indiquant si le niveau de jeu a commencé et a terminé*/
    private boolean started,finished = false;

    /*Variables contenant le texte indicatif du Score, le "Touch to play text" ainsi que l'objet TypeFace*/
    private TextPaint score_textPaint, begin_textPaint;

    /*Variable contenant le score actuel sur le niveau de jeu, initialement à 0*/
    private int score = 0;

    /*Variables relatives au niveau de jeu, respectivement : L'id du plus haut niveau atteint,
    l'id réel du niveau actuel, le nombre de briques détruites
    et l'id transmit par l'activité sous la forme id_lvl - 1 en String*/
    private int maxLvl_unlocked, real_lvl, block_destroyed;
    private String lvl_id;

    /*Variables contenant les différents objets Sprite du niveau de jeu*/
    private Sprite ball_sprite,paddle_sprite,green_block_sprite,blue_block_sprite, grey_block_sprite,
            red_block_sprite,yellow_sprite,quit_sprite,restart_sprite;

    /*Constructeur*/
    public SuperBouleSurfaceView(Context context, String lvl_id, int maxLvl_unlocked){
        super(context);

        /*Récupération des informations relatives à la taille de l'écran*/
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_height = metrics.heightPixels;
        screen_width = metrics.widthPixels;

        /*Création des sprites relatifs au niveau de jeu*/
        ball_sprite = new Sprite(R.drawable.ball,context);
        paddle_sprite = new Sprite(R.drawable.paddle,context);
        green_block_sprite = new Sprite(R.drawable.greenblock,context);
        blue_block_sprite = new Sprite(R.drawable.blueblock, context);
        grey_block_sprite = new Sprite(R.drawable.greyblock, context);
        red_block_sprite = new Sprite(R.drawable.redblock, context);
        yellow_sprite = new Sprite(R.drawable.yellowblock, context);
        quit_sprite = new Sprite(R.drawable.quit, context);
        restart_sprite = new Sprite(R.drawable.restart, context);

        /*Récupération des informations relatives au niveau de jeu*/
        this.lvl_id = lvl_id; /*Important pour créer le niveau en fonction de son id*/
        this.maxLvl_unlocked = maxLvl_unlocked; /*Important pour indiquer quel niveau est débloqué si partie gagnée*/
        this.block_destroyed = 0; /*Important pour la quête qui demande de détruire un certain nombre de briques*/
        this.real_lvl = Integer.parseInt(lvl_id) + 1;

        /*Inialisation des boutons : Rejouer et Quitter le niveau de jeu*/
        restart = new GameButton(restart_sprite,context);
        restart.resize(128,128);
        restart.setCordx(screen_width/2-restart.getWidth()/2);
        restart.setCordy(screen_height/2-restart.getHeight()/2);

        quit = new GameButton(quit_sprite,context);
        quit.resize(128,128);
        quit.setCordx(screen_width/2-quit.getWidth()/2);
        quit.setCordy(restart.getCordy()+(restart.getHeight()*1.5f));

        /*Initialisation des indicateurs textuels de score et de "touch to play"*/
        score_textPaint = new TextPaint();
        score_textPaint.setFakeBoldText(true);
        score_textPaint.setTextSize(screen_width/15);
        score_textPaint.setTextAlign(Paint.Align.CENTER);
        score_textPaint.setColor(Color.WHITE);

        begin_textPaint = new TextPaint();
        begin_textPaint.setFakeBoldText(true);
        begin_textPaint.setTextSize(screen_width/25);
        begin_textPaint.setTextAlign(Paint.Align.CENTER);
        begin_textPaint.setColor(Color.WHITE);

        /*Construction du niveau : Méthode décrite plus bas*/
        initLevel();
        /* Initialisation ou Rénitialisation des éléments du niveau : Méthode décrite plus bas*/
        initGameElements(context);
    }

    /**
     * Définit comment agencé le niveau en fonction du lvl_id
     */
    public void initLevel(){
        /*Dans cette méthode, on définit une liste 2d représentant la structure
        * des briques du niveau ainsi que la vitesse de la Balle en fonction du lvl_id:
        * - La vitesse de la balle est calculée en fonction de la taille de l'écran
        * - Un élément de la liste représente un id de brique, l'indice de cet élément sa position*/

        switch (this.lvl_id){
            case "0":
                velocityX = screen_width / 68;
                velocityY = screen_height / 68;
                lvl = new int[][]{
                        {1,4,4,4,1,1,4,4,4,1},
                        {1,1,2,2,1,1,2,2,1,1},
                        {1,1,2,3,1,1,3,2,1,1},
                        {1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,5,5,1,1,1,1}};
                break;
            case "1":
                velocityX = screen_width / 68;
                velocityY = screen_height / 68;
                lvl = new int[][]{
                        {1,1,1,1,1,1,1,1,1,1},
                        {2,2,2,2,2,2,2,2,2,2},
                        {3,3,3,3,3,3,3,3,3,3},
                        {4,4,4,4,4,4,4,4,4,4},
                        {5,5,5,5,5,5,5,5,5,5}};
                break;
            case "2":
                velocityX = screen_width / 58;
                velocityY = screen_height / 58;
                lvl = new int[][]{
                        {0,1,0,0,0,0,0,0,1,0},
                        {0,0,2,2,2,2,2,2,0,0},
                        {0,2,2,3,2,2,3,2,2,0},
                        {2,0,2,2,2,2,2,2,0,2},
                        {5,0,2,0,0,0,0,2,0,5},
                        {2,0,0,4,0,0,4,0,0,2}};
                break;
            case "3":
                velocityX = screen_width / 48;
                velocityY = screen_height / 48;
                lvl = new int[][]{
                        {1,4,1,1,1,1,1,1,4,1},
                        {1,1,4,4,1,1,4,4,1,1},
                        {1,1,2,2,1,1,2,2,1,1},
                        {1,1,1,3,3,3,3,1,1,1},
                        {1,1,3,5,5,5,5,3,1,1}};
                break;

            case "4":
                velocityX = screen_width / 68;
                velocityY = screen_height / 68;
                lvl = new int[][]{
                        {1,4,4,4,1,1,4,4,4,1},
                        {1,1,2,2,1,1,2,2,1,1},
                        {1,1,2,3,1,1,3,2,1,1},
                        {1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,5,5,1,1,1,1}};
                break;
        }
    }

    /**
     * Initialisation ou Rénitialisation des éléments du niveau et construction du niveau
     * @param context Contexte dans lequel situer les ressources
     */
    public void initGameElements(Context context){
        /*Initialisation/RéInitialisation des états du jeu*/
        started = false;
        finished = false;

        /*Initialisation/RéInitialisation du Pad et de sa position*/
        paddle = new GameObject(paddle_sprite,context);
        paddle.resize(256,64);
        paddle.setCordx(screen_width/2);
        paddle.setCordy((screen_height / 10) * 8.5f);

        /*Initialisation/RéInitialisation de la Balle et de sa position*/
        ball = new GameObject(ball_sprite,context);
        ball.resize(60,60);
        ball.setCordx(screen_width/2-ball.getWidth()/2);
        ball.setCordy(screen_height/2-ball.getHeight()/2);

        /*Initialisation/RéInitialisation du score*/
        score = 0;

        /*Parcours de la liste lvl[][] et placement de la brique en fonction de son id et de sa position*/
        int b_cordy = screen_height/10;
        for(int i = 0; i < lvl.length;i++){
            int b_cordx = 0;
            for(int j = 0; j < lvl[i].length;j++){
                if(lvl[i][j]!=0){
                    Sprite sprite = null;
                    switch (lvl[i][j]){
                        /*On définit quel sprite à associer à la brique*/
                        case 1:
                            sprite = green_block_sprite;
                            break;
                        case 2:
                            sprite = red_block_sprite;
                            break;
                        case 3:
                            sprite = grey_block_sprite;
                            break;
                        case 4:
                            sprite = blue_block_sprite;
                            break;
                        case 5:
                            sprite = yellow_sprite;
                            break;
                    }
                    /*On crée un objet brique, avec le sprite choisi*/
                    GameObject block = new GameObject(sprite,context);
                    /*On redimensionne la brique*/
                    block.resize(screen_width/10,screen_width/10);
                    /*Et on la positionne au bon emplacement*/
                    block.setCordx(b_cordx);block.setCordy(b_cordy);
                    /*On ajoute la brique crée dans la liste block_list pour la considérer dans nos calculs*/
                    block_list.add(block);
                }
                b_cordx += screen_width/10;
            }
            b_cordy += screen_width/10;
        }
    }

    @Override
    /**
     * Méthode d'affichage actualisée en permanence tant que  le thread du jeu est en exécution
      */
    public void draw(Canvas canvas) {
        super.draw(canvas);

        /*Affichage des objets : Pad et de la Balle*/
        paddle.display(canvas);
        ball.display(canvas);

        /*Affichage des briques non détruites, contenues dans la liste : block_list*/
        for(GameObject b:block_list){
            b.display(canvas);
        }

        /*Si la partie est gagnée/terminée on affiche Un texte indicatif + les boutons "Rejouer" et "Quitter"*/
        if(finished){
            canvas.drawText(getResources().getString(R.string.terminatedGameText), screen_width/2, restart.getCordy()-(restart.getHeight()*1.5f), begin_textPaint);
            restart.display(canvas);
            quit.display(canvas);
        }

        /*Affichage du texte indicatif du score*/
        canvas.drawText("Score : " + score, screen_width/2, screen_height/20, score_textPaint);

        /*Si la partie n'est pas commencée, on affiche le texte indiquant à l'utilisateur qu'on attends son action pour commencer*/
        if(!started){
            canvas.drawText(getResources().getString(R.string.touchScreen), screen_width/2, screen_height/2, begin_textPaint);
        }
    }

    @Override
    /**
     * Méthode de mise à jour des variables actualisée en permanence tant que  le thread du jeu est en exécution
     */
    public void update() {
        super.update();

        /*Si la partie est terminée, on ré-initialise le niveau de jeu*/
        if(finished){
            if(restart.checkIfClicked(touch_cordx,touch_cordy)){
                initGameElements(getContext());
            }
        }

        /*Mise à jour de l'état du jeu : Méthode décrite plus bas*/
        updateGameState();

        /*Si la partie a commencé, on mets à jour les variables relatives aux objets : Ball et Pad*/
        if(started){
            if(!finished){
                updateBall(); /*Mise à jour de l'objet Balle : Méthode décrite plus bas*/
                updatePaddle(); /*Mise à jour de l'objet Pad : Méthode décrite plus bas*/
            }
        }

    }

    /**
     * Mets à jour l'état du jeu
     */
    public void updateGameState(){
        /*Si la liste des briques non détruites est vide, la partie est terminée*/
        if(block_list.isEmpty()){
            /*Si le niveau actuel n'est pas le dernier niveau du jeu,
            * On indique que le niveau est débloqué*/
            if(maxLvl_unlocked < 4 && real_lvl == maxLvl_unlocked) maxLvl_unlocked = real_lvl + 1;
            finished = true;
        }
    }

    /**
     * Mets à jour l'objet Pad : Mouvement du pad
     */
    public void updatePaddle(){

        if(touch_cordx+paddle.getWidth()/2 > screen_width){
            /*Si l'Objet pad dépasse de l'écran par la droite
            * On réajuste sa position pour que son sprite ne soit pas hors de l'écran*/
            paddle.setCordx(screen_width-paddle.getWidth());
        }else if(touch_cordx - paddle.getWidth()/2  < 0){
            /*Si l'Objet pad dépasse de l'écran par la gauche
             * On réajuste sa position pour que son sprite ne soit pas hors de l'écran*/
            paddle.setCordx(0);
        }
        else{
            /*Si l'objet pad ne dépasse pas de l'écran,
            * On le déplace à la position du doigt posé sur l'écran (Que pour la coordonnée x)*/
            paddle.setCordx(touch_cordx-paddle.getWidth()/2);
        }
    }

    /**
     * Mets à jour l'objet Balle : Comportement de la balle
     */
    public void updateBall(){

        /*Note : On considère la "vitesse" de la balle comme la quantité de donnée ajoutée aux coordonnées de la balle,
        * pour la déplacer*/

        /* On mets à jour la position de la balle avec les "vitesses" respectives*/
        ball.setCordx(ball.getCordx()+velocityX);
        ball.setCordy(ball.getCordy()+velocityY);

        /*Ci-dessous, on ajoute le rebond de la balle sur les bords de l'écran*/
        if (ball.getCordx() < 0) {
            /*Si la balle cogne l'extremité gauche de l'écran, on inverse sa "vitesse" en X*/
            velocityX = Math.abs(velocityX);
            /*On réajuste sa position pour que son sprite ne dépasse pas de l'écran*/
            ball.setCordx(0);
        } else if (ball.getCordx() + ball.getWidth() > screen_width) {
            /*Si la balle cogne l'extremité droite de l'écran, on inverse sa "vitesse" en X*/
            velocityX = -Math.abs(velocityX);
            /*On réajuste sa position pour que son sprite ne dépasse pas de l'écran*/
            ball.setCordx(screen_width - ball.getWidth());
        }

        if (ball.getCordy() < 0) {
            /*Si la balle cogne l'extremité haute de l'écran, on inverse sa "vitesse" en Y*/
            velocityY = Math.abs(velocityY);
            /*On réajuste sa position pour que son sprite ne dépasse pas de l'écran*/
            ball.setCordy(0);
        }

        if(ball.getCordy() > screen_height){
            /*Si la balle cogne l'extremité haute de l'écran, la partie est terminée*/
            finished = true;
        }

        /*Ci-dessous, on ajoute le rebond de la balle avec le Pad*/
        if (ball.entringEnCollisioningCarre(paddle)) {

            /*Si il y a collision entre la Balle et le Pad, on lui donne une "vitesse"  négative en Y, pour qu'elle aille vers le haut*/
            velocityY = -Math.abs(velocityY);

            /*On récupère la position du millieu du pad et de la balle,
            * Pour pouvoir déterminer si la balle a tapé sur la gauche du Pad,
            * ou de la droite*/

            float ball_centerPos = ball.getCordx() + (ball.getWidth()/2);
            float paddle_centerPos = (paddle.getCordx() + paddle.getWidth()/2);

            if ( ball_centerPos > paddle_centerPos ) {
                /*Si la balle a tapé à droite du pad, on lui donne une "vitesse"  positive en X, pour qu'elle aille vers la droite*/
                velocityX = Math.abs(velocityX);

            } else {
                /*Si la balle a tapé à gauche du pad, on lui donne une "vitesse"  négative en X, pour qu'elle aille vers la gauche*/
                velocityX = -Math.abs(velocityX);
            }
        }

        /*Ci-dessous, on ajoute le rebond de la balle avec les briques et on supprime la brique concernée*/
        for(GameObject b: block_list) {
            if(ball.entringEnCollisioningCarre(b)){
                /*Même stratégie, si la balle entre en collision avec la brique b,
                * On détermine dans quel sens la renvoyer comme plus haut*/
                if(ball.getCordy() < b.getCordy()){
                    velocityY = -Math.abs(velocityY);
                }else if(ball.getCordy() > b.getCordy()){
                    velocityY = Math.abs(velocityY);
                }else if(ball.getCordx() < b.getCordx()){
                    velocityX = -Math.abs(velocityX);
                }else{
                    velocityX = Math.abs(velocityX);
                }

                /*On incrémente le score de 10*/
                score += 10;
                /*On incrémente l'indicateur de briques détruites de 1*/
                block_destroyed += 1;
                /*Et on supprime la brique b de la liste (pour ne plus la considérer)*/
                block_list.remove(b);
            }

        }

    }

    /**
     * Récupération des inputs : Doigts de l'utilisateur
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();
        /*On récupère les coordonnées du touché de l'utilisateur*/
        touch_cordx = event.getX();
        touch_cordy = event.getY();
        switch(action) {
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
                if(!started)
                    /*Si la partie n'avait pas commencé, le touché lance la partie*/
                    started = true;
                if(quit.checkIfClicked(touch_cordx,touch_cordy) && finished){
                    /*Si la partie est terminée, et que le bouton "Quitter" est cliqué,
                    * On prépare le lancement de l'activité "Menu" en lui passant
                    * les variables contenant l'id du niveau maximum débloqué, et le nombre de briques détruites*/

                    Intent intent = new Intent(getContext(), SuperBouleMenuActivity.class);
                    intent.putExtra("block_destroyed",block_destroyed);
                    intent.putExtra("maxLvl_unlocked", maxLvl_unlocked);
                    /*On kill l'activité courante*/
                    ((Activity) getContext()).finish();
                    /*On lance l'activité menu*/
                    getContext().startActivity(intent);
                }
                break;
        }
        return true;
    }

}
