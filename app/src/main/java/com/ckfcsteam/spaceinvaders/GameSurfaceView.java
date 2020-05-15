package com.ckfcsteam.spaceinvaders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ckfcsteam.spaceinvaders.activities.Over2Activity;
import com.ckfcsteam.spaceinvaders.gamelib.Invaders;
import com.ckfcsteam.spaceinvaders.gamelib.ProjectilesInvaders;
import com.ckfcsteam.spaceinvaders.gamelib.ProjectilesShip;
import com.ckfcsteam.spaceinvaders.gamelib.Ship;

/**
 * Surface view de Infinity Invaders
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback{


    private SurfaceHolder holder;
    private GameThread thread = null;

    /* Attributs global du jeu */

    // Detection de gesture
    private GestureDetector mGestureDetector;
    // Taille de l'écran
    private int screenWidth;
    private int screenHeight;
    // Creation du vaisseau
    private Ship vaisseau;
    // Creation des invaders
    private Invaders invaders;
    // Creation des projectiles du vaisseau
    private ProjectilesShip projectilesShip;
    // Creation des projectiles ennemis
    private ProjectilesInvaders projectilesInvaders;
    // Score de la partie
    private int score;
    // Etat du jeu
    private boolean disabled;
    // Niveau de la partie
    private int level;
    // Activity de l'app
    private Activity activity;

    /* Constructeur */
    public GameSurfaceView(final Context context, int level, Activity act) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);

        // Le jeu n'est pas encore chargé
        this.level = level;
        activity = act;

        // Gestion des "one single tap""
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                System.out.println("SingleTap");
                if(!isDisabled() && vaisseau.checkIfClicked(event.getX(), event.getY())){
                    // Clicker sur le vaisseau pour tirer
                    projectilesShip.add(vaisseau.shoot(context, screenWidth /32, screenWidth /40));

                }
                return super.onSingleTapUp(event);
            }
        });

        //thread = new GameThread();
    }


    /* Methodes callback sur holder */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Initialisation du score
        score = 0;
        // Le jeu est mis en pause en début de partie
        disabled = true;
        // Recuperation des dimensions de l'écran
        screenWidth = getWidth();
        screenHeight = getHeight();
        // Parametres initiaux des element du jeu
        // Creation des objet du jeu
        vaisseau = new Ship(getContext());
        invaders = new Invaders(screenWidth, screenHeight,getContext());
        projectilesShip = new ProjectilesShip(screenWidth, screenHeight);
        projectilesInvaders = new ProjectilesInvaders(screenWidth, screenHeight);

         // Config du vaisseau
        vaisseau.setCordx(((float) screenWidth/2) - ((float)vaisseau.getWidth()/2));
        vaisseau.setCordy(screenHeight - vaisseau.getHeight()-10);
        vaisseau.resize(screenWidth /5, screenWidth /4);

        // On relance tout à chaque fois que la surfaceview est relancée
        thread = new GameThread();
        thread.isRunning = true;
        thread.start();
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
        // Game over si on sort de l'écran
        gameOver();
    }

    /* Méthodes du jeu */

    /**
     * Maj des éléments du jeu
     */
    public void update(){
        // Modifie les élements si le jeu n'est pas en pause
        if(!isDisabled()){
            // Mouvement des projectiles alliés
            projectilesShip.move(screenHeight /100);

            // Maj du score en fonction du mode de jeu
            score += projectilesShip.hit(invaders)*level;

            // Maj des lignes d'invaders
            invaders.majLines();

            // Descente des invaders
            invaders.raid((float) screenHeight /2000);

            /* Gestion des niveaux */
            if(level > 1){
                // Mouvement horizontal des invaders
                invaders.moveAllLR((float) screenWidth /1000, screenWidth);

                if(level > 2){
                    // Ajout des projectiles des nouveaux tirs ennemis, si il y en a
                    projectilesInvaders.addAll(invaders.FirstLineShoot(screenWidth, screenHeight));

                    // Gestion des deplacement des projectiles ennemis
                    projectilesInvaders.move((float) screenHeight /500);//70);

                    // Gestion des collisions entre projectiles ennemis
                    projectilesInvaders.annulation(projectilesShip);
                }

            }

            // Detection Game Over
            if(invaders.firstLineCordY() > screenHeight-vaisseau.getHeight() || projectilesInvaders.hitShip(vaisseau)){
                activity.finish();
            }
        }
    }

    /**
     * Désactive les animations et renvoie vers l'écran du game over
     */
    private void gameOver(){
        setDisabled(true);
        System.out.println("It's lose man");
        Intent intent = new Intent(getContext(), Over2Activity.class);
        intent.putExtra("score", getScore());
        intent.putExtra("mode", getLevel());
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();

        // Couleur du fond d'écran
        canvas.drawARGB(255,0,0,0);

        // Affichage des projectiles du vaisseau
        projectilesShip.displayAll(canvas);

        // Affichage du vaisseau
        vaisseau.display(canvas);

        // Affchage des invaders
        invaders.displayAll(canvas);

        // Affichage projectiles des invaders
        projectilesInvaders.displayAll(canvas);

    }

    /* Gestion des évenements */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /* Traitement par mGestureDetector pour detecter les single tap*/
        mGestureDetector.onTouchEvent(event);

        //  Detection des mouvements pour le deplacement de vaisseau horizontalement
        int action = event.getActionMasked();
        if(MotionEvent.ACTION_MOVE == action) {
            if(!isDisabled()){
                // Mouvements du vaisseau avec les doigt
                // tout en l'ajustant au centre du vaisseau
                float touch_cordx = event.getX() - vaisseau.getWidth() / 2;
                float touch_cordy = event.getY();
                if(touch_cordy <= vaisseau.getCordy()+ vaisseau.getHeight() && touch_cordy >= vaisseau.getCordy()){
                    vaisseau.setCordx(touch_cordx);
                }
            }
        }
        return true;
    }

    /*Getters & Setters*/

    /**
     * Desactive/Active tous les projectiles
     *
     * @param b si b est vrai desactive, sinon les actives
     */
    public void setDisabled(boolean b){
        disabled = b;
        /*projectilesShip.setDisabled(b);
        projectilesInvaders.setDisabled(b);
        invaders.setDisabled(b);
        avion.setDisabled(b);*/

    }

    public int getLevel() {
        return level;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public int getScore() {
        return score;
    }

    /**
     * Classe GameThread qui gère l'affichage du jeu dans un thread independant
     */
    public class GameThread extends Thread {

        boolean isRunning = true;

        @Override
        public void run() {

            while (isRunning) {
                Canvas canvas = null;

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

}