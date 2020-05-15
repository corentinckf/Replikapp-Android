package com.ckfcsteam.replikapp.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ckfcsteam.replikapp.R;
import com.google.android.material.card.MaterialCardView;

public class BibliFragment extends Fragment {

    /* DEBUT : Initialisation des variables */

    public BibliFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {





        return inflater.inflate(R.layout.fragment_bibli, container, false);
    }
}
