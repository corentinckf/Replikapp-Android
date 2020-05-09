package com.ckfcsteam.replikapp.library;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.MainThread;

public class Game extends SurfaceView implements SurfaceHolder.Callback{
    /*Classe écrite grâce au Tutoriel d'Adam Sinicki de la communauté androidauthority.com*/
    /*Déclaration des attributs*/
    private GameThread thread; /*Attribut qui contiendra le Thread*/

    /*Constructeur*/
    public Game(Context context) {
        super(context);
        getHolder().addCallback(this); /*On intercépte les événements*/
        thread = new GameThread(getHolder(), this); /*On crée l'objet GameThread*/
        setFocusable(true);
    }

    @Override
    /**
     * On lance le thread lorsque la SurfaceView est créée
     */
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true); /*On passe isRunning à true, pour commencer l'actualisation de update() et draw()*/
        thread.start(); /* Et on démarre le thread */
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    /**
     * On détruis le thread lorsque la SurfaceView est détruite
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        /*Ici, la Stratégie est de boucler tant que l'on arrive pas à interompre le thread*/
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false); /*On passe isRunning à false, pour interrompre l'actualisation de update() et draw()*/
                thread.join(); /*Bloque le thread jusqu'à qu'il s'arrête*/
            } catch (InterruptedException e) { e.printStackTrace();}
            retry = false; /* On sort de la boucle si on a réussi*/
        }
    }

    /**
     * Méthode à surcharger avec les variables à mettre à jour en permanence
     */
    public void update() {}

    public class GameThread extends Thread {
        /*Déclaration des attributs*/
        private SurfaceHolder surfaceHolder;
        private Game gameView;
        private boolean running;
        public Canvas canvas;

        /*Constructeur*/
        public GameThread(SurfaceHolder surfaceHolder, Game gameView){
            super();
            this.surfaceHolder = surfaceHolder;
            this.gameView = gameView;
        }

        /**
         * Passe running au booléen voulu en paramètre
         * @param isRunning la valeur voulue
         */
        public void setRunning(boolean isRunning) {running = isRunning;}

        @Override
        public void run() {
            /*Le code ci-dessous est tiré du tutoriel d'adam Sinicky d'androidauthority.com*/
            while (running) { /*Tant que le Thread est en cours d'éxécution*/
                canvas = null;
                try {
                    canvas = this.surfaceHolder.lockCanvas(); /*On lock le canvas pour qu'aucun autre Thread y accède*/
                    synchronized(surfaceHolder) {
                        this.gameView.update(); /*On appelle update */
                        this.gameView.draw(canvas); /*On mets à jour l'affichage*/
                    }
                } catch (Exception e) {}
                finally {
                    if (canvas != null){
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas); /*On relache le canvas : un repos bien mérité*/
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }
}


