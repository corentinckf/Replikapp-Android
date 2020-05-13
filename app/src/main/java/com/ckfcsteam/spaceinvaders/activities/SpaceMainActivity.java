package com.ckfcsteam.spaceinvaders.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ckfcsteam.spaceinvaders.GameSurfaceView;

public class SpaceMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(new GameSurfaceView(getApplicationContext(),1568, this));
    }
}
