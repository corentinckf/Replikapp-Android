package com.ckfcsteam.replikapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ckfcsteam.replikapp.R;

public class RegisterActivity extends AppCompatActivity {

    /* Déclaration de variables */
    private TextView login_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //////////////////////////////////////////////////////////////////////
        //Attention à bien appeller ces deux fonctions avant setContentView()
        /*On passe cette activité en mode plein écran*/
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        /* On enlève l'affichage du titre de l'activité */
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        //////////////////////////////////////////////////////////////////////
        setContentView(R.layout.activity_register);

        /* DEBUT : Lien qui permet d'aller vers l'activité Login en un click */
        login_link = findViewById(R.id.loginFromRegister_link);
        login_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class); // Création d'une instance de l'activité register
                startActivity(intent); //On lance l'activité RegisterActivity pour register l'utilisateur
                finish();
            }
        });
        /* FIN : Lien qui permet d'aller vers l'activité Login en un click */
    }
}
