package com.ckfcsteam.spaceinvaders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.replikapp.activities.LoginActivity;

/**
 * Activité du menu de infinity invaders
 */
public class Menu2Activity extends AppCompatActivity {

    // Nom du fichier de sauvegarde sharedPreferences
    private final String NAME = "infinity";
    // Meilleurs score dans chaque mode
    private int[] highScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ii);

        // Récupération des meilleurs score
        highScores = new int[3];
        SharedPreferences sharedPreferences = getSharedPreferences(NAME, MODE_PRIVATE);

        for (int i = 0; i < highScores.length; i++) {
            // "high"+nombre associé au mode de jeu
            String highScoreKey = "high" + (i + 1);
            int highScore = sharedPreferences.getInt(highScoreKey, -1);
            highScores[i] = highScore;
        }
    }

    /**
     * Lors du clic sur le bouton facile lancera l'activité du jeu en mode facile
     *
     * @param view le bouton
     */
    public void easy(View view) {
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
    public void normal(View view) {
        if (highScores[0] < 10) {
            String msg = getResources().getString(R.string.toastInvaders1);
            Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            toast.show();

        } else {
            Intent intent = new Intent(Menu2Activity.this, GameActivity.class);
            // On passe le niveau (normal = 2)
            intent.putExtra("level", 2);
            startActivity(intent);
        }

    }

    /**
     * Lors du clic sur le bouton difficile lancera l'activité du jeu en mode difficile
     *
     * @param view le bouton
     */
    public void hard(View view) {
        if (highScores[1] < 20) {
            String msg = getResources().getString(R.string.toastInvaders2);
            Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            toast.show();
        } else {
            Intent intent = new Intent(Menu2Activity.this, GameActivity.class);
            // On passe le niveau (hard = 3)
            intent.putExtra("level", 3);
            startActivity(intent);
        }

    }

    /**
     * Lors du clic sur le bouton home renverra l'utilisateur à l'acceuil
     *
     * @param view le bouton image
     */
    public void home(View view) {
        exit_Confirmation();
    }

    /**
     * Lors du clic sur le bouton score lancera l'activité affichant les scores
     *
     * @param view le bouton image
     */
    public void score(View view) {
        Intent intent = new Intent(Menu2Activity.this, Score2Activity.class);
        startActivity(intent);
    }


    public void exit_Confirmation() {
        /*On crée une boite de dialogue pour confirmer/annuler l'arrêt du jeu*/
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.quitGame)
                .setMessage(R.string.quitGameConf)
                .setPositiveButton(R.string.positiveAnswer, new DialogInterface.OnClickListener() {
                    /*On pose sur le "positiveButton" un listener*/
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Et on kill l'activité actuelle*/
                        Intent intent = new Intent(Menu2Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                })
                .setNegativeButton(R.string.negativeAnswer, null)
                .show();
    }
}