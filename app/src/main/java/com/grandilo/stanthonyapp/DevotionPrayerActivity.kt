package com.grandilo.stanthonyapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest

import com.grandilo.stanthonyapp.db.DBAdapter
import com.grandilo.stanthonyapp.utils.AdsUtil
import com.grandilo.stanthonyapp.utils.LogUtils

class DevotionPrayerActivity : AppCompatActivity() {
    lateinit var db: DBAdapter
    lateinit var textTitle: TextView
    lateinit var textBody: TextView
    lateinit var title: String
    lateinit var body: String
    var prayerCounter = 1
    val prayerTitle: String
        get() {
            db.open()
            if (prayerCounter == 30 || prayerCounter < 1) {
                prayerCounter = 1
            }
            val c = db.getDevotionPrayer(prayerCounter.toLong())
            title = c!!.getString(1)
            db.close()
            return title
        }
    val prayerBody: String
        get() {
            db.open()
            if (prayerCounter == 30 || prayerCounter < 1) {
                prayerCounter = 1
            }
            val c = db.getDevotionPrayer(prayerCounter.toLong())
            body = c!!.getString(2)
            db.close()
            return body
        }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devotion_prayer)
        db = DBAdapter(this)
        val nextButton = findViewById<View>(R.id.nextButton) as Button
        val previousButton = findViewById<View>(R.id.previousButton) as Button
        val reportButton = findViewById<View>(R.id.report) as Button
        textTitle = findViewById<View>(R.id.text_title) as TextView
        textBody = findViewById<View>(R.id.text_body) as TextView
        textTitle.text = prayerTitle
        textBody.text = prayerBody
        AdsUtil.initializeAdmobInterstitial(this)
        AdsUtil.mInterstitialAd.adUnitId = getString(R.string.admob_interstitial_devotion)
        AdsUtil.mInterstitialAd.loadAd(AdRequest.Builder().build())
        setAdListener()
        var adCounter = 1
        nextButton.setOnClickListener {
            prayerCounter++
            //adCounter++

            if (prayerCounter % 5 == 0){

                showInterstitialAds()
            }
            textTitle.text = prayerTitle
            textBody.text = prayerBody
        }

        previousButton.setOnClickListener {
            prayerCounter--
            textTitle.text = prayerTitle
            textBody.text = prayerBody
        }
        reportButton.setOnClickListener {
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        val url = "https://api.whatsapp.com/send?phone=+2348111875720"
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

            val builder = AlertDialog.Builder(this@DevotionPrayerActivity)
            builder.setTitle("Make Correction")
            builder.setMessage("You will be redirected to WhatsApp to report this directly to the developer").setPositiveButton("Continue", dialogClickListener)
                    .setNegativeButton("Cancel", dialogClickListener).show()
        }

        val intent = intent
        val cheeseName = intent.getStringExtra(EXTRA_NAME)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val collapsingToolbar = findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbar.title = cheeseName

    }

    private fun setAdListener() {
        AdsUtil.mInterstitialAd.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdLeftApplication() {
                AdsUtil.mInterstitialAd.loadAd(AdRequest.Builder().build())
            }

            override fun onAdClosed() {
                AdsUtil.mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }    }


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

