package com.ckfcsteam.replikapp.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.replikapp.activities.SuperBouleMenuActivity;
import com.ckfcsteam.spaceinvaders.activities.Menu2Activity;
import com.google.android.material.card.MaterialCardView;

public class BibliFragment extends Fragment {

    /* DEBUT : Initialisation des variables */
    public MaterialCardView game1,game2;
    public MainActivity mainActivity;

    public BibliFragment(){


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {





        View view = inflater.inflate(R.layout.fragment_bibli, container, false);
        game1 = view.findViewById(R.id.infinityInvadersMCV);
        game2 = view.findViewById(R.id.superBouleMCV);


        // Redirection de la materialCardview dans le jeu 1
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Menu2Activity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Redirection de la materialCardview dans le jeu 2
        game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SuperBouleMenuActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });



        return view;
    }
}
