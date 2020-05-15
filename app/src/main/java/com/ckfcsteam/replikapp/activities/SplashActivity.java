package com.ckfcsteam.replikapp.activities;

// Importation des bibliothèques
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;

//TODO Import à  effacer avant de rendre
import com.ckfcsteam.spaceinvaders.activities.Menu2Activity;

public class SplashActivity extends AppCompatActivity {

    /* Attributs */
    Handler handler;

    /* DEBUT : Méthode onCreate pour le SplashSreen/Ecran de lancement de l'app */
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /* Instance Handler qui va permettre de lancer une activité pendant un temps imparti (ici, 3 secondes).*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Lance une activité dans un temps imparti (3 secondes) pour renvoyer vers l'activité de connexion
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
    /*FIN : méthode onCreate */
}
