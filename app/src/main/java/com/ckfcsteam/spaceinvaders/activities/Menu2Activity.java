package com.ckfcsteam.spaceinvaders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;

public class Menu2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ii);
    }

    /**
     * Lors du clic sur le bouton facile lancera l'activité du jeu en mode facile
     *
     * @param view le bouton
     */
    public void easy(View view){
        Intent intent = new Intent(Menu2Activity.this, GameActivity.class);
        // On passe le niveau (easy = 1)
        intent.putExtra("level", 1);
        startActivity(intent);
    }
    /**
     * Lors du clic sur le bouton normal lancera l'activité du jeu en mode normal
     *
     * @param view le bouton
     */
    public void normal(View view){
        Intent intent = new Intent(Menu2Activity.this, GameActivity.class);
        // On passe le niveau (normal = 2)
        intent.putExtra("level", 2);
        startActivity(intent);
    }
    /**
     * Lors du clic sur le bouton difficile lancera l'activité du jeu en mode difficile
     *
     * @param view le bouton
     */
    public void hard(View view){
        Intent intent = new Intent(Menu2Activity.this, GameActivity.class);
        // On passe le niveau (hard = 3)
        intent.putExtra("level", 3);
        startActivity(intent);
    }

    /**
     * Lors du clic sur le bouton home renverra l'utilisateur à l'acceuil
     *
     * @param view le bouton image
     */
    public void home(View view){
        Intent intent = new Intent(Menu2Activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Lors du clic sur le bouton score lancera l'activité affichant les scores
     *
     * @param view le bouton image
     */
    public void score(View view){
        Intent intent = new Intent(Menu2Activity.this, Score2Activity.class);
        startActivity(intent);
    }
}
