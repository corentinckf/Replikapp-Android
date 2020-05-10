package com.ckfcsteam.spaceinvaders;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ckfcsteam.replikapp.R;


public class GameActivity extends AppCompatActivity {
    // Attributs
    private Game1 mySurface;
    private TextView score;
    private MajScoreThread majScoreThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //setContentView(new Game1(getApplicationContext()));
        // Layout du jeu Infinity invaders
        setContentView(R.layout.activity_game_ii);
        // Ajout de notre surface view dans le layout
        LinearLayout surface = findViewById(R.id.mySurfaceInv);
        mySurface = new Game1(getApplicationContext());
        surface.addView(mySurface);
        // Recuperation du text view qui contient le score
        score = findViewById(R.id.nScore);
        // Gestion du score
        majScoreThread = new MajScoreThread();
        majScoreThread.start();
    }

    public void playPause(View view){
        System.out.println(mySurface.isDisable());
        mySurface.setDisabled(!mySurface.isDisable());
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
