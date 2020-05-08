package com.ckfcsteam.replikapp.activities;

// Importations de bibliothèques
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ckfcsteam.replikapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    /* DEBUT :  Déclaration de variables */
    private TextView textErr;
    TextInputEditText signUpMail;
    TextInputEditText signUpPass;
    private TextView regToLog;
    private Button regBtn;
    private FirebaseAuth auth;
    /* FIN :  Déclaration de variables */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /* DEBUT : Récupération des ID XML */
        textErr = findViewById(R.id.textRErr);
        signUpMail = findViewById(R.id.mailRegTEField);
        signUpPass = findViewById(R.id.passRegTEField);
        regToLog = findViewById(R.id.regToLog);
        regBtn = findViewById(R.id.regBtn);
        auth = FirebaseAuth.getInstance();

        /* FIN : Récupération des ID XML */


        /* DEBUT : Ecouteur du bouton d'inscription*/
        ////////////////////////////////////////////////////////////////////////////////////////////

        /* Le bouton 'regBtn' vérifie que l'utilisateur entre les informations correctement avant de terminer l'opération:
         * Il vérifie :
         *       - Si le champ texte d'adresse mail est non vide,
         *       - Si le champ texte mot de passe est non vide,
         *       - Si le mot de passe contient au moins huit caractères.
         *
         * Puis effectue l'inscription grâce à la méthode proposée par FireBase pour l'inscription.
         * Celle-ci vérifie que la tâche éffectuée est complète.
         *       - Si oui, renvoie sur l'activité Login(connexion),
         *       - Si non, message d'erreur.*/

        ////////////////////////////////////////////////////////////////////////////////////////////

        regBtn.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n") // Erreur/Avertissement mis de côté pour permettre les chaînes de caractères dans les Toast
            @Override
            public void onClick(View v) {
                String email = signUpMail.getText().toString();
                String pass = signUpPass.getText().toString();


                /* Conditions pour mails et mdp corrects */

                //Si champ Email vide
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), R.string.mail_empty, Toast.LENGTH_LONG).show();
                    textErr.setText(R.string.mail_empty);
                    signUpMail.setError("Error");
                    return;

                }

                //Si champ mdp vide
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(),R.string.pw_empty, Toast.LENGTH_LONG).show();
                    textErr.setText(R.string.pw_empty);
                    return;
                }

                //Mot de passe de minimum 8 caractères pour la sécurité
                if(pass.length() < 8){
                    Toast.makeText(getApplicationContext(),R.string.short_pw, Toast.LENGTH_LONG).show();
                    textErr.setText(R.string.short_pw);
                }else{
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                                textErr.setText(R.string.reg_fail);
                            }else{
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finish();
                            }
                        }
                    });
                }
            }
        });
        /* FIN :  Ecouteur du bouton d'inscription*/

        /* DEBUT : Lien qui permet d'aller vers l'activité Login en un click */
        ////////////////////////////////////////////////////////////////////////////////////////////

        /* Création d'une instance de l'activité register puis on lance l'activité RegisterActivity pour register l'utilisateur*/

        ////////////////////////////////////////////////////////////////////////////////////////////
        regToLog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /* FIN : Lien qui permet d'aller vers l'activité Login en un click */


    }
    /* FIN : Méthode onCreate */
}