package com.grandilo.stanthony;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class DevotionFragment extends Fragment {
    TextView qouteTextView1;
    TextView qouteTextView2;
    TextView qouteTextView3;
    TextView qouteTextView4;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = (View) inflater.inflate(
                R.layout.fragment_devotion, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DevotionPrayerActivity.class));
            }
        });

        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        qouteTextView1 = (TextView)rootView.findViewById(R.id.qoute_1);
        qouteTextView2 = (TextView)rootView.findViewById(R.id.qoute_2);
        qouteTextView3 = (TextView)rootView.findViewById(R.id.qoute_3);
        qouteTextView4 = (TextView)rootView.findViewById(R.id.qoute_4);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        return rootView;
    }

}
