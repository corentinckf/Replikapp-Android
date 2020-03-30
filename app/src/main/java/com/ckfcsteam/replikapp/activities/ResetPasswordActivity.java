package com.ckfcsteam.replikapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ckfcsteam.replikapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    /* Déclaration des variables */
    private TextInputEditText textMailR;
    private TextView textErr;
    private Button resetBtn;
    private Button cancelBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        /* Récupération des ID XML */
        resetBtn = findViewById(R.id.resetPassBtn);
        cancelBtn = findViewById(R.id.cancelRPBtn);
        textMailR = findViewById(R.id.mailResetTEField);
        textErr = findViewById(R.id.textResetErr);
        auth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textMailR.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplication(), R.string.mail_empty, Toast.LENGTH_SHORT).show();
                    textErr.setText(R.string.mail_empty);

                }
                else{
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPasswordActivity.this, R.string.email_sent, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ResetPasswordActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
