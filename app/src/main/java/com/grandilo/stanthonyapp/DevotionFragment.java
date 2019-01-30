package com.grandilo.stanthonyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class DevotionFragment extends Fragment {
    TextView qouteTextView1;
    TextView qouteTextView2;
    TextView qouteTextView3;
    TextView qouteTextView4;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = (View) inflater.inflate(
                R.layout.fragment_devotion, container, false);

        /*Button fab = (Button) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DevotionPrayerActivity.class));
            }
        });*/

        qouteTextView1 = (TextView)rootView.findViewById(R.id.qoute_1);
        qouteTextView2 = (TextView)rootView.findViewById(R.id.qoute_2);


        return rootView;
    }

}
