package com.alexey.minay.tradingadeptapp.di

import androidx.lifecycle.ViewModel
import com.alexey.minay.core_dagger2.ViewModelKey
import com.alexey.minay.tradingadeptapp.TradingAdeptViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TradingAdeptViewModelBinding {

    @Binds
    @[IntoMap ViewModelKey(TradingAdeptViewModel::class)]
    fun bindTradingAdeptViewModel(viewModel: TradingAdeptViewModel): ViewModel

}