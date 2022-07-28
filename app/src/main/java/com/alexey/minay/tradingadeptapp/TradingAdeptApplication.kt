package com.alexey.minay.tradingadeptapp

import android.app.Application
import com.alexey.minay.tradingadeptapp.di.TradingAdeptComponent

class TradingAdeptApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        TradingAdeptComponent.init()
    }

}