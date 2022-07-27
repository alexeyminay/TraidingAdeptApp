package com.alexey.minay.tradingadeptapp.di

import androidx.lifecycle.ViewModel
import com.alexey.minay.tradingadeptapp.TradingAdeptViewModel
import dagger.Binds
import dagger.Module

@Module
interface TradingAdeptViewModelBinding {

    @Binds
    fun bindTradingAdeptViewModel(viewModel: TradingAdeptViewModel): ViewModel

}