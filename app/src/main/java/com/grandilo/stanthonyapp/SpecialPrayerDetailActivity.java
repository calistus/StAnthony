/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grandilo.stanthonyapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.grandilo.stanthonyapp.db.DBAdapter;

public class SpecialPrayerDetailActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_special_prayer);
        db = new DBAdapter(this);
        textTitle = (TextView) findViewById(R.id.text_title);
        textBody = (TextView) findViewById(R.id.text_body);
        textTitle.setText(getPrayerTitle());
        textBody.setText(getPrayerBody());

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        loadBackdrop();

         }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(Prayers.getRandomCheeseDrawable()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).fitCenter()).into(imageView);
    }

    public String getPrayerTitle(){
        db.open();
        Cursor c = db.getSpecialPrayer(SpecialPrayersListFragment.itemPosition+1);
        title=c.getString(1);
        db.close();
        return title;
    }
    public String getPrayerBody(){
        db.open();
        Cursor c = db.getSpecialPrayer(SpecialPrayersListFragment.itemPosition+1);
        body=c.getString(2);
        db.close();
        return body;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, AboutStAnthony.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
