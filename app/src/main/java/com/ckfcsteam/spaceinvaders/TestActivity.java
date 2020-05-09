package com.ckfcsteam.spaceinvaders;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ckfcsteam.replikapp.R;


public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //setContentView(new Game1(getApplicationContext()));
        // Layout du jeu Infinity invaders
        setContentView(R.layout.fragment_space_game);
        LinearLayout surface = findViewById(R.id.mySurfaceInv);
        surface.addView(new Game1(getApplicationContext()));
    }
}
