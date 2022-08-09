package com.alexey.minay.tradingadeptapp

import androidx.lifecycle.ViewModel
import com.alexey.minay.core_navigation.Action
import com.alexey.minay.feature_navigation_impl.ScreenNavigator
import javax.inject.Inject

class TradingAdeptViewModel @Inject constructor(
    private val navigator: ScreenNavigator
) : ViewModel() {

    val screenFlow get() = navigator.mainFragmentFlow

    fun onBackPressed() {
        navigator.perform(Action.Back)
    }

}