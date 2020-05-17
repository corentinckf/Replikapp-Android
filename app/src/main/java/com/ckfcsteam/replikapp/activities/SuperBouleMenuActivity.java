package com.ckfcsteam.replikapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.replikapp.fragments.LevelSelection;
import com.ckfcsteam.replikapp.fragments.QuestPanel;

public class SuperBouleMenuActivity extends AppCompatActivity {
    /*Variables qui contiendront les différents boutons*/
    private Button play_button,quit_button,quests_button;
    /*Variables qui contiendront les différents fragments*/
    private Fragment levelSelectionPanel_fragment, questPanel_fragment;
    /*Variables qui contiendront le niveau max débloqué et le nombre de briques détruites*/
    private int maxLvl_unlocked, block_numbers;
    /*Variable qui contiendra les préférences utilisateurs*/
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*On attache à cette activité, le xml menu du jeu Superboule*/
        setContentView(R.layout.superboule_menu);

        /*On récupère les Shared preferences*/
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        /*On tente de récuperer le niveau max débloqué et le nombre de briques détruites,
        * si on n'y arrive pas on attribut une valeur par défaut : Respectivement 1 et 0*/

        maxLvl_unlocked = sharedPreferences.getInt("maxLvl_unlocked", 1);
        block_numbers = sharedPreferences.getInt("block_destroyed", 0);

        /*On "surchage" cette récupération de données, par une tentative de récupération du niveau max débloqué
        * et du nombre de briques détruites passé par la SurfaceView du jeu SuperBoule*/

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            maxLvl_unlocked = extras.getInt("maxLvl_unlocked");
            block_numbers += extras.getInt("block_destroyed");
        }


        /*On initialise les fragments contenant le menu de sélection de niveau et le paneau des quêtes*/
        levelSelectionPanel_fragment = new LevelSelection(maxLvl_unlocked);
        questPanel_fragment = new QuestPanel(maxLvl_unlocked, block_numbers);

        /*Et on affiche par défaut le fragment de sélection de niveau*/
        showFragment(levelSelectionPanel_fragment); /*Méthode showFragment détaillée plus bas*/

        /*Ci-dessous, le comportement du bouton Jouer*/
        /*On récupère le bouton concerné*/
        play_button = (Button) findViewById(R.id.menu_play_button);
        /*on lui ajoute un onTouchListener*/
        play_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        /*Sur l'appui, on change l'image du bouton pour l'animer*/
                        play_button.setBackgroundResource(R.drawable.play_button_pressed);
                        /*Et on affiche le fragment de sélection de niveau */
                        showFragment(levelSelectionPanel_fragment);
                        break;
                    case MotionEvent.ACTION_UP:
                        /*Sur le relâchement de l'appui, on change l'image du bouton pour l'animer*/
                        play_button.setBackgroundResource(R.drawable.play_button_idle);
                        break;
                }
                return false;
            }
        });

        /*Ci-dessous, le comportement du bouton Quitter*/
        /*On récupère le bouton concerné*/
        quit_button = (Button) findViewById(R.id.menu_quit_button);
        /*on lui ajoute un onTouchListener*/
        quit_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        /*Sur l'appui, on change l'image du bouton pour l'animer*/
                        quit_button.setBackgroundResource(R.drawable.quit_button_pressed);
                        break;
                    case MotionEvent.ACTION_UP:
                        /*Sur le relâchement de l'appui, on change l'image du bouton pour l'animer*/
                        quit_button.setBackgroundResource(R.drawable.quit_button_idle);
                        /*Et on lance la demande de confirmation de quittage du jeu*/
                        exit_Confirmation(); /*Méthode exite_Confirmation détaillée plus bas*/
                        break;
                }
                return false;
            }
        });

        /*Ci-dessous, le comportement du bouton Quêtes*/
        /*On récupère le bouton concerné*/
        quests_button = (Button) findViewById(R.id.menu_quests_button);
        /*on lui ajoute un onTouchListener*/
        quests_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        /*Sur l'appui, on change l'image du bouton pour l'animer*/
                        quests_button.setBackgroundResource(R.drawable.quests_button_pressed);
                        /*Et on affiche le fragment de visualisation des quêtes */
                        showFragment(questPanel_fragment);
                        break;
                    case MotionEvent.ACTION_UP:
                        /*Sur le relâchement de l'appui, on change l'image du bouton pour l'animer*/
                        quests_button.setBackgroundResource(R.drawable.quests_button_idle);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    /**
     * Surcharger cette méthode permet modifier l'action du bouton RETOUR du télephone.
     */
    public void onBackPressed() {
        exit_Confirmation(); /*Méthode exite_Confirmation détaillée plus bas*/
    }

    /**
     * Crée une boite de dialogue pour confirmer/annuler l'arrêt du jeu
     */
    public void exit_Confirmation(){
        /*On crée une boite de dialogue pour confirmer/annuler l'arrêt du jeu*/
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.quitGame)
                .setMessage(R.string.quitGameConf)
                .setPositiveButton(R.string.positiveAnswer, new DialogInterface.OnClickListener()
                {
                    /*On pose sur le "positiveButton" un listener*/
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Sur le clique de ce bouton, on ajoute les couples:
                        * - (clé,valeur) dans les SharedPréférences*/
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("maxLvl_unlocked", maxLvl_unlocked);
                        editor.putInt("block_destroyed", block_numbers);
                        editor.commit();
                        /*Et on kill l'activité actuelle*/
                        Intent intent = new Intent(SuperBouleMenuActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                })
                .setNegativeButton(R.string.negativeAnswer, null)
                .show();
    }

    /**
     * Affiche le fragment en paramètre dans une view
     * @param fragment Fragment souhaité
     */
    private void showFragment(Fragment fragment) {
        /*Ce code est inspiré de la documentation officielle android*/
        /**/
        /*On crée l'objet transaction*/
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        /*On remplace le contenu de menu_content par le fragment*/
        transaction.replace(R.id.menu_content, fragment);
        /*On ajoute la transaction au BackStack, ce qui permet de pouvoir revenir en arrière avec
        * le bouton RETOUR du télephone*/
        transaction.addToBackStack(null);
        /*Et on valide la transaction*/
        transaction.commit();

    }
}
