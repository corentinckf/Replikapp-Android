package com.ckfcsteam.replikapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ckfcsteam.replikapp.surfaceviews.SuperBouleSurfaceView;

public class SuperBouleActivity extends AppCompatActivity {

    /*Variable qui contiendra la SurfaceView du jeu SuperBoule*/
    private SuperBouleSurfaceView SuperBoule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*On utilise la méthode des diapos pour passer des valeurs d'activité en activité,
        * sauf que dans ce cas on a : d'activité à SurfaceView*/
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            /*On récupere l'id du niveau*/
            String lvl_id = extras.getString("lvl_id");
            /*On récupere le niveau débloqué maximum*/
            int maxlvl_unlocked = extras.getInt("maxLvl_unlocked");
            /*On crée la SurfaceView en lui passant les valeurs*/
            SuperBoule = new SuperBouleSurfaceView(this, lvl_id, maxlvl_unlocked);
        }
        /*On lie la SurfaceView du jeu SuperBoule à cette activité*/
        setContentView(SuperBoule);
    }
}
