package com.grandilo.stanthonyapp;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.grandilo.stanthonyapp.db.DBAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    DBAdapter db;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-9249562617917886~5785800067");


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        String destDir = "/data/data/" + getPackageName() +
                "/databases/";
        String destPath = destDir + "prayers";
        File f = new File(destPath);
        if (!f.exists()) {
            //---make sure directory exists---
            File directory = new File(destDir);
            directory.mkdirs();
            //---copy the db from the assets folder into
            // the databases folder---
            try {
                CopyDB(getBaseContext().getAssets().open("prayers"),
                        new FileOutputStream(destPath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        db = new DBAdapter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DevotionPrayerActivity.class));
            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void CopyDB(InputStream inputStream, OutputStream outputStream)
            throws IOException {
//---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, AboutStAnthony.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new DevotionFragment(), "Devotion");
        adapter.addFragment(new SpecialPrayersListFragment(), "Special Prayers");
        viewPager.setAdapter(adapter);
    }
    private void shareWithFriends() {
        final String appLink = "https://play.google.com/store/apps/details?id=com.grandilo.stanthonyapp";
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Guild of St. Anthony App");
        shareIntent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.share_message), appLink));
        shareIntent.setType("text/link");
        startActivity(Intent.createChooser(shareIntent, "Share using:"));
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_invite:
                                shareWithFriends();
                                return true;
                            case R.id.nav_update:
                                try{startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));}catch(Exception e){e.printStackTrace();}
                                return true;
                            case R.id.nav_reminder:
                                // A date-time specified in milliseconds since the epoch.
                                long startMillis = 0;
                                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                                builder.appendPath("time");
                                ContentUris.appendId(builder, startMillis);
                                Intent intent = new Intent(Intent.ACTION_VIEW)
                                        .setData(builder.build());
                                startActivity(intent);
                                return true;
                            case R.id.nav_feedback:
                                startActivity(new Intent(MainActivity.this, FeedBack.class));
                                return true;
                            case R.id.nav_about:
                                startActivity(new Intent(MainActivity.this, About.class));
                                return true;


                        }

                        return true;
                    }
                });
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
