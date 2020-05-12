package com.ckfcsteam.replikapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class StoreFragment extends Fragment implements RewardedVideoAdListener{
    private MaterialCardView freeCoin;
    private RewardedVideoAd mRewardedVideoAd;
    private View rootView;
    private MainActivity mainActivity;
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
                Toast.makeText(mainActivity.getBaseContext(), R.string.error, Toast.LENGTH_LONG).show();
                if(mRewardedVideoAd.isLoaded()){
                    mRewardedVideoAd.show();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onRewardedVideoAdLoaded() {}

    @Override
    public void onRewardedVideoAdOpened() {}

    @Override
    public void onRewardedVideoStarted() {}

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

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

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }

}
