package com.ckfcsteam.replikapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

public class StoreFragment extends Fragment implements RewardedVideoAdListener{

    /* DEBUT : Initialisation des variables */
    private MaterialCardView freeCoin;
    private RewardedVideoAd mRewardedVideoAd;
    private View rootView;
    private MainActivity mainActivity;
    private MaterialCardView vipCV;
    /* FIN : Initialisation des variables */

    public StoreFragment(){//Constructeur vide necéssaire
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_store, container, false);
        MobileAds.initialize(getContext(), "ca-app-pub-6113200648741812/4193990483");
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        mainActivity = (MainActivity) getActivity();

        loadRewardedVideoAd();

        freeCoin = rootView.findViewById(R.id.freeCoin);
        freeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mRewardedVideoAd.isLoaded()){
                    mRewardedVideoAd.show();
                }
            }
        });

        vipCV = rootView.findViewById(R.id.buy_item);

        //ecouteur du bouton vip
        vipCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog qui s'affiche pour la confirmation de l'achat vip
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.titleDialogvip)).setMessage(R.string.messageDialogvip).setPositiveButton(R.string.logOutADYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Si le vip est egale à faux
                        if(mainActivity.vip.compareTo("false")== 0){
                            // si le nombre de pièce est supérieure à 100
                            if(mainActivity.coin_amount >= 100){
                                // on réduit le nombre de pièces du joueur et on upgrade le vip
                                mainActivity.decCoinAmount(100);
                                mainActivity.updateVIPBD("true");
                                Toast.makeText(mainActivity, getString(R.string.success), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(mainActivity, getString(R.string.error), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(mainActivity, getString(R.string.testHaveVIP) , Toast.LENGTH_SHORT).show();
                        }

                    }
                }).setNegativeButton(R.string.logOutADNo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

            }
        });



        return rootView;
    }

    /* Methodes vide necessaires à l'ajout de pubs google*/
    @Override
    public void onRewardedVideoAdLoaded() {}

    @Override
    public void onRewardedVideoAdOpened() {}

    @Override
    public void onRewardedVideoStarted() {}

    // Si la pub est lancée ET terminée, l'utilisateur reçoit une récompense
    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }


    // Récompense de du visionnage de pub
    @Override
    public void onRewarded(RewardItem rewardItem) {
        mainActivity.incCoinAmount(150);
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoCompleted() {
        loadRewardedVideoAd();
    }

    /**
     * Lancement de la pub s'il appuie sur le bouton associé
     */
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }

}
