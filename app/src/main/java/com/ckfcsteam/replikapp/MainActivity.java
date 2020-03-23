package com.ckfcsteam.replikapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ckfcsteam.replikapp.activities.LoginActivity;
import com.ckfcsteam.replikapp.fragments.BibliFragment;
import com.ckfcsteam.replikapp.fragments.ProfilFragment;
import com.ckfcsteam.replikapp.fragments.StoreFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{

    /* Déclaration de variables */
    private FirebaseAuth auth;

    private int coin_amount;
    private TextView coind_amount_ind;

    private LinearLayout bottomBar_item1,bottomBar_item2,bottomBar_item3;
    private TextView bottomBar_text1, bottomBar_text2, bottomBar_text3;

    private Boolean bottomBar_item1_selectionned = true;
    private Boolean bottomBar_item2_selectionned = false;
    private Boolean bottomBar_item3_selectionned = false;

    @Override
    public void onBackPressed() {
        //Désactiver le boutton retour d'android
    }

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
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance(); // Initialisation de L'Auth Firebase

        /* Liaison des variables au xml */
        bottomBar_item1 = findViewById(R.id.bottomBar_item1);
        bottomBar_item2 = findViewById(R.id.bottomBar_item2);
        bottomBar_item3 = findViewById(R.id.bottomBar_item3);

        bottomBar_text1 = findViewById(R.id.bottomBar_text1);
        bottomBar_text2 = findViewById(R.id.bottomBar_text2);
        bottomBar_text3 = findViewById(R.id.bottomBar_text3);

        updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);

        coind_amount_ind = findViewById(R.id.coin_text);

        bottomBar_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar_item1_selectionned = true;
                bottomBar_item2_selectionned = false;
                bottomBar_item3_selectionned = false;
                updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
            }
        });

        bottomBar_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar_item1_selectionned = false;
                bottomBar_item2_selectionned = true;
                bottomBar_item3_selectionned = false;
                updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
            }
        });

        bottomBar_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar_item1_selectionned = false;
                bottomBar_item2_selectionned = false;
                bottomBar_item3_selectionned = true;
                updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
            }
        });



    }

    @Override
    public void onStart() {

        super.onStart();

        if(auth.getCurrentUser() !=  null){ // Si pas d'utilisateur lié à l'Auth
            Intent login_activity_intent = new Intent(this, LoginActivity.class); // Création d'une instance de l'activité login
            startActivity(login_activity_intent); //On lance l'activité LoginActivity pour auth l'utilisateur
            finish();
        }else{} // Sinon on reste sur MainActivity
    }

    public void updateBottomBarItemsUI(Boolean item1_selected, Boolean item2_selected, Boolean item3_selected){
        if(item1_selected){
            bottomBar_item1.setBackgroundColor(Color.LTGRAY);
            bottomBar_text1.setTextColor(Color.BLACK);

            showFragment(new BibliFragment());

        }else{
            bottomBar_item1.setBackgroundColor(Color.WHITE);
            bottomBar_text1.setTextColor(Color.GRAY);
        }

        if(item2_selected){
            bottomBar_item2.setBackgroundColor(Color.LTGRAY);
            bottomBar_text2.setTextColor(Color.BLACK);

            showFragment(new ProfilFragment());

        }else{
            bottomBar_item2.setBackgroundColor(Color.WHITE);
            bottomBar_text2.setTextColor(Color.GRAY);
        }

        if(item3_selected){
            bottomBar_item3.setBackgroundColor(Color.LTGRAY);
            bottomBar_text3.setTextColor(Color.BLACK);
            showFragment(new StoreFragment());
        }else{
            bottomBar_item3.setBackgroundColor(Color.WHITE);
            bottomBar_text3.setTextColor(Color.GRAY);
        }
    }


    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.page, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void test(){

    }

    public void incCoinAmount(int add){
        coin_amount += add;
        updateCoinAmountInd();
    }

    public void updateCoinAmountInd(){
        coind_amount_ind.setText(coin_amount + "x");
    }

}
