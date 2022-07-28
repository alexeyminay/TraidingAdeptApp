package com.alexey.minay.tradingadeptapp

import androidx.lifecycle.ViewModel
import com.alexey.minay.feature_navigation_impl.ScreenNavigator
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class TradingAdeptViewModel @Inject constructor(
    private val navigator: ScreenNavigator
) : ViewModel() {

    val screenFlow get() = navigator.mainFragmentFlow

}