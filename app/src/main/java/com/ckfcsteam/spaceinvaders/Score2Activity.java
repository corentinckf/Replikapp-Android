package com.ckfcsteam.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.spaceinvaders.gamelib.HighScore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Score2Activity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<HighScore> list;
    //private TextView modeView;
    //private TextView highView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score2);
        // Récuperation des meilleurs scores
        list = loadHighScore();
        // Récupération et traitement de la listView
        listView = findViewById(R.id.myListV);
        listView.setAdapter(new myAdapter());

    }

    /**
     * Charge les différents meilleurs score sauvegardé
     *
     * @return liste des meilleurs score
     */
    private ArrayList<HighScore> loadHighScore(){
        ArrayList<HighScore> res = new ArrayList<>();
        for(int mode=1; mode<=3;mode++){
            // Charge du fichier de sauvegarde SharedPreferences
            String highScoreKey = "high"+mode;
            SharedPreferences sharedPreferences = getSharedPreferences("infinity", MODE_PRIVATE);
            // Récupération du meilleur score
            int highScore = sharedPreferences.getInt(highScoreKey, -1);
            // Sauvegarde du meilleurs score, si il a changé
            String modeStr;
            switch (mode){
                case 1:
                    modeStr = getResources().getString(R.string.easy);
                    break;
                case 2:
                    modeStr = getResources().getString(R.string.normal);
                    break;
                case 3:
                    modeStr = getResources().getString(R.string.easy);
                    break;
                default:
                    modeStr = getResources().getString(R.string.nothing);
            }
            res.add(new HighScore(modeStr, highScore));
        }
        return (res);

    }

    {

    }
    /**
     * Lors du clic sur le bouton home renverra l'utilisateur à l'acceuil
     *
     * @param view le bouton image
     */
    public void home(View view){
        Intent intent = new Intent(Score2Activity.this, Menu2Activity.class);
        startActivity(intent);
        finish();
    }

    private class myAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        public myAdapter(){
            inflater = LayoutInflater.from(Score2Activity.this);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public HighScore getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // On récupere le layout à appliquer à chaque éléments
            View view =inflater.inflate(R.layout.adapter_score2, null);

            // Meilleurs score courant
            HighScore currentHS = getItem(position);

            // Récuperation des vues à modifier
            TextView modeView = view.findViewById(R.id.modeScore);
            TextView hgView = view.findViewById(R.id.hghScore);

            // Maj des vues
            modeView.setText(currentHS.getMode());
            hgView.setText(currentHS.getHighScore()+"");


            return view;
        }
    }

}
