package com.grandilo.stanthonyapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.grandilo.stanthonyapp.db.DBAdapter;
import com.grandilo.stanthonyapp.utils.AdsUtil;

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
        Button nextButton = (Button) findViewById(R.id.nextButton);
        Button previousButton = (Button) findViewById(R.id.previousButton);
        Button reportButton = (Button) findViewById(R.id.report);
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
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                String url = "https://api.whatsapp.com/send?phone=+2348111875720";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(DevotionPrayerActivity.this);
                builder.setTitle("Make Correction");
                builder.setMessage("You will be redirected to WhatsApp to report this directly to the developer").setPositiveButton("Continue", dialogClickListener)
                        .setNegativeButton("Cancel", dialogClickListener).show();

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

