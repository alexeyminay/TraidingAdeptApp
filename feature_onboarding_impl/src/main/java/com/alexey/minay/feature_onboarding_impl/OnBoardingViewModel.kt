package com.alexey.minay.feature_onboarding_impl

import com.alexey.minay.core_navigation.Action
import com.alexey.minay.core_navigation.INavigator
import com.alexey.minay.core_ui.SingleStateViewModel
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(
    private val navigator: INavigator
) : SingleStateViewModel<Unit, Nothing>(Unit) {

    fun openMenu() {
        navigator.perform(Action.OpenMenu)
    }

}