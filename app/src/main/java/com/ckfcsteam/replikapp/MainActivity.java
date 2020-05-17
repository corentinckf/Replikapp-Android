package com.ckfcsteam.replikapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ckfcsteam.replikapp.activities.LoginActivity;
import com.ckfcsteam.replikapp.fragments.BibliFragment;
import com.ckfcsteam.replikapp.fragments.ProfilFragment;
import com.ckfcsteam.replikapp.fragments.StoreFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class MainActivity extends AppCompatActivity{

    /* Déclaration de variables */
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;

    public int coin_amount = 0;
    private TextView coind_amount_ind, pseudoText;
    private ImageView logOutIv;

    //NM
    public Switch switchNM;
    private FrameLayout mainLayout;
    private MaterialToolbar topMaterialTB;

    boolean testNM;
    public String vip;


    private LinearLayout bottomBar_item1,bottomBar_item2,bottomBar_item3;
    private TextView bottomBar_text1, bottomBar_text2, bottomBar_text3;

    private Boolean bottomBar_item1_selectionned = true;
    private Boolean bottomBar_item2_selectionned = false;
    private Boolean bottomBar_item3_selectionned = false;

    public MainActivity(){

    }
    @Override
    public void onBackPressed() {
        //Désactiver le boutton retour d'android
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase
        auth = FirebaseAuth.getInstance(); // Initialisation de L'Auth Firebase
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        // Référence du dossiers "users" de la base de données firebase
        databaseReference = firebaseDatabase.getReference("Users");

        //NM ID XML

        switchNM = findViewById(R.id.switchNM);
        mainLayout = findViewById(R.id.page);
        topMaterialTB = findViewById(R.id.hub_topBar);
        testNM = false;




        /* Liaison des variables au xml */
        bottomBar_item1 = findViewById(R.id.bottomBar_item1);
        bottomBar_item2 = findViewById(R.id.bottomBar_item2);
        bottomBar_item3 = findViewById(R.id.bottomBar_item3);

        bottomBar_text1 = findViewById(R.id.bottomBar_text1);
        bottomBar_text2 = findViewById(R.id.bottomBar_text2);
        bottomBar_text3 = findViewById(R.id.bottomBar_text3);

        updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);

        coind_amount_ind = findViewById(R.id.coin_text);
        pseudoText = findViewById(R.id.pseudo_text);
        logOutIv = findViewById(R.id.logOutIv);
        switchNM = findViewById(R.id.switchNM);

        switchNM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    nightModeOn();
                    updateBottomBarItemsUINM(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
                    testNM = true;
                }else{
                    nightModeOff();
                    updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
                    testNM = false;
                }
            }
        });


        // Ecouteur de l'imageView permettant la deconnexion en affichant un alertDialog de confirmation
        logOutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.logOutADTitle).setMessage(R.string.logOutADMessage).setPositiveButton(R.string.logOutADYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                    }
                }).setNegativeButton(R.string.logOutADNo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

            }
        });

        /* Mise en place des différents fragments dans chaque cas */

        bottomBar_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar_item1_selectionned = true;
                bottomBar_item2_selectionned = false;
                bottomBar_item3_selectionned = false;

                if(testNM){
                    updateBottomBarItemsUINM(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
                }else{
                    updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
                }


            }
        });

        bottomBar_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar_item1_selectionned = false;
                bottomBar_item2_selectionned = true;
                bottomBar_item3_selectionned = false;
                if(testNM){
                    updateBottomBarItemsUINM(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
                }else{
                    updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
                }
            }
        });

        bottomBar_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar_item1_selectionned = false;
                bottomBar_item2_selectionned = false;
                bottomBar_item3_selectionned = true;
                if(testNM){
                    updateBottomBarItemsUINM(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
                }else{
                    updateBottomBarItemsUI(bottomBar_item1_selectionned,bottomBar_item2_selectionned,bottomBar_item3_selectionned);
                }
            }
        });



    }

    /**
     * Méthode appelée lorsque l'utilisateur est sur l'activit/ lorsque l'activité est visible par l'utilisateur
     */
    @Override
    public void onStart() {

        super.onStart();

        // On vérifie l'état de connexion de l'utilisateur
       if(auth.getCurrentUser() ==  null){ // Si pas d'utilisateur lié à l'Auth
            Intent login_activity_intent = new Intent(this, LoginActivity.class); // Création d'une instance de l'activité login
            startActivity(login_activity_intent); //On lance l'activité LoginActivity pour auth l'utilisateur
            finish();
        }else{
           // Si c'est le cas, on effectue les différentes opérations
           getVIPValue();
           displayCoinAmount();
           displayPseudo();
       } // Sinon on reste sur MainActivity


    }

    // Mise en place graphique des fragments
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

    /**
     * Méthode permettant de mettre en place le night mode de la bottom bar
     * @param item1_selected 'bouton' Fragment 1
     * @param item2_selected 'bouton' Fragment 2
     * @param item3_selected 'bouton' Fragment 3
     */
    public void updateBottomBarItemsUINM(Boolean item1_selected, Boolean item2_selected, Boolean item3_selected){
        if(item1_selected){
            bottomBar_item1.setBackgroundColor(Color.LTGRAY);
            bottomBar_text1.setTextColor(Color.WHITE);

            showFragment(new BibliFragment());

        }else{
            bottomBar_item1.setBackgroundColor(Color.rgb(108,108,108));
            bottomBar_text1.setTextColor(Color.WHITE);
        }

        if(item2_selected){
            bottomBar_item2.setBackgroundColor(Color.LTGRAY);
            bottomBar_text2.setTextColor(Color.WHITE);

            showFragment(new ProfilFragment());

        }else{
            bottomBar_item2.setBackgroundColor(Color.rgb(108,108,108));
            bottomBar_text2.setTextColor(Color.WHITE);
        }

        if(item3_selected){
            bottomBar_item3.setBackgroundColor(Color.LTGRAY);
            bottomBar_text3.setTextColor(Color.WHITE);
            showFragment(new StoreFragment());
        }else{
            bottomBar_item3.setBackgroundColor(Color.rgb(108,108,108));
            bottomBar_text3.setTextColor(Color.WHITE);
        }
    }

    /**
     * méthode d'affichage des fragments
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.page, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    /**
     * Méthode d'incrémentation des pièces du joueur
     * @param add valeur à ajouter au total des jetons de l'utilisateur
     */
    public void incCoinAmount(int add){

        coin_amount += add;
        String coin_res = Integer.toString(coin_amount);
        updateCoinBD(coin_res);
        displayCoinAmount();

    }

    /**
     * méthode permettant de faire la décrémentation des pièces joueur
     * @param add valeur à décrémenter
     */
    public void decCoinAmount(int add){

        coin_amount -= add;
        String coin_res = Integer.toString(coin_amount);
        updateCoinBD(coin_res);
        displayCoinAmount();

    }

    /**
     * Méthode de mise à jour des jetons de l'utilisateur dans la base de données.
     * @param value valeur à mettre dans a base de données firebase
     */
    private void updateCoinBD(String value) {

        HashMap<String, Object> result = new HashMap<>();
        result.put("coin", value);
        databaseReference.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Méthode permettant de modifier la valeur du statut de VIP dans la base de données
     * @param value valeur à insérer dans la base de données
     */
    public void updateVIPBD(String value) {

        HashMap<String, Object> result = new HashMap<>();
        result.put("vip", value);
        databaseReference.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Méthode d'affichage des jetons utilisateur
     */
    private void displayCoinAmount(){
        Query query = databaseReference.orderByChild("uid").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // On vérifie les données obtenues
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    // On récupère les données
                    String coin = ""+ds.child("coin").getValue();
                    coin_amount = Integer.parseInt(coin);
                    coind_amount_ind.setText( "x" + coin);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Methode permettant d'obtenir la valeur du statut VIP depuis la base de données
     */
    public void getVIPValue(){

        Query query = databaseReference.orderByChild("uid").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // On vérifie les données obtenues
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    // On récupère les données
                   vip = ""+ds.child("vip").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Méthode d'affichage du pseudo utilisateur
     */
    private void displayPseudo(){
                Query query = databaseReference.orderByChild("uid").equalTo(user.getUid());
                query.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // On vérifie les données obtenues
                        for(DataSnapshot ds : dataSnapshot.getChildren()){

                            // On récupère les données
                            String name = ""+ds.child("name").getValue();
                            pseudoText.setText(name);

                        }
                    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * méthode permettant la deconnexion de l'utilisateur
     */
    public void signOut(){
        auth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Méhtode lorsque l'on souhaite activer le nightmode
     */
    public void nightModeOn(){



        mainLayout.setBackgroundColor(Color.rgb(48,48,48));
        coind_amount_ind.setTextColor(Color.rgb(255,255,255));
        pseudoText.setTextColor(Color.rgb(255,255,255));
        topMaterialTB.setBackgroundColor(Color.rgb(108,108,108));

    }

    /**
     * Méhtode lorsque l'on souhaite desactiver le nightmode
     */
    public void nightModeOff(){
        mainLayout.setBackgroundColor(Color.rgb(255,255,255));
        coind_amount_ind.setTextColor(Color.rgb(0,0,0));
        pseudoText.setTextColor(Color.rgb(0,0,0));
        topMaterialTB.setBackgroundColor(Color.rgb(255,255,255));

    }







}
