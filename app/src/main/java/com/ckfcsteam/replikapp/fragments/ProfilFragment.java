package com.ckfcsteam.replikapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.replikapp.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfilFragment extends Fragment {

    /* DEBUT : Déclaration de variables */
    // firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ImageView avatarProfil;
    private TextView nameProfil, mailProfil, phoneProfil;
    private Button logoutBtn;
    /* FIN : Déclaration de variables */


    public ProfilFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        // Initialisation de Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        //récupération des ID XML
        avatarProfil = view.findViewById(R.id.avatarProfil);
        nameProfil = view.findViewById(R.id.nameProfil);
        mailProfil = view.findViewById(R.id.mailProfil);
        phoneProfil = view.findViewById(R.id.phoneProfil);
        logoutBtn = view.findViewById(R.id.logoutBtn);


        /* Nous récupérons les informations de l'utilisateur connecté grâce à l'uil ou le mail (Ici ce sera l'uid).
        * Grâce à orderByChild de Query, nous regardons si la clé uid correspond à l'uid de l'utilisateur connecté
        * L'accès aux données sera donc possible et la récupération aussi grâce à getChildren */

        Query query = databaseReference.orderByChild("uid").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // On vérifie les données obtenues
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    // On récupère les données
                    String name = ""+ds.child("name").getValue();
                    String email = ""+ds.child("email").getValue();
                    String phone = ""+ds.child("phone").getValue();
                    String image = ""+ds.child("image").getValue();


                    //On affiche dans le profil
                    String emailDisplay = getString(R.string.mailInfo) +"     "+ email;
                    String phoneDisplay = getString(R.string.phoneInfo) +"     "+ phone;

                    nameProfil.setText(name);
                    mailProfil.setText(emailDisplay);
                    phoneProfil.setText(phoneDisplay);
                    try {
                        //Si l'image est récupéré alors on la met en place sur l'imageview
                        Picasso.get().load(R.drawable.ic_add_image).into(avatarProfil);
                    }catch (Exception e){
                        //Si il y a une exception quelconque lors de la récupération de l'image.
                        Picasso.get().load(R.drawable.ic_add_image).into(avatarProfil);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });


        return view;
    }



}
