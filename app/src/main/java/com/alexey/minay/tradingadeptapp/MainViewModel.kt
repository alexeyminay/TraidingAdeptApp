package com.alexey.minay.tradingadeptapp

import androidx.lifecycle.ViewModel
import com.alexey.minay.feature_navigation_impl.ScreenNavigator

class MainViewModel(
    private val navigator: ScreenNavigator
): ViewModel() {

    val screenFlow get() = navigator.mainFragmentFlow

}