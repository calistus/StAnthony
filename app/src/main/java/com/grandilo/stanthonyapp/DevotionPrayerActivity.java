package com.grandilo.stanthonyapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.grandilo.stanthonyapp.db.DBAdapter;

public class DevotionPrayerActivity extends AppCompatActivity {


    public static final String EXTRA_NAME = "cheese_name";
    DBAdapter db;
    TextView textTitle ;
    TextView textBody;
    String title;
    String body;
    int prayerCounter = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devotion_prayer);
        db = new DBAdapter(this);
        FloatingActionButton nextButton = (FloatingActionButton) findViewById(R.id.nextButton);
        FloatingActionButton previousButton = (FloatingActionButton) findViewById(R.id.previousButton);
        textTitle = (TextView) findViewById(R.id.text_title);
        textBody = (TextView) findViewById(R.id.text_body);
        textTitle.setText(getPrayerTitle());
        textBody.setText(getPrayerBody());
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prayerCounter++;
                textTitle.setText(getPrayerTitle());
                textBody.setText(getPrayerBody());
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prayerCounter--;
                textTitle.setText(getPrayerTitle());
                textBody.setText(getPrayerBody());
            }
        });


        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    public String getPrayerTitle(){
           db.open();
        if((prayerCounter == 30)||(prayerCounter<1))
        {
            prayerCounter = 1;
        }
        Cursor c = db.getDevotionPrayer(prayerCounter);
        title=c.getString(1);
        db.close();
        return title;
    }
    public String getPrayerBody(){
        db.open();
        if((prayerCounter == 30)||(prayerCounter<1))
        {
            prayerCounter = 1;
        }
        Cursor c = db.getDevotionPrayer(prayerCounter);
        body=c.getString(2);
        db.close();
        return body;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, AboutStAnthony.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

