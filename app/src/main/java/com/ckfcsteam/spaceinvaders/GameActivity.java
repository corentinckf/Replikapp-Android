package com.ckfcsteam.spaceinvaders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ckfcsteam.replikapp.R;


public class GameActivity extends AppCompatActivity {
    // Attributs
    private Game1 mySurface;
    private TextView score;
    private ImageButton stateImg;
    private ImageButton playImg;
    private MajScoreThread majScoreThread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //setContentView(new Game1(getApplicationContext()));
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
        mySurface = new Game1(getApplicationContext(), level, this);
        surface.addView(mySurface);
        // Recuperation des éléments du layout
        score = findViewById(R.id.nScore);
        stateImg = findViewById(R.id.stateImg);
        playImg = findViewById(R.id.playImg);

        // Gestion du score
        majScoreThread = new MajScoreThread();
        majScoreThread.start();
    }

    public void playPause(View view){
        System.out.println(mySurface.isDisabled());
        mySurface.setDisabled(!mySurface.isDisabled());
        if(mySurface.isDisabled()){
            stateImg.setImageResource(android.R.drawable.ic_media_play);
            playImg.setVisibility(View.VISIBLE);
        }else{
            stateImg.setImageResource(android.R.drawable.ic_media_pause);
            playImg.setVisibility(View.INVISIBLE);
            //playImg.setImageResource(0);
            //playImg.setEnabled(false);
        }
    }

    private void gameOver(){
        System.out.println("It's lose man");
        Intent intent = new Intent(GameActivity.this, Over2Activity.class);
        intent.putExtra("score", mySurface.getScore());
        intent.putExtra("mode", mySurface.getLevel());
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        majScoreThread.interrupt();
    }

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
