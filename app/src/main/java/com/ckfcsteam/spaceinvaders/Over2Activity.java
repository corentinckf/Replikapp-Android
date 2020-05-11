package com.ckfcsteam.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;

public class Over2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over2);
    }

    /**
     * Lors du clic sur le bouton home renverra l'utilisateur à l'acceuil
     *
     * @param view le bouton image
     */
    public void home(View view){
        Intent intent = new Intent(Over2Activity.this, Menu2Activity.class);
        startActivity(intent);
        finish();
    }
}
