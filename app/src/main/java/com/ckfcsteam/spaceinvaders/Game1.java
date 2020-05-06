package com.ckfcsteam.spaceinvaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ckfcsteam.spaceinvaders.gamelib.Invaders;
import com.ckfcsteam.spaceinvaders.gamelib.Projectile;
import com.ckfcsteam.spaceinvaders.gamelib.Ship;

import java.util.ArrayList;

public class Game1 extends SurfaceView implements SurfaceHolder.Callback{

    //TODO Detetcter la collision entre les invaders et les parois
    // pour changer leur direction horizontale

    private SurfaceHolder holder = null;
    private GameThread thread = null;

    /* Attributs global du jeu */

    //
    private GestureDetector mGestureDetector;

    // Taille de l'écran
    private int x;
    private int y;
    // Creation du vaisseau
    private Ship avion ;
    // Creation des invaders
    private Invaders invaders;
    // Creation d'un tableau de projectiles
    private ArrayList<Projectile> projectiles;
    // Recuperation de l'emplacement du doigt de l'utilisateur
    private float touch_cordx;
    private float touch_cordy;
    // Score de la partie
    private int score;
    private String stringOfScore;

    /* Constructeur */
    public Game1(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);

        // Gestion des clic simple
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                System.out.println("up");
                if(avion.checkIfClicked(event.getX(), event.getY())){
                    Projectile projectile = new Projectile(getContext(), avion.getMidCordX(),avion.getCordy()+100);
                    projectile.resize(x/32, x/40);
                    projectiles.add(projectile);
                }
                return super.onSingleTapUp(event);
            }
        });

        //thread = new GameThread();
    }


    /* Methodes callback sur holder */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //this.holder = holder;
        System.out.println(getWidth());
        // Initialisation du score
        score = 0;
        stringOfScore = "Score : " + score;
        // Recuperation des dimensions de l'écran
        x = getWidth();
        y = getHeight();
        // Parametres initiaux des element du jeu
        // Creation des objet du jeu
        avion = new Ship(getContext());
        invaders = new Invaders(x,y,getContext());
        projectiles = new ArrayList<>();


        // Config avion
        avion.setCordy(y-avion.getHeight()-10);
        avion.resize(x/5,x/4);

        //Config invaders
        invaders.resize(x/16, x/20);


        // On relance tout à chaque fois que la surfaceview est relancée
        thread = new GameThread();
        thread.isRunning = true;
        thread.start();

        //invader = new Invader(R.drawable.invadersm1, getContext());


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //thread.isRunning = true;
        //thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("Finiser974");
        thread.isRunning = false;

        boolean joined = false;
        while (!joined) {
            try {
                thread.join();
                joined = true;
            } catch (InterruptedException e) {}
        }
        System.out.println("ReallyFinish00000");
    }

    /* Méthodes du jeu */
    public void update(){
        // Gestion du mouvement des projectiles
        // et suppression de ceux qui sorte de l'écran
        for(int i=0; i<projectiles.size(); i++){
            if(projectiles.get(i).getCordy() < projectiles.get(i).getHeight())
                projectiles.remove(i);
            else{
                //projectiles.get(i).move(y/30);
                projectiles.get(i).move(y/100);
                if(invaders.entringEnCollisioningCarreAll(projectiles.get(i))){
                    projectiles.remove(i);
                    score ++;
                }
            }
        }
        // Maj du score
        stringOfScore = "Score : " + score;
        // Maj des lignes
        invaders.majLines();
        invaders.resize(x/16, x/20);
        // Descente des invaders
        invaders.raid(y/2000);
        // Detection des invaders détruits
        if(invaders.firstLineCordY() > y){
            for(int i=0; i<projectiles.size(); i++){
                projectiles.get(i).setDisabled(true);
            }
            invaders.setDisabled(true);
            avion.setDisabled(true);

        }

        // Mouvement horizontal des invaders
        invaders.moveAllLR(x/1000, x);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();

        //avion.setCordx((x/2)-(avion.getWidth()/2));
        //avion.setCordy(y-avion.getHeight()-10);
        //x = getWidth();
        //System.out.println(x);
        //y = getHeight();
        // Config avion
        //avion.setCordy(y-avion.getHeight()-10);
        //avion.resize(x/5,x/4);

        //Config invaders
        //invader.resize(x/8, x/10);
        //invaders.resize(x/16, x/20);

        // Couleur du fond d'écran
        canvas.drawARGB(255,150,150,10);
        // Affichage des projectiles
        for(int i=0; i<projectiles.size(); i++){
            projectiles.get(i).display(canvas);
        }
        // Affichage du vaisseau
        avion.display(canvas);
        // Affchage des invaders
        invaders.displayAll(canvas);
        // Affichage du score
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawText(stringOfScore,x/2,100, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(stringOfScore,0,100, paint);
        //invader.display(canvas);
        //System.out.println("draw");
    }

    /* Gestion des évenements */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);


        /**/
        mGestureDetector.onTouchEvent(event);
        /*
        (new GestureDetector.SimpleOnGestureListener(){

        })
        */

        int action = event.getActionMasked();

        switch(action) {
            case MotionEvent.ACTION_MOVE:
                // Mouvements du vaisseau avec les doigt
                // tout en l'ajustant au centre du vaisseau
                touch_cordx = event.getX()-avion.getWidth()/2;
                touch_cordy = event.getY()/*-avion.getHeight()/2*/;
                if(touch_cordy<=avion.getCordy()+avion.getHeight() && touch_cordy>=avion.getCordy()){

                    avion.setCordx(touch_cordx);
                    //avion.setCordy(touch_cordy);
                }


                break;

            case MotionEvent.ACTION_UP:
                //onPause = onPause ? false : true;

                break;
        }
        return true;
    }


    /* Classe GameThread qui gère l'affichage du jeu dans un thread independant */
    public class GameThread extends Thread {

        boolean isRunning = true;

        @Override
        public void run() {

            while (isRunning) {
                Canvas canvas = null;
                //System.out.println("run");

                try {
                    // On récupère le canvas pour dessiner dessus
                    canvas = holder.lockCanvas();
                    // On s'assure qu'aucun autre thread n'accède au holder
                    synchronized (holder) {
                        // Et on dessine
                        update();
                        draw(canvas);

                    }
                } finally {
                    // Notre dessin fini, on relâche le Canvas pour que le dessin s'affiche
                    if (canvas != null)
                        holder.unlockCanvasAndPost(canvas);
                }

                // Pour dessiner à 50 fps
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {}
            }
        }
    }

    /*private class mGestureDetector extends GestureDetector.SimpleOnGestureListener{

    }*/
}