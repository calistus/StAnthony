package com.grandilo.stanthonyapp.utils

import android.content.Context
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.tasks.OnCompleteListener

object AdsUtil {
    lateinit var mInterstitialAd: InterstitialAd

    fun initializeAdmobInterstitial(context: Context) {
        MobileAds.initialize(context,
                "ca-app-pub-9249562617917886~5785800067")
        mInterstitialAd = InterstitialAd(context)
    }

 }