package com.alexey.minay.feature_onboarding_impl.di

import androidx.lifecycle.ViewModel

class OnBoardingComponentHolder : ViewModel() {

    var component: OnBoardingComponent = OnBoardingComponent.create()

}