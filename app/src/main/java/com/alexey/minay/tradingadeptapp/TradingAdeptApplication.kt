package com.alexey.minay.tradingadeptapp

import android.app.Application
import com.alexey.minay.tradingadeptapp.di.TradingAdeptComponent
import com.facebook.stetho.Stetho

class TradingAdeptApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        TradingAdeptComponent.init()
    }

}