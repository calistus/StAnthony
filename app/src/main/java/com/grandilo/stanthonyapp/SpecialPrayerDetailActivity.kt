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

package com.grandilo.stanthonyapp

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.ads.AdRequest
import com.grandilo.stanthonyapp.db.DBAdapter
import com.grandilo.stanthonyapp.utils.AdsUtil
import com.grandilo.stanthonyapp.utils.LogUtils

class SpecialPrayerDetailActivity : AppCompatActivity() {
    lateinit var db: DBAdapter
    lateinit var textTitle: TextView
    lateinit var textBody: TextView
    lateinit var title: String
    lateinit var body: String
    internal var prayerCounter = 1

    val prayerTitle: String
        get() {
            db.open()
            val c = db.getSpecialPrayer((SpecialPrayersListFragment.itemPosition + 1).toLong())
            title = c!!.getString(1)
            db.close()
            return title
        }
    val prayerBody: String
        get() {
            db.open()
            val c = db.getSpecialPrayer((SpecialPrayersListFragment.itemPosition + 1).toLong())
            body = c!!.getString(2)
            db.close()
            return body
        }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_special_prayer)
        db = DBAdapter(this)
        textTitle = findViewById<TextView>(R.id.text_title)
        textBody = findViewById<TextView>(R.id.text_body)
        textTitle.text = prayerTitle
        textBody.text = prayerBody

        val intent = intent
        val cheeseName = intent.getStringExtra(EXTRA_NAME)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val collapsingToolbar = findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbar.title = cheeseName

        loadBackdrop()

        AdsUtil.initializeAdmobInterstitial(this)
        AdsUtil.mInterstitialAd.adUnitId = getString(R.string.admob_interstitial_test)
        AdsUtil.mInterstitialAd.loadAd(AdRequest.Builder().build())


    }

    private fun loadBackdrop() {
        val imageView = findViewById<View>(R.id.backdrop) as ImageView
        Glide.with(this).load(Prayers.getRandomCheeseDrawable()).apply(RequestOptions().placeholder(R.mipmap.ic_launcher).fitCenter()).into(imageView)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sample_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, AboutStAnthony::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        val EXTRA_NAME = "cheese_name"
    }

    private fun showInterstitialAds() {
        if (AdsUtil.mInterstitialAd.isLoaded) {
            AdsUtil.mInterstitialAd.show()
            LogUtils.log("PRAYER", "Ad is up and running")

        } else {
            LogUtils.log("PRAYER", "The interstitial wasn't loaded yet.")
        }

    }

    override fun onBackPressed() {
        showInterstitialAds()
        super.onBackPressed()

    }
}
