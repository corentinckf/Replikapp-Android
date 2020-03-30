package com.ckfcsteam.replikapp.activities;

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

    /* Déclaration de variables */

    private TextView regToLog;
    private TextView textErr;
    private Button regBtn;
    private FirebaseAuth auth;

    TextInputEditText signUpMail;
    TextInputEditText signUpPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /* Récupération des ID XML */

        signUpMail = findViewById(R.id.mailRegTEField);
        signUpPass = findViewById(R.id.passRegTEField);
        auth = FirebaseAuth.getInstance();
        regBtn = findViewById(R.id.regBtn);
        textErr = findViewById(R.id.textRErr);


        /* Ecouteur du bouton d'inscription*/
        regBtn.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String email = signUpMail.getText().toString();
                String pass = signUpPass.getText().toString();


                /* Conditions pour mails et mdp corrects */

                //Si champ Email vide
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), R.string.mail_empty, Toast.LENGTH_LONG).show();
                    textErr.setText(R.string.mail_empty);
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

        /* DEBUT : Lien qui permet d'aller vers l'activité Login en un click */
        regToLog = findViewById(R.id.regToLog);
        regToLog.setOnClickListener(new View.OnClickListener() {

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
