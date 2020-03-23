package com.ckfcsteam.replikapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    /* Déclaration de variables */
    private TextView register_link;

    private FirebaseAuth auth;
    private String email;
    private String password;

    private Button login_button;

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
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance(); // Initialisation de L'Auth Firebase

        login_button = findViewById(R.id.login_button);

        /* DEBUT : Lien qui permet d'aller vers l'activité Register en un click */
        register_link = findViewById(R.id.registerFromLogin_link);
        register_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class); // Création d'une instance de l'activité register
                startActivity(intent); //On lance l'activité RegisterActivity pour register l'utilisateur
                finish();
            }
        });
        /* FIN : Lien qui permet d'aller vers l'activité Register en un click */

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signInWithEmailAndPassword("test","123")
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) { // Si la connexion aboutie
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Création d'une instance de l'activité Main
                                    startActivity(intent); //On lance l'activité MainActivity pour register l'utilisateur
                                    finish();
                                } else {} // Sinon on porte un Toast à l'échec
                            }
                        });
            }
        });

    }


}
