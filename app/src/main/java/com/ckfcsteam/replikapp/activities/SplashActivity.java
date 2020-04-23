package com.ckfcsteam.replikapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;

//TODO Import à  effacer avant de rendre
import com.ckfcsteam.spaceinvaders.TestActivity;

public class SplashActivity extends AppCompatActivity {

    /* Attributs */
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /* Lancement du splashscreen pour */
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, TestActivity.class);
                //TODO Remplacer la ligne ci-dessus par la ligne commeté ci-dessous
                //Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
