package com.ckfcsteam.spaceinvaders.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.spaceinvaders.GameSurfaceView;

/**
 * Activité de l'écran de jeu infinity invaders
 */
public class GameActivity extends AppCompatActivity {
    // Attributs
    private GameSurfaceView mySurface;
    private TextView score;
    private ImageButton stateImg;
    private ImageButton playImg;
    private ImageButton songImg;
    private MajScoreThread majScoreThread;
    private boolean muted;
    private final String NAME = "infinity";
    private final String MUTED = "mute";
    private MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Layout du jeu Infinity invaders
        setContentView(R.layout.activity_game_ii);

        // Recupération du niveau choisie indiqué par l'activité précédente
        Intent intent = getIntent();
        // Niveau choisie par le joueur
        int level = 0;
        if(intent != null ){
            level = intent.getIntExtra("level", 0);
        }

        // Ajout de notre surface view dans le layout
        LinearLayout surface = findViewById(R.id.mySurfaceInv);
        mySurface = new GameSurfaceView(getApplicationContext(), level, this);
        surface.addView(mySurface);

        // Recuperation des éléments du layout
        score = findViewById(R.id.nScore);
        stateImg = findViewById(R.id.stateImg);
        playImg = findViewById(R.id.playImg);
        songImg = findViewById(R.id.songImg);

        // Gestion du score
        majScoreThread = new MajScoreThread();
        majScoreThread.start();

        // Gestion du son
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tetristheme);
        mediaPlayer.setLooping(true);

        mediaPlayer.start();

        /* Chargement des préférences pour le son */
        // Charge du fichier de sauvegarde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(NAME, MODE_PRIVATE);
        muted = sharedPreferences.getBoolean(MUTED, false);

        if(muted){
            songImg.setImageResource(android.R.drawable.ic_lock_silent_mode);
            songImg.setBackgroundColor(Color.RED);
            mediaPlayer.pause();

        }else{
            songImg.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
            songImg.setBackgroundColor(Color.GREEN);
        }

    }

    /**
     * Mets le jeu en pause, ou le retire en fontion de sont état actuelle
     * @param view Le bouton qui a été cliqué
     */
    public void playPause(View view){
        System.out.println(mySurface.isDisabled());
        mySurface.setDisabled(!mySurface.isDisabled());
        if(mySurface.isDisabled()){
            stateImg.setImageResource(android.R.drawable.ic_media_play);
            playImg.setVisibility(View.VISIBLE);
        }else{
            stateImg.setImageResource(android.R.drawable.ic_media_pause);
            playImg.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Active/désactive la musique en fontion de l'état actuelle
     * @param view Le bouton qui a été cliqué
     */
    public void songOnOff(View view){
        muted = !muted;
        if(muted){
            songImg.setImageResource(android.R.drawable.ic_lock_silent_mode);
            songImg.setBackgroundColor(Color.RED);
            //TODO eteindre la musique
            mediaPlayer.pause();

        }else{
            songImg.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
            songImg.setBackgroundColor(Color.GREEN);
            //TODO jouer la musique
            mediaPlayer.start();
        }

        // Sauvegardes des preférences pour le son
        SharedPreferences sharedPreferences = getSharedPreferences(NAME, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(MUTED, muted).apply();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        majScoreThread.interrupt();
        mediaPlayer.stop();
    }

    /**
     * Thread qui mets à jour le score toutes les 20ms
     */
    private class MajScoreThread extends Thread{
        @Override
        public void run() {
            super.run();
            try{
                while(!interrupted()){
                    // TODO Regler les conflit de view
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            score.setText(mySurface.getScore()+"");
                        }
                    });
                    sleep(20);
                }

            }catch (InterruptedException e){}
        }
    }
}
