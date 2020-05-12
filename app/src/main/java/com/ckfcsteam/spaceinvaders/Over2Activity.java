package com.ckfcsteam.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;

public class Over2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over2);
        // Recuperation du mode de jeu et du score
        Intent intent = getIntent();
        int mode = -1;
        int score = -1;
        if(intent != null ){
            mode = intent.getIntExtra("mode", -1);
            score = intent.getIntExtra("score", -1);
        }
        // Affichage du score
        TextView scoreView = findViewById(R.id.score);
        scoreView.setText(score+"");
        // Charge du fichier de sauvegarde SharedPreferences
        String highScoreKey = "high"+mode;
        SharedPreferences sharedPreferences = getSharedPreferences("infinity", MODE_PRIVATE);
        // Récupération du meilleur score
        int highScore = sharedPreferences.getInt(highScoreKey, -1);
        //System.out.println(highScoreKey);
        //scoreView.setText(highScoreKey);
        if(highScore != -1){
            TextView hScoreView = findViewById(R.id.highScore);
            hScoreView.setText(highScore+"");
        }else{
            TextView hScoreView = findViewById(R.id.highScore);
            hScoreView.setText("- - -");
        }

        // Sauvegarde du meilleurs score, si il a changé
        if(score > highScore){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(highScoreKey, score);
            editor.apply();
        }

    }

    /**
     * Lors du clic sur le bouton home renverra l'utilisateur à l'acceuil
     *
     * @param view le bouton image
     */
    public void home(View view){
        Intent intent = new Intent(Over2Activity.this, Menu2Activity.class);
        startActivity(intent);
        finish();
    }
}
