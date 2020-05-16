package com.ckfcsteam.replikapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.replikapp.activities.SuperBouleActivity;
import com.ckfcsteam.replikapp.models.CustomAdaptater;
import com.ckfcsteam.replikapp.models.DataModel;

import java.util.ArrayList;

public class LevelSelection extends Fragment {

    /*Déclaration des variables*/
    ArrayList<DataModel> dataModels;
    private static CustomAdaptater adapter;
    private ListView lvl_list;
    private int maxLvl_unlocked ;

    /*Constructeur*/
    public LevelSelection(int maxLvl_unlocked){
        this.maxLvl_unlocked = maxLvl_unlocked;
    }

    @Override
    /*Associe le fragment à la création de la View*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_levelselection, container, false);
    }

    @Override
    /*Construit le menu de sélection des niveau à la création de l'activité*/
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*On récupère dans la vue, l'emplacement où afficher la présentation sous forme de liste*/
        lvl_list = (ListView) getView().findViewById(R.id.menu_levelList);

        /*On crée une liste d'objet dataModels, objet permettant de décrire un niveau*/
        dataModels = new ArrayList<DataModel>();
        /*On ajoute à la liste créée, les descriptions de niveau voulues*/
        dataModels.add(new DataModel(R.drawable.lock_icon,getResources().getString(R.string.lvl1),getResources().getString(R.string.easy)));
        dataModels.add(new DataModel(R.drawable.lock_icon,getResources().getString(R.string.lvl2),getResources().getString(R.string.easy)));
        dataModels.add(new DataModel(R.drawable.lock_icon,getResources().getString(R.string.lvl3),getResources().getString(R.string.normal)));
        dataModels.add(new DataModel(R.drawable.lock_icon,getResources().getString(R.string.lvl4),getResources().getString(R.string.hard)));

        /*Pour chaque description de niveau*/
        for (DataModel dm : dataModels){
            if(dataModels.indexOf(dm) < maxLvl_unlocked){
                /*On lui attribue l'image du niveau "débloqué" si l'indice du niveau est inférieur au nombre de niveau max débloqué*/
                dm.setImg_ressource(R.drawable.menu_level_indicator);
            }else dm.setImg_ressource(R.drawable.lock_icon); /*On lui attribue l'image du niveau "bloqué" sinon*/
        }

        /*On récupère notre Adaptateur Custom*/
        adapter = new CustomAdaptater(dataModels,getContext());
        /*On ajoute l'adaptateur à l'objet concerné*/
        lvl_list.setAdapter(adapter);

        /*Et on pose un listener sur la présentation sous forme de liste*/
        lvl_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position <= (maxLvl_unlocked-1)){
                    /*Si le niveau est débloqué, on récupère son numéro de position*/
                    String value = Integer.toString(position);
                    /*On prépare l'activité du jeu Superboule*/
                    Intent i = new Intent(getContext(), SuperBouleActivity.class);
                    /*En lui passant les valeurs : lvl_id souhaité et nombre de niveau maximum débloqué */
                    i.putExtra("lvl_id",value);
                    i.putExtra("maxLvl_unlocked", maxLvl_unlocked);
                    /*On lance l'activité*/
                    startActivity(i);
                    /*Et on kill celle actuelle*/
                    ((Activity) getActivity()).finish();
                }else{
                    /*Si le niveau est bloqué, on affiche un petit Toast informatif*/
                    Toast.makeText(getContext(),getResources().getString(R.string.unlockLvl), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
